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
})
