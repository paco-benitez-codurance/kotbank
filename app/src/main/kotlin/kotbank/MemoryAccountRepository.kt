package kotbank

open class MemoryAccountRepository {
    private var amount: MutableList<LogAccount> = mutableListOf()
    fun add(log: LogAccount) {
        this.amount.add(log)
    }

    fun getAmounts(): List<LogAccount> = this.amount.sortedWith { a, b -> compare(a, b) }

    private fun compare(a: LogAccount?, b: LogAccount?): Int {
        val aStr = yyyymmddFormat(a)
        val bStr = yyyymmddFormat(b)
        return bStr.compareTo(aStr)
    }

    private fun yyyymmddFormat(a: LogAccount?) = a!!.date.split("/").reversed().joinToString()
}