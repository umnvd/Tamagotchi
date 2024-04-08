package ru.umnvd.tamagotchi.domain.usecases

import ru.umnvd.tamagotchi.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.domain.repositories.TamagotchiRepository

class TreatTamagotchiUseCase constructor(
    private val tamagotchiRepository: TamagotchiRepository
)  {

    data class Params(
        val tamagotchi: Tamagotchi,
    )

    suspend operator fun invoke(params: Params): Result<Tamagotchi> {
        val tamagotchi = params.tamagotchi
        val currentState = tamagotchi.state
        val currentMemory = tamagotchi.memory

        return Result.failure(NotImplementedError())
    }

    companion object {

        private const val STEP = 1
    }
}