package kotbank

open class AccountRepository {
    private var amount: MutableList<LogAccount> = mutableListOf()
    fun add(log: LogAccount) {
        this.amount.add(log)
    }

    fun getAmounts(): List<LogAccount> = this.amount
}