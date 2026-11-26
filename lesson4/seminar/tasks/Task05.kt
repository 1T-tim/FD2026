package seminar4

<<<<<<< HEAD
fun registrationStatus(requested: Int?, remaining: Int): String = when {
    requested == null -> "Неверное количество мест"
    requested > remaining -> "Мест не хватает"
    else -> "Зарегистрирован"
}
=======
/**
 * 5. Реши, можно ли зарегистрировать участника. requested — запрошенное число мест
 * после разбора (положительный Int или null), remaining — сколько мест осталось (0 или больше).
 * Верни ровно одну из строк: "Неверное количество мест", если requested == null;
 * "Мест не хватает", если requested больше remaining; "Зарегистрирован" в остальных случаях.
 * Примеры: (null, 3) → "Неверное количество мест", (4, 3) → "Мест не хватает",
 * (3, 3) → "Зарегистрирован". Если мест ровно столько, сколько нужно, это успех.
 * Функция только возвращает статус. Места списывать не надо.
 */
fun registrationStatus(requested: Int?, remaining: Int): String = error("Задача 5 пока не решена")
>>>>>>> 061c056 (Опубликовать решения задач 1–4 и 9 по Kotlin)

fun main() {
    println(registrationStatus(3, 3))
}
