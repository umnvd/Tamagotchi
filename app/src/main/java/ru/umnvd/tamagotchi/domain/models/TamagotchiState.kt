package ru.umnvd.tamagotchi.domain.models

/**
 * Состояние тамагочи.
 *
 * @property health Здоровье.
 * @property energy Бодрость.
 * @property satiety Сытость.
 * @property weight Вес.
 * @property joy Радость.
 * @property discipline Дисциплина.
 * */
data class TamagotchiState(
    val health: TamagotchiStateValue = TamagotchiStateValue(DEFAULT_HEALTH),
    val energy: TamagotchiStateValue = TamagotchiStateValue(DEFAULT_ENERGY),
    val satiety: TamagotchiStateValue = TamagotchiStateValue(DEFAULT_SATIETY),
    val weight: TamagotchiStateValue = TamagotchiStateValue(DEFAULT_WEIGHT),
    val joy: TamagotchiStateValue = TamagotchiStateValue(DEFAULT_JOY),
    val discipline: TamagotchiStateValue = TamagotchiStateValue(DEFAULT_DISCIPLINE),
) {

    companion object {

        private const val DEFAULT_HEALTH = 10
        private const val DEFAULT_ENERGY = 5
        private const val DEFAULT_SATIETY = 0
        private const val DEFAULT_WEIGHT = 0
        private const val DEFAULT_JOY = 5
        private const val DEFAULT_DISCIPLINE = 5
    }
}
