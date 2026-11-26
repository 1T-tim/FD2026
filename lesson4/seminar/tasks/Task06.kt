package seminar4

class VenueSettings(private val defaultCapacity: String?) {
    fun capacityFor(organizerLimit: Any?): Int {
        val limit = when (organizerLimit) {
            null -> defaultCapacity
            is String -> organizerLimit
            is Int -> organizerLimit.toString()
            else -> throw EventConfigException("Лимит должен быть строкой или целым числом")
        }
        return readCapacity(limit)
    }
}

fun main() {
    println(VenueSettings("30").capacityFor(null))
}
