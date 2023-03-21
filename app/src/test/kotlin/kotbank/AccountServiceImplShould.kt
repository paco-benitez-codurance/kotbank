package kotbank

import io.kotest.core.spec.style.FreeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotbank.Money.Companion.toMoney

class AccountServiceImplShould : FreeSpec({
    lateinit var clock: Clock
    lateinit var accountService: AccountService
    lateinit var repository: MemoryAccountRepository
    lateinit var printService: PrintService

    beforeEach {
        clock = mockk()
        repository = mockk(relaxed = true)
        printService = mockk(relaxed = true)
        accountService = AccountServiceImpl(clock, repository, printService)
    }

    "print when there is a LogAccount " {
        val currentDate = "10/01/2012"
        val accounts = listOf(LogAccount(currentDate, 1000.toMoney(), 1000))
        every { repository.getAmounts() } returns accounts

        accountService.printStatement()

        verify { printService.printStatement(accounts) }

    }

    "deposit an amount one time with an empty account" {
        val currentDate = "10/01/2012"
        every { clock.currentDate() } returns currentDate


        accountService.deposit(1000)

        verify {
            repository.add(LogAccount(currentDate, 1000.toMoney(), 1000))
        }
    }

    "withdraw an amount one time with an empty account" {
        val currentDate = "10/01/2012"
        every { clock.currentDate() } returns currentDate


        accountService.withdraw(1000)
        verify {
            repository.add(LogAccount(currentDate, (-1000).toMoney(), -1000))
        }
    }
})
