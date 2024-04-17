package ru.umnvd.tamagotchi.domain.usecases.tamagotchi

import ru.umnvd.tamagotchi.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.domain.models.TamagotchiSignal
import ru.umnvd.tamagotchi.domain.repositories.TamagotchiRepository
import javax.inject.Inject

class GenerateTamagotchiSignalUseCase @Inject constructor(
    private val tamagotchiRepository: TamagotchiRepository,
) {

    suspend operator fun invoke(): Result<TamagotchiSignal?> = runCatching {
        val tamagotchi = tamagotchiRepository.getTamagotchi().getOrThrow()

        when {
            tamagotchi.isSick() -> TamagotchiSignal.SICK
            tamagotchi.wantToSleep() -> TamagotchiSignal.SLEEP
            tamagotchi.wantToCleanDuck() -> TamagotchiSignal.DUCK
            tamagotchi.wantToEat() -> TamagotchiSignal.EAT
            tamagotchi.wantToPlay() -> TamagotchiSignal.PLAY
            else -> null
        }
    }

    private fun Tamagotchi.isSick(): Boolean = state.health <= 0

    private fun Tamagotchi.wantToSleep(): Boolean = state.energy <= 0

    private fun Tamagotchi.wantToCleanDuck(): Boolean = room.poopQuantity > 0

    private fun Tamagotchi.wantToEat(): Boolean = state.satiety <= 0

    private fun Tamagotchi.wantToPlay(): Boolean = state.joy <= 0
}