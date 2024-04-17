package ru.umnvd.tamagotchi.domain.usecases.signal

import ru.umnvd.tamagotchi.domain.models.TamagotchiSignal
import ru.umnvd.tamagotchi.domain.repositories.TamagotchiRepository
import javax.inject.Inject

class GenerateTamagotchiSignalUseCase @Inject constructor(
    private val tamagotchiRepository: TamagotchiRepository,
) {

    suspend operator fun invoke(): Result<TamagotchiSignal> = runCatching {
        val tamagotchi = tamagotchiRepository.getTamagotchi().getOrThrow()

        // TODO

        TamagotchiSignal.EAT
    }
}