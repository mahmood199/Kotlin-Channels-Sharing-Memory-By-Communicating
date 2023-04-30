package common_methods

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import models.Cappuccino
import models.Menu
import models.Milk

fun grindCoffeeBeans(beans: String): String {
    println("Grinding beans")
    return "Coffee Bean Type: $beans"
}

fun pullEspressoShot(menu: Menu, beans: String): String {
    println("Pulling espresso shot")
    return "${menu.milk} $beans"
}

fun steamMilk(order: Cappuccino, milk: Milk): String {
    println("Steaming Milk: $order")
    return "$milk"
}

fun makeCappuccino(order: Cappuccino, espressoShot: String, steamedMilk: String): Cappuccino {
    println("Combining all ingredients: $espressoShot $steamedMilk")
    return Cappuccino(order.coffeeBean, order.milk)
}

suspend fun grindCoffeeBeansAsync(beans: String): String {
    println("${currentCoroutineContext()[CoroutineName]} Grinding beans")
    delay(100)
    return "Coffee Bean Type: $beans"
}

suspend fun pullEspressoShotAsync(menu: Menu, beans: String): String {
    println("${currentCoroutineContext()[CoroutineName]} Pulling espresso shot")
    delay(100)
    return "${menu.milk} $beans"
}

suspend fun steamMilkAsync(order: Cappuccino, milk: Milk): String {
    println("${currentCoroutineContext()[CoroutineName]} Steaming Milk: $order")
    delay(100)
    return "$milk"
}

suspend fun makeCappuccinoAsync(order: Cappuccino, espressoShot: String, steamedMilk: String): Cappuccino {
    println("${currentCoroutineContext()[CoroutineName]} Combining all ingredients: $espressoShot $steamedMilk")
    delay(100)
    return Cappuccino(order.coffeeBean, order.milk)
}
