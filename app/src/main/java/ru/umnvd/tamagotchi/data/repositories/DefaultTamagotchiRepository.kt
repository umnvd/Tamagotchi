package ru.umnvd.tamagotchi.data.repositories

import ru.umnvd.tamagotchi.domain.models.Tamagotchi
import ru.umnvd.tamagotchi.domain.models.TamagotchiMemory
import ru.umnvd.tamagotchi.domain.models.TamagotchiRoom
import ru.umnvd.tamagotchi.domain.models.TamagotchiState
import ru.umnvd.tamagotchi.domain.repositories.TamagotchiRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultTamagotchiRepository @Inject constructor(

) : TamagotchiRepository {

    private var tamagotchi = Tamagotchi(
        state = TamagotchiState(),
        memory = TamagotchiMemory(),
        room = TamagotchiRoom(),
    )

    override suspend fun getTamagotchi(): Result<Tamagotchi> {
        return Result.success(tamagotchi)
    }

    override suspend fun saveTamagotchi(tamagotchi: Tamagotchi): Result<Tamagotchi> {
        this.tamagotchi = tamagotchi
        return Result.success(tamagotchi)
    }
}