package ru.umnvd.tamagotchi.common.domain.models

/**
 * Значение составляющей состояния тамагочи.
 *
 * @property value Значение от [MINIMUM_VALUE] до [MAXIMUM_VALUE].
 * */
class TamagotchiStateValue(value: Int) {
    val value: Int

    init {
        this.value = value.coerceIn(
            minimumValue = MINIMUM_VALUE,
            maximumValue = MAXIMUM_VALUE,
        )
    }

    val maximum: Boolean = value == MAXIMUM_VALUE
    val minimum: Boolean = value == MINIMUM_VALUE

    operator fun compareTo(value: Int): Int {
        return this.value.compareTo(value)
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

    companion object {
        private const val MINIMUM_VALUE = -100
        private const val MAXIMUM_VALUE = 100
    }
}
