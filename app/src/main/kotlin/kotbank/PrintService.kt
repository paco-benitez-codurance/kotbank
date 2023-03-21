package kotbank

private const val HEADER = "Date||Amount||Balance"

class PrintService(private val output: Output = ConsoleOutput()) {
    fun printStatement(accounts: List<LogAccount>) {
        val res = accounts.fold(
                HEADER
        ) { acc, item ->
            acc + formatTransaction(item.date, item.amount.value, item.total)
        }

        output.print(res)
    }

    private fun formatTransaction(date: String, item: Int, total: Int) = "\n${date}||${item}||${total}"

}