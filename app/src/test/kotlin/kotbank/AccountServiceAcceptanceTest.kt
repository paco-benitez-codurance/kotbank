/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package kotbank

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.stream.Collectors


/*
Given a client makes a deposit of 1000 on 10-01-2012
And a deposit of 2000 on 13-01-2012
And a withdrawal of 500 on 14-01-2012
When they print their bank statement
Then they would see:

Date       || Amount || Balance
14/01/2012 || -500   || 2500
13/01/2012 || 2000   || 3000
10/01/2012 || 1000   || 1000
 */
class AccountServiceAcceptanceTest : StringSpec({
    var accountService: AccountService = AccountServiceImpl()

    beforeEach { accountService = AccountServiceImpl() }
    afterEach { }

    "bank kata acceptance test".config(enabled = true) {
        val clock = mockk<Clock>();
        every { clock.currentDate() } returns  "10/01/2012" andThen "13/01/2012" andThen "14/01/2012";

        val accountService: AccountService = AccountServiceImpl(clock = clock)

        //Set date on 10-01-2012
        accountService.deposit(1000)

        //Set date on 13-11-2012
        accountService.deposit(2000)

        //Set date on 14-11-2012
        accountService.withdraw(500)

        val expected = """
            Date||Amount||Balance
            14/01/2012||-500||2500
            13/01/2012||2000||3000
            10/01/2012||1000||1000
        """.trimIndent()

        val (baos, old) = initCaptureOutput()

        accountService.printStatement()

        endCaptureOutput(old)

        val written = String(baos.toByteArray())
        written shouldBe expected
    }

    "printStatement should show something" {
        val (baos, old) = initCaptureOutput()

        accountService.printStatement()

        val written = String(baos.toByteArray())
        written shouldBe "Date||Amount||Balance"

        endCaptureOutput(old)
    }
})

private fun endCaptureOutput(old: PrintStream) {
    System.out.flush()
    System.setOut(old)
}

private fun initCaptureOutput(): Pair<ByteArrayOutputStream, PrintStream> {
    val baos = ByteArrayOutputStream()
    val ps = PrintStream(baos)
    val old = System.out
    System.setOut(ps)
    return Pair(baos, old)
}
