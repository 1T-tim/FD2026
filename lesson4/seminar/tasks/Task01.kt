package seminar4

fun displayName(name: String?): String {
    val trimmed = name?.trim() ?: return "Гость"
    return trimmed.ifEmpty { "Гость" }
}

fun main() {
    println(displayName("  Аня  "))
}
