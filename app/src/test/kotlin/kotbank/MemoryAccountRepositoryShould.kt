package kotbank

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotbank.Money.Companion.toMoney

class MemoryAccountRepositoryShould : FreeSpec({

    "memory account should returns a list of sorted items " {
        val sut = MemoryAccountRepository();

        sut.add(LogAccount("1/1/1999", 1.toMoney(), 1))
        sut.add(LogAccount("1/1/2001", 1.toMoney(), 1))


        val list = sut.getAmounts()

        list shouldBe listOf(
            LogAccount("1/1/2001", 1.toMoney(), 1),
            LogAccount("1/1/1999", 1.toMoney(), 1)
        )
    }
});