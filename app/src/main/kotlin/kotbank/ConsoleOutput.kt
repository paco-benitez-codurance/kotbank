package kotbank

class ConsoleOutput : Output {
    override fun print(str: String) {
        kotlin.io.print(str)
    }
}