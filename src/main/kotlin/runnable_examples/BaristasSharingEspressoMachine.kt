package runnable_examples

import common_methods.grindCoffeeBeansAsync2
import common_methods.makeCappuccinoAsync2
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import machine.EspressoMachine
import models.Cappuccino
import models.CoffeeBean
import models.Menu
import models.Milk

@OptIn(ExperimentalCoroutinesApi::class)
fun main(): Unit = runBlocking {

    val espressoMachine = EspressoMachine(this)
    val orders = buildList {
        add(Cappuccino(CoffeeBean.Regular(), Milk.Whole()))
        add(Cappuccino(CoffeeBean.Premium(), Milk.Breve()))
        add(Cappuccino(CoffeeBean.Regular(), Milk.NonFat()))
        add(Cappuccino(CoffeeBean.Decaf(), Milk.Whole()))
        add(Cappuccino(CoffeeBean.Regular(), Milk.NonFat()))
        add(Cappuccino(CoffeeBean.Decaf(), Milk.NonFat()))
    }

    val ordersChannel = produce(CoroutineName("Cashier")) {
        for (order in orders) {
            send(order)
        }
    }

    coroutineScope {
        launch(CoroutineName("Barista-1")) {
            makeCoffeeAsync(ordersChannel, espressoMachine)
        }
        launch(CoroutineName("Barista-2")) {
            makeCoffeeAsync(ordersChannel, espressoMachine)
        }
    }

    espressoMachine.shutDownMachine()
}

suspend fun makeCoffeeAsync(ordersChannel: ReceiveChannel<Menu>, espressoMachine: EspressoMachine) {
    for(order in ordersChannel) {
        println(order)
        when(order) {
            is Cappuccino -> {
                val groundBeans = grindCoffeeBeansAsync2(order.beans())
                coroutineScope {
                    val espressoShotDeferred = async {
                        espressoMachine.pullEspressoShot(groundBeans)
                    }
                    val steamedMilkDeferred = async {
                        espressoMachine.steamMilk(order.milk)
                    }

                    val cappuccino = makeCappuccinoAsync2(
                        order,
                        espressoShotDeferred.await(),
                        steamedMilkDeferred.await()
                    )

                    println("Serve: $cappuccino")
                }
            }
        }
    }
}
