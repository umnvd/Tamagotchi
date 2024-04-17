package ru.umnvd.tamagotchi.domain.usecases.userinteraction

import ru.umnvd.tamagotchi.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.domain.repositories.TamagotchiRepository
import javax.inject.Inject

class IgnoreDuckSignalUseCase @Inject constructor(
    private val tamagotchiRepository: TamagotchiRepository
) {

    data class Params(
        val tamagotchi: Tamagotchi,
    )

    suspend operator fun invoke(params: Params): Result<Tamagotchi> {
        val currentTamagotchi = params.tamagotchi
        val currentState = currentTamagotchi.state

        val newState = currentState.copy(
            discipline = currentState.discipline - DISCIPLINE_STEP,
        )

        return tamagotchiRepository.saveTamagotchi(
            tamagotchi = currentTamagotchi.copy(
                state = newState,
            )
        )
    }

    companion object {

        private const val DISCIPLINE_STEP = 10
    }
}