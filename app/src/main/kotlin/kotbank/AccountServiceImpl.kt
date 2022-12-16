package kotbank

class AccountServiceImpl(val output: Output = ConsoleOutput()) : AccountService {

    override fun deposit(amount: Int) {
        TODO("Not yet implemented")
    }

    override fun withdraw(amount: Int) {
        TODO("Not yet implemented")
    }

    override fun printStatement() {
        output.print("Date || Amount || Balance")
    }

}
