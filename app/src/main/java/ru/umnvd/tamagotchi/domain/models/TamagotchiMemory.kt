package ru.umnvd.tamagotchi.domain.models

import java.time.LocalDateTime

/**
 * Память последних действий тамагочи.
 *
 * @property lastMeal Последний прием пищи.
 * @property lastSleep Последний сон.
 * @property lastPlay Последняя игра.
 * @property lastPunishment Последнее наказание.
 * @property lastDefecation Последнее испражнение.
 * @property lastCleaning Последняя уборка.
 * @property lastTreatment Последнее лечение.
 * @property lastSignal Последний сигнал.
 * @property lastUserInteraction Последнее взаимодействие с пользователем.
 * */
data class TamagotchiMemory(
    val lastMeal: LocalDateTime? = null,
    val lastSleep: LastTamagotchiSleep? = null,
    val lastPlay: LocalDateTime? = null,
    val lastPunishment: LocalDateTime? = null,
    val lastDefecation: LocalDateTime? = null,
    val lastCleaning: LocalDateTime? = null,
    val lastTreatment: LocalDateTime? = null,
    val lastSignal: LastTamagotchiSignal? = null,
) {

    val lastUserInteraction: LocalDateTime? = listOfNotNull(
        lastMeal,
        lastSleep?.start,
        lastPlay,
        lastPunishment,
        lastCleaning,
        lastTreatment,
    ).minOrNull()
}

/**
 * Последний сигнал тамагочи.
 *
 * @property dateTime Дата и время сигнала.
 * @property signal Сигнал.
 * */
data class LastTamagotchiSignal(
    val dateTime: LocalDateTime,
    val signal: TamagotchiSignal,
)

/**
 * Последний сон тамагочи.
 *
 * @property start Дата и время начала сна.
 * @property end Дата и время пробуждения.
 * */
data class LastTamagotchiSleep(
    val start: LocalDateTime,
    val end: LocalDateTime? = null,
)
