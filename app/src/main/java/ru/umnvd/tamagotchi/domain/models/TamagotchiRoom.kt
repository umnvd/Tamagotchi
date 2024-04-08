package ru.umnvd.tamagotchi.domain.models

/**
 * Комната тамагочи
 *
 * @property light включен ли свет
 * @property poopQuantity количество экскрементов
 * */
data class TamagotchiRoom(
    val light: Boolean = true,
    val poopQuantity: Int = 0,
)
