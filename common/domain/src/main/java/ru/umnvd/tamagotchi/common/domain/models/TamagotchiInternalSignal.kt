package ru.umnvd.tamagotchi.common.domain.models

/**
 * Внутренний сигналы тамагочи.
 * */
enum class TamagotchiInternalSignal {
    /**
     * Проснулся.
     * */
    AWAKE,

    /**
     * Хочет какать.
     * */
    POOP,

    /**
     * Сгенерировать сигнал пользователю.
     * */
    TIMER,
}