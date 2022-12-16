package kotbank

interface AccountService {
    fun deposit(amount: Int): Unit
    fun withdraw(amount: Int): Unit
    fun printStatement(): Unit

    companion object {

    }
}