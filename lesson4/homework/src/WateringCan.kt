package homework

class EmptyWateringCanException : IllegalStateException("Лейка пуста")

class WateringCan(private val capacity: Int = 2) {
    init { require(capacity > 0) }

    private var remaining = capacity

    fun pour() {
        if (remaining == 0) throw EmptyWateringCanException()
        remaining--
    }

    fun refill() {
        remaining = capacity
    }
}
