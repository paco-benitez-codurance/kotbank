package kotbank

import kotbank.Money.Companion.toMoney


class AccountServiceImpl(
        private val clock: Clock = Clock(),
        private val repository: MemoryAccountRepository = MemoryAccountRepository(),
        private val printService: PrintService = PrintService()) :
        AccountService {


    override fun deposit(amount: Int) {
        val total = this.repository.getAmounts().sumOf { it.amount.value } + amount
        val log = LogAccount(this.clock.currentDate(), amount.toMoney(), total)
        repository.add(log)
    }

    override fun withdraw(amount: Int) {
        deposit(-amount)
    }

    override fun printStatement() {
        printService.printStatement(repository.getAmounts())
    }
}
