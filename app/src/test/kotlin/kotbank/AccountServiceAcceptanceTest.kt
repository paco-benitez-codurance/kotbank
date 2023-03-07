/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package kotbank

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.io.ByteArrayOutputStream
import java.io.PrintStream


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
        val accountService: AccountService = AccountServiceImpl()

        //Set date on 10-01-2012
        accountService.deposit(1000)

        //Set date on 13-11-2012
        accountService.deposit(2000)

        //Set date on 14-11-2012
        accountService.withdraw(500)

        val expected = """
            Date       || Amount || Balance
            14/01/2012 || -500   || 2500
            13/01/2012 || 2000   || 3000
            10/01/2012 || 1000   || 1000
        """.trimIndent()
        accountService.printStatement()
        //assert statement equal expected
    }

    "printStatement should show something" {
        val baos = ByteArrayOutputStream()
        val ps = PrintStream(baos)
        val old = System.out
        System.setOut(ps)

        accountService.printStatement()

        val written = String(baos.toByteArray())
        written shouldBe "Date || Amount || Balance"

        System.out.flush()
        System.setOut(old)
    }
})
