package ru.umnvd.tamagotchi.di

import dagger.Binds
import dagger.Module
import ru.umnvd.tamagotchi.data.repositories.DefaultTamagotchiRepository
import ru.umnvd.tamagotchi.domain.repositories.TamagotchiRepository

@Module
interface RepositoriesBindsModule {

    @Binds
    fun bindTamagotchiRepositoryToDefaultTamagotchiRepository(
        defaultTamagotchiRepository: DefaultTamagotchiRepository,
    ): TamagotchiRepository
}