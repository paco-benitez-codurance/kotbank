package kotbank

import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AccountServiceImplShould : FreeSpec({
    lateinit var output: Output
    lateinit var clock: Clock
    lateinit var accountService: AccountService
    lateinit var repository: AccountRepository

    beforeEach {
        output = mockk(relaxed = true)
        clock = mockk()
        repository = mockk(relaxed = true)
        accountService = AccountServiceImpl(output, clock, repository)
    }

    "printStatement" - {
        "printStatement should write header"  {
            every {repository.getAmounts()} returns emptyList()

            accountService.printStatement()

            verify { output.print("Date || Amount || Balance") }
        }
        "print when there is a LogAccount " {
            val currentDate = "10/01/2012"
            every {repository.getAmounts()} returns listOf(LogAccount(currentDate,1000))

            accountService.printStatement()

            verify {
                output.print(
                    """
            Date || Amount || Balance
            ${currentDate}||1000||1000
        """.trimIndent()
                )
            }
        }

        "print when there are more than one LogAccount" {
            every {repository.getAmounts()} returns listOf(
                LogAccount("10/01/2012", 1000),
                LogAccount("11/01/2012", 500))

            accountService.printStatement()
            verify {
                output.print(
                    """
            Date || Amount || Balance
            10/01/2012||1000||1000
            11/01/2012||500||1500
        """.trimIndent()
                )
            }
        }

        "print when there are more than one LogAccount and negative amount" {
            every {repository.getAmounts()} returns listOf(
                LogAccount("10/01/2012", 1000),
                LogAccount("11/01/2012", -500))

            accountService.printStatement()
            verify {
                output.print(
                    """
            Date || Amount || Balance
            10/01/2012||1000||1000
            11/01/2012||-500||500
        """.trimIndent()
                )
            }
        }
    }


    "deposit an amount one time with an empty account" {
        val currentDate = "10/01/2012"
        every { clock.currentDate() } returns currentDate


        accountService.deposit(1000)

        verify {
            repository.add(LogAccount(currentDate, 1000))
        }
    }

    "withdraw an amount one time with an empty account" {
        val currentDate = "10/01/2012"
        every { clock.currentDate() } returns currentDate


        accountService.withdraw(1000)
        verify {
            repository.add(LogAccount(currentDate, -1000))
        }
    }
})
