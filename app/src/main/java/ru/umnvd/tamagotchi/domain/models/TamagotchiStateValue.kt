package ru.umnvd.tamagotchi.domain.models

/**
 * Значение составляющей состояния тамагочи
 *
 * @property value значение от -10 до 10
 * */
class TamagotchiStateValue(value: Int) {

    val value: Int

    init {
        this.value = value.coerceIn(
            minimumValue = -10,
            maximumValue = 10,
        )
    }

    operator fun plus(value: Int): TamagotchiStateValue {
        return TamagotchiStateValue(this.value + value)
    }

    operator fun plus(value: TamagotchiStateValue): TamagotchiStateValue {
        return TamagotchiStateValue(this.value + value.value)
    }

    operator fun minus(value: Int): TamagotchiStateValue {
        return TamagotchiStateValue(this.value - value)
    }

    operator fun minus(value: TamagotchiStateValue): TamagotchiStateValue {
        return TamagotchiStateValue(this.value - value.value)
    }
}
