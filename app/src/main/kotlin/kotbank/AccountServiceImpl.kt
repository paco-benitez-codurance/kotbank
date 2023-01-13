package kotbank

class AccountServiceImpl(private val output: Output = ConsoleOutput(), private val clock: Clock = Clock()) :
    AccountService {

    private var date: String? = null
    private var amount: Int? = null

    override fun deposit(amount: Int) {
        this.amount = amount
        this.date = this.clock.currentDate()
    }

    override fun withdraw(amount: Int) {
        TODO("Not yet implemented")
    }

    override fun printStatement() {
        var result = "Date || Amount || Balance"
        if (amount != null) {
            result += "\n${this.date}||${this.amount}||${this.amount}"
        }

        output.print(result)
    }

}
