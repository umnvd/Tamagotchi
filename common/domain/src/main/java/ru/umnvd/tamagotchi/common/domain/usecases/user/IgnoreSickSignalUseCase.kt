package ru.umnvd.tamagotchi.common.domain.usecases.user

import ru.umnvd.tamagotchi.common.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.common.domain.repositories.TamagotchiRepository
import javax.inject.Inject

class IgnoreSickSignalUseCase @Inject constructor(
    private val tamagotchiRepository: TamagotchiRepository
)  {
    data class Params(
        val tamagotchi: Tamagotchi,
    )

    suspend operator fun invoke(params: IgnoreDuckSignalUseCase.Params): Result<Tamagotchi> {
        val currentTamagotchi = params.tamagotchi
        val currentState = currentTamagotchi.state

        val newState = currentState.copy(
            health = currentState.health - HEALTH_STEP,
            discipline = currentState.discipline - DISCIPLINE_STEP,
        )

        return tamagotchiRepository.saveTamagotchi(
            currentTamagotchi.copy(
                state = newState,
            )
        )
    }

    companion object {
        private const val HEALTH_STEP = 10
        private const val DISCIPLINE_STEP = 10
    }
}