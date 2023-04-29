package common_methods

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
