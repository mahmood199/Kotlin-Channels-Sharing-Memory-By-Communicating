package models

data class Cappuccino(
    override val coffeeBean: CoffeeBean,
    override val milk: Milk
) : Menu(coffeeBean, milk) {

    fun beans(): String = coffeeBean.name

}
