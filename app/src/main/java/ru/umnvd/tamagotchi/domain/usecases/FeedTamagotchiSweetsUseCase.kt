package ru.umnvd.tamagotchi.domain.usecases

import ru.umnvd.tamagotchi.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.domain.repositories.TamagotchiRepository
import java.time.LocalDateTime

class FeedTamagotchiSweetsUseCase constructor(
    private val tamagotchiRepository: TamagotchiRepository
)  {

    data class Params(
        val tamagotchi: Tamagotchi,
    )

    // TODO: Add tamagotchi answer. Tamagothi may not want to eat if he well-fed,
    //  but post EAT signal if has bad discipline

    suspend operator fun invoke(params: Params): Result<Tamagotchi> {
        val tamagotchi = params.tamagotchi
        val currentState = tamagotchi.state
        val currentMemory = tamagotchi.memory

        val newState = currentState.copy(
            joy = currentState.joy + JOY_STEP,
            weight = currentState.weight + WEIGHT_STEP,
        )

        val newMemory = currentMemory.copy(
            lastMeal = LocalDateTime.now()
        )

        return tamagotchiRepository.saveTamagotchi(
            tamagotchi = tamagotchi.copy(
                state = newState,
                memory = newMemory,
            )
        )
    }

    companion object {

        private const val JOY_STEP = 1
        private const val WEIGHT_STEP = 1
    }
}