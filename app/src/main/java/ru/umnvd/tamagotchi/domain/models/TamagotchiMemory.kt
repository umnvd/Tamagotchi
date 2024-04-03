package ru.umnvd.tamagotchi.domain.models

import java.time.LocalDateTime

/**
 * Память последних действий тамагочи
 *
 * @property lastMeal последний прием пищи
 * @property lastSleep последний сон
 * @property lastPlay последняя игра
 * @property lastPunishment последнее наказание
 * @property lastDefecation последнее испражнение
 * @property lastCleaning последняя уборка
 * @property lastUserInteraction последнее взаимодействие с пользователем
 * */
data class TamagotchiMemory(
    val lastMeal: LocalDateTime?,
    val lastSleep: LocalDateTime?,
    val lastPlay: LocalDateTime?,
    val lastPunishment: LocalDateTime?,
    val lastDefecation: LocalDateTime?,
    val lastCleaning: LocalDateTime?,
    val lastUserInteraction: LocalDateTime?,
)
