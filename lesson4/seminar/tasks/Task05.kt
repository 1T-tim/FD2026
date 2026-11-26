package seminar4

fun registrationStatus(requested: Int?, remaining: Int): String = when {
    requested == null -> "Неверное количество мест"
    requested > remaining -> "Мест не хватает"
    else -> "Зарегистрирован"
}

fun main() {
    println(registrationStatus(3, 3))
}
