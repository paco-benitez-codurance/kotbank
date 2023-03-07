package kotbank

import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AccountServiceImplShould : StringSpec({
    lateinit var output: Output
    lateinit var clock: Clock
    lateinit var accountService: AccountService

    beforeEach {
        output = mockk(relaxed = true)
        clock = mockk()
        accountService = AccountServiceImpl(output, clock)
    }


    "printStatement should write header" {
        accountService.printStatement()

        verify { output.print("Date || Amount || Balance") }
    }

    "deposit an amount one time with an empty account" {
        val currentDate = "10/01/2012"
        every { clock.currentDate() } returns currentDate


        accountService.deposit(1000)

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

    "deposit amount one time with not empty account" {
        every { clock.currentDate() } returnsMany (listOf("10/01/2012", "10/01/2012"))
        accountService.deposit(1000)

        accountService.deposit(500)

        accountService.printStatement()
        verify {
            output.print(
                """
            Date || Amount || Balance
            10/01/2012||1000||1000
            10/01/2012||500||1500
        """.trimIndent()
            )
        }
    }
})
