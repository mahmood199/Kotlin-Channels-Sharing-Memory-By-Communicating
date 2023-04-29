package runnable_examples

import common_methods.grindCoffeeBeans
import common_methods.makeCappuccino
import common_methods.milkType
import common_methods.pullEspressoShot
import models.Cappuccino
import models.Menu


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
