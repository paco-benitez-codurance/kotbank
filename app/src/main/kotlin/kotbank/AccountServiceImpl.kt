package kotbank

private const val HEADER = "Date || Amount || Balance"

class AccountServiceImpl(
        private val output: Output = ConsoleOutput(),
        private val clock: Clock = Clock(),
        private val repository: AccountRepository = AccountRepository()) :
        AccountService {


    override fun deposit(amount: Int) {
        var log = LogAccount(this.clock.currentDate(), amount)
        repository.add(log)
    }

    override fun withdraw(amount: Int) {
        deposit(-amount)
    }

    override fun printStatement() {

        val res = repository.getAmounts().fold(
                Pair(0, HEADER)
        ) { acc, item ->
            val total = acc.first + item.amount
            Pair(total, acc.second + formatTransaction(item.date, item.amount, total))
        }

        output.print(res.second)

    }

    private fun formatTransaction(date: String, item: Int, total: Int) = "\n${date}||${item}||${total}"
}
