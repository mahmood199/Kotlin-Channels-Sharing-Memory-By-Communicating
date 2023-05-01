package runnable_examples

import common_methods.grindCoffeeBeans
import common_methods.makeCappuccino
import common_methods.steamMilk
import common_methods.pullEspressoShot
import models.Cappuccino
import models.CoffeeBean
import models.Menu
import models.Milk


fun main() {
    val list = buildList<Menu> {
        add(Cappuccino(CoffeeBean.Regular(), Milk.Whole()))
    }
    makeCoffee(list)
}

fun makeCoffee(orders: List<Menu>) {
    for (order in orders) {
        println("Processing order: $order")
        when (order) {
            is Cappuccino -> {
                val groundBeans = grindCoffeeBeans(order.beans())
                val espressoShot = pullEspressoShot(order, groundBeans)
                val steamedMilk = steamMilk(order, order.milk)
                val cappuccino = makeCappuccino(order, espressoShot, steamedMilk)
                println("Serve $cappuccino")
            }
        }
    }
}
