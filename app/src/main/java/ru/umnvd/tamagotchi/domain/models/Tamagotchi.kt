package ru.umnvd.tamagotchi.domain.models

/**
 * Тамагочи.
 *
 * @property state Текущее состояние.
 * @property memory Последние действия.
 * @property room Окружение тамагочи.
 * */
data class Tamagotchi(
    val state: TamagotchiState,
    val memory: TamagotchiMemory,
    val room: TamagotchiRoom,
)
