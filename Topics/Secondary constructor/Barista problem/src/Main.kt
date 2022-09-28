// write the EspressoMachine class here
class EspressoMachine(var costPerServing: Float) {

    constructor(coffeeCapsulesCount: Int, totalCost: Float): this(totalCost / coffeeCapsulesCount.toFloat())

    constructor(coffeeBeansWeight: Float, totalCost: Float): this(totalCost / coffeeBeansWeight * 10.0f)

}