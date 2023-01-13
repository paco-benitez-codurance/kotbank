package kotbank

import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AccountServiceImplShould : StringSpec({
    "printStatement should write header" {
        val output = mockk<Output>(relaxed = true)

        val accountService = AccountServiceImpl(output)

        accountService.printStatement()

        verify { output.print("Date || Amount || Balance") }
    }

    "deposit foo" {
        val output = mockk<Output>(relaxed = true)
        val clock = mockk<Clock>()
        val currentDate = "10/01/2012"
        every { clock.currentDate() } returns currentDate

        val accountService = AccountServiceImpl(output, clock)

        accountService.deposit(1000)

        accountService.printStatement()
        verify { output.print("""
            Date || Amount || Balance
            ${currentDate}||1000||1000
        """.trimIndent()) }
    }
})
