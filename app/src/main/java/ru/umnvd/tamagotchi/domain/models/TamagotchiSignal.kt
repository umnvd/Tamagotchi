package ru.umnvd.tamagotchi.domain.models

/**
 * Сигнал тамагочи.
 * */
enum class TamagotchiSignal {
    
    /**
     * Хочет кушать.
     * */
    EAT,

    /**
     * Хочет играть.
     * */
    PLAY,

    /**
     * Хочет спать.
     * */
    SLEEP,

    /**
     * Заболел.
     * */
    SICK,

    /**
     * Покакал.
     * */
    DUCK,
}