package kotbank

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Clock {
    fun currentDate(): String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}
