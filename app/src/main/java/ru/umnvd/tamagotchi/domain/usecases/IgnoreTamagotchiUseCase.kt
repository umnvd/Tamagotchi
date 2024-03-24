package ru.umnvd.tamagotchi.domain.usecases

import ru.umnvd.tamagotchi.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.domain.models.TamagotchiSignal
import java.time.LocalDateTime

class IgnoreTamagotchiUseCase {

    data class Params(
        val tamagotchi: Tamagotchi,
        val signal: TamagotchiSignal,
    )

    // TODO: Maype use chain of responsobility for the each signal

    suspend operator fun invoke(params: Params): Result<Tamagotchi> {
        val tamagotchi = params.tamagotchi
        val currentState = tamagotchi.state
        val currentMemory = tamagotchi.memory

        val newState = when (params.signal) {
            TamagotchiSignal.EAT -> {
                currentState.copy(
                    satiety = currentState.satiety - EAT_SIGNAL_SATIETY_STEP,
                    weight = currentState.weight - EAT_SIGNAL_WEIGHT_STEP,
                    joy = currentState.joy - EAT_SIGNAL_JOY_STEP,
                    discipline = currentState.discipline - EAT_SIGNAL_DISCIPLINE_STEP,
                )
            }

            // TODO
            else -> currentState
        }

        val newMemory = currentMemory.copy(
            lastIgnore = LocalDateTime.now(),
        )

        // TODO: Save result
        return Result.success(
            value = tamagotchi.copy(
                state = newState,
                memory = newMemory,
            )
        )
    }

    companion object {

        private const val EAT_SIGNAL_SATIETY_STEP = 1
        private const val EAT_SIGNAL_JOY_STEP = 1
        private const val EAT_SIGNAL_WEIGHT_STEP = 1
        private const val EAT_SIGNAL_DISCIPLINE_STEP = 1
    }
}