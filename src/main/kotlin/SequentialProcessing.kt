import models.Cappuccino
import models.Menu
import models.Milk


fun main() {

}

fun makeCoffee(orders: List<Menu>) {
    for (order in orders) {
        println("Processing order: $order")
        when (order) {
            is Cappuccino -> {
                val groundBeans = grindCoffeeBeans(order.beans())
                val espressoShot = pullEspressoShot(order, groundBeans)
                val steamedMilk = milkType(order, order.milk)
                val cappuccino = makeCappuccino(order, espressoShot, steamedMilk)
                println("Serve $cappuccino")
            }
        }
    }
}

fun grindCoffeeBeans(beans: String): String {
    println("Log something here")
    return "Coffee Bean Type: $beans"
}

fun pullEspressoShot(menu: Menu, beans: String): String {
    println("Log something here")
    return "${menu.milk} $beans"
}

fun milkType(order: Cappuccino, milk: Milk): String {
    println("Log something here: $order")
    return "$milk"
}

fun makeCappuccino(order: Cappuccino, espressoShot: String, steamedMilk: String): Cappuccino {
    println("Log something here: $espressoShot $steamedMilk")
    return Cappuccino(order.coffeeBean, order.milk)
}
