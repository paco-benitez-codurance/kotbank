package kotbank

private const val HEADER = "Date||Amount||Balance"

class AccountServiceImpl(
        private val output: Output = ConsoleOutput(),
        private val clock: Clock = Clock(),
        private val repository: MemoryAccountRepository = MemoryAccountRepository()) :
        AccountService {


    override fun deposit(amount: Int) {
        val total = this.repository.getAmounts().sumOf { it.amount } + amount
        val log = LogAccount(this.clock.currentDate(), amount, total)
        repository.add(log)
    }

    override fun withdraw(amount: Int) {
        deposit(-amount)
    }

    override fun printStatement() {

        val res = repository.getAmounts().fold(
                HEADER
        ) { acc, item ->
            acc + formatTransaction(item.date, item.amount, item.total)
        }

        output.print(res)

    }

    private fun formatTransaction(date: String, item: Int, total: Int) = "\n${date}||${item}||${total}"
}
