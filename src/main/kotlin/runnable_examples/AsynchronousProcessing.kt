package runnable_examples

import common_methods.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.Cappuccino
import models.CoffeeBean
import models.Menu
import models.Milk

fun main(): Unit = runBlocking {
    val orders = buildList {
        add(Cappuccino(CoffeeBean.Regular(), Milk.Whole()))
        add(Cappuccino(CoffeeBean.Premium(), Milk.Breve()))
        add(Cappuccino(CoffeeBean.Regular(), Milk.NonFat()))
        add(Cappuccino(CoffeeBean.Decaf(), Milk.Whole()))
        add(Cappuccino(CoffeeBean.Regular(), Milk.NonFat()))
        add(Cappuccino(CoffeeBean.Decaf(), Milk.NonFat()))
    }

    launch(CoroutineName("Barista-1")) {
        makeCoffeeAsync(orders)
    }
    launch(CoroutineName("Barista-2")) {
        makeCoffeeAsync(orders)
    }
}

suspend fun makeCoffeeAsync(orders: List<Menu>) {
    for (order in orders) {
        println("Processing order: $order")
        when (order) {
            is Cappuccino -> {
                val groundBeans = grindCoffeeBeansAsync(order.beans())
                val espressoShot = pullEspressoShotAsync(order, groundBeans)
                val steamedMilk = steamMilkAsync(order, order.milk)
                val cappuccino = makeCappuccinoAsync(order, espressoShot, steamedMilk)
                println("Serve $cappuccino")
            }
        }
    }
}