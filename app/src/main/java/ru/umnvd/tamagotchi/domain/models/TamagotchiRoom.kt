package ru.umnvd.tamagotchi.domain.models

/**
 * Окружение тамагочи.
 *
 * @property light Включен ли свет.
 * @property poopQuantity Количество экскрементов.
 * */
data class TamagotchiRoom(
    val light: Boolean = true,
    val poopQuantity: Int = 0,
)
