package models

sealed class Milk(open val name: String) {

    data class Whole (override val name: String = "Whole") : Milk(name)

    data class Breve(override val name: String = "Breve") : Milk(name)

    data class NonFat(override val name: String = "NonFat") : Milk(name)

    data class SteamedMilk(override val name: String = "SteamedMilk") : Milk(name)

}