package kotbank

import java.util.Optional

private const val HEADER = "Date || Amount || Balance"

class AccountServiceImpl(private val output: Output = ConsoleOutput(), private val clock: Clock = Clock()) :
    AccountService {

    private var date: String? = null
    private var amount: MutableList<Int> = mutableListOf()

    override fun deposit(amount: Int) {
        this.amount.add(amount)
        this.date = this.clock.currentDate()
    }

    override fun withdraw(amount: Int) {
        TODO("Not yet implemented")
    }

    override fun printStatement() {

        val res = amount.fold(
            Pair(0, HEADER)
        ) { acc, item ->
            val total = acc.first + item
            Pair(total, acc.second + formatTransaction(item, total))
        }

        output.print(res.second)

    }

    private fun formatTransaction(item: Int, total: Int) = "\n${this.date}||${item}||${total}"
}
