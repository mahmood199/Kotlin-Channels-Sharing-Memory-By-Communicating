package models

class Cappuccino(coffeeBean: CoffeeBean, milk: Milk) : Menu(coffeeBean, milk) {
    fun beans(): String = coffeeBean.name

}
