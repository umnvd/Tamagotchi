package ru.umnvd.tamagotchi.common.domain.usecases.user

import ru.umnvd.tamagotchi.common.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.common.domain.repositories.TamagotchiRepository
import javax.inject.Inject

class IgnoreEatSignalUseCase @Inject constructor(
    private val tamagotchiRepository: TamagotchiRepository
) {
    data class Params(
        val tamagotchi: Tamagotchi,
    )

    suspend operator fun invoke(params: Params): Result<Tamagotchi> {
        val tamagotchi = params.tamagotchi
        val currentState = tamagotchi.state

        val newWeight = if (currentState.satiety <= 0) {
            currentState.weight - WEIGHT_STEP
        } else {
            currentState.weight
        }

        val newState = currentState.copy(
            satiety = currentState.satiety - SATIETY_STEP,
            weight = newWeight,
            discipline = currentState.discipline - DISCIPLINE_STEP,
        )

        return tamagotchiRepository.saveTamagotchi(
            tamagotchi = tamagotchi.copy(
                state = newState,
            )
        )
    }

    companion object {
        private const val SATIETY_STEP = 10
        private const val WEIGHT_STEP = 10
        private const val DISCIPLINE_STEP = 10
    }
}