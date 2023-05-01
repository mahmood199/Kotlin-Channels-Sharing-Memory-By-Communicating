package models

sealed class CoffeeBean(open val name: String) {

    data class Regular(override val name: String = "Regular") : CoffeeBean(name)

    data class Premium(override val name: String = "Premium") : CoffeeBean(name)

    data class Decaf(override val name: String = "Decaf") : CoffeeBean(name)

    data class GroundBeans(override val name: String = "GroundBeans") : CoffeeBean(name)

}