package ru.umnvd.tamagotchi.domain.usecases

import ru.umnvd.tamagotchi.domain.models.FoodType
import ru.umnvd.tamagotchi.domain.models.Tamagotchi
import java.time.LocalDateTime

class FeedTamagotchiUseCase {

    data class Params(
        val tamagotchi: Tamagotchi,
        val foodType: FoodType,
    )

    // TODO: Add tamagotchi answer. Tamagothi may not want to eat if he well-fed,
    //  but post EAT signal if has bad discipline

    suspend operator fun invoke(params: Params): Result<Tamagotchi> {
        val tamagotchi = params.tamagotchi
        val currentState = tamagotchi.state
        val currentMemory = tamagotchi.memory

        val newState = when (params.foodType) {
            FoodType.SUBSTANTIAL_MEAL -> {
                currentState.copy(
                    satiety = currentState.satiety + SATIETY_STEP,
                )
            }

            FoodType.SWEETS -> {
                currentState.copy(
                    joy = currentState.joy + JOY_STEP,
                    weight = currentState.weight + WEIGHT_STEP,
                )
            }
        }
        val newMemory = currentMemory.copy(
            lastMeal = LocalDateTime.now()
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

        private const val SATIETY_STEP = 1
        private const val JOY_STEP = 1
        private const val WEIGHT_STEP = 1
    }
}