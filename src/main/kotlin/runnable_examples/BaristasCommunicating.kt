package runnable_examples

import common_methods.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import models.*
import kotlin.system.measureTimeMillis

fun main(): Unit = runBlocking {
    val orders = buildList {
        add(Cappuccino(CoffeeBean.Regular, Milk.Whole))
        add(Cappuccino(CoffeeBean.Premium, Milk.Breve))
        add(Cappuccino(CoffeeBean.Regular, Milk.NonFat))
        add(Cappuccino(CoffeeBean.Decaf, Milk.Whole))
        add(Cappuccino(CoffeeBean.Regular, Milk.NonFat))
        add(Cappuccino(CoffeeBean.Decaf, Milk.NonFat))
    }

    val ordersChannel = Channel<Menu>()

    launch {
        for(order in orders) {
            ordersChannel.send(order)
        }
        //ordersChannel.close()
    }

    val t = measureTimeMillis {
        coroutineScope {
            launch(CoroutineName("Barista-1")) {
                makeCoffeeAsync(ordersChannel)
            }
            launch(CoroutineName("Barista-2")) {
                makeCoffeeAsync(ordersChannel)
            }
        }
    }

    println("Coroutine Execution time: ${t}ms")
}

suspend fun makeCoffeeAsync(orders: ReceiveChannel<Menu>) {
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


class EspressoMachine(scope: CoroutineScope): CoroutineScope by scope {

    suspend fun pullEspressoShot(): Espresso {
        //TODO
        return Espresso("First espresso")
    }

    suspend fun steamMilk(milk: Milk): Milk {
        delay(200)
        return Milk.Steamed
    }

}
