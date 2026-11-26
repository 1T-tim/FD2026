package homework

class Tomato(val variety: String? = null) {
    fun label(): String = variety?.trim() ?: "Без сорта"
}
