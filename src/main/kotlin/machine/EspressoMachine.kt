package machine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.selects.select
import models.CoffeeBean
import models.Espresso
import models.Milk

@OptIn(ObsoleteCoroutinesApi::class)
class EspressoMachine(scope: CoroutineScope): CoroutineScope by scope {
    
    data class SteamMilkRequest(
        val milk: Milk,
        val steamedMilkChannel: SendChannel<Milk.SteamedMilk>
    )
    
    data class PullEspressoShotRequest(
        val groundCoffeeBeans: CoffeeBean.GroundBeans,
        val espressoShotChannel: SendChannel<Espresso>
    )

    private val portaFilterOne: SendChannel<PullEspressoShotRequest> = actor {
        consumeEach {
            println("Pulling espresso shot on portafilter one")
            delay(20)
            it.espressoShotChannel.send(Espresso(it.groundCoffeeBeans.name))
            it.espressoShotChannel.close()
        }
    }

    private val portaFilterTwo: SendChannel<PullEspressoShotRequest> = actor {
        consumeEach {
            println("Pulling espresso shot on portafilter two")
            delay(20)
            it.espressoShotChannel.send(Espresso(it.groundCoffeeBeans.name))
            it.espressoShotChannel.close()
        }
    }

    private val steamWandOne: SendChannel<SteamMilkRequest> = actor {
        consumeEach {
            println("Steaming milk with wand one")
            delay(10)
            it.steamedMilkChannel.send(Milk.SteamedMilk())
            it.steamedMilkChannel.close()
        }
    }

    private val steamWandTwo: SendChannel<SteamMilkRequest> = actor {
        consumeEach {
            println("Steaming milk with wand two")
            delay(10)
            it.steamedMilkChannel.send(Milk.SteamedMilk())
            it.steamedMilkChannel.close()
        }
    }


    suspend fun pullEspressoShot(bean: CoffeeBean.GroundBeans): Espresso {
        return select { 
            val channel = Channel<Espresso>()
            val req = PullEspressoShotRequest(bean, channel)
            portaFilterOne.onSend(req) {
                channel.receive()
            }
            portaFilterTwo.onSend(req) {
                channel.receive()
            }
        }
    }

    suspend fun steamMilk(milk: Milk): Milk.SteamedMilk {
        return select { 
            val channel = Channel<Milk.SteamedMilk>()
            val req = SteamMilkRequest(milk, channel)
            steamWandOne.onSend(req) {
                channel.receive()
            }
            steamWandTwo.onSend(req) {
                channel.receive()
            }
        }
    }
    
    fun shutDownMachine() {
        portaFilterOne.close()
        portaFilterTwo.close()
        steamWandOne.close()
        steamWandTwo.close()
    }

}