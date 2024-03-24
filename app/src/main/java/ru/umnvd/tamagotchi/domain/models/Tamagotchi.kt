package ru.umnvd.tamagotchi.domain.models

/**
 * Тамагочи
 *
 * @property state текущее состояние
 * @property memory последние действия
 * */
data class Tamagotchi(
    val state: TamagotchiState,
    val memory: TamagotchiMemory,
)
