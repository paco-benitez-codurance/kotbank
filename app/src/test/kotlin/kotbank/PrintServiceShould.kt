package kotbank

import io.kotest.core.spec.style.FreeSpec
import io.mockk.mockk
import io.mockk.verify
import kotbank.Money.Companion.toMoney

class PrintServiceShould : FreeSpec({
    lateinit var output: Output
    lateinit var printService: PrintService

    beforeEach {
        output = mockk(relaxed = true)
        printService = PrintService(output)

    }

    "printStatement should write header" {
        printService.printStatement(emptyList())

        verify { output.print("Date||Amount||Balance") }
    }

    "print when there is a LogAccount " {
        val currentDate = "10/01/2012"

        printService.printStatement(listOf(LogAccount(currentDate, 1000.toMoney(), 1000)))

        verify {
            output.print(
                    """
            Date||Amount||Balance
            ${currentDate}||1000||1000
        """.trimIndent()
            )
        }
    }

    "print when there are more than one LogAccount" {

        printService.printStatement(listOf(
                LogAccount("11/01/2012", 500.toMoney(), 1500),
                LogAccount("10/01/2012", 1000.toMoney(), 1000)
        ))
        verify {
            output.print(
                    """
            Date||Amount||Balance
            11/01/2012||500||1500
            10/01/2012||1000||1000
        """.trimIndent()
            )
        }
    }

    "print when there are more than one LogAccount and negative amount" {

        printService.printStatement(listOf(
                LogAccount("11/01/2012", (-500).toMoney(), 500),
                LogAccount("10/01/2012", 1000.toMoney(), 1000),
        ))
        verify {
            output.print(
                    """
            Date||Amount||Balance
            11/01/2012||-500||500
            10/01/2012||1000||1000
        """.trimIndent()
            )
        }
    }

})
