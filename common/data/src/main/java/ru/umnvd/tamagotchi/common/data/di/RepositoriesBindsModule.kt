package ru.umnvd.tamagotchi.common.data.di

import dagger.Binds
import dagger.Module
import ru.umnvd.tamagotchi.common.data.repositories.DefaultTamagotchiRepository
import ru.umnvd.tamagotchi.common.domain.repositories.TamagotchiRepository

@Module
interface RepositoriesBindsModule {
    @Binds
    fun bindTamagotchiRepositoryToDefaultTamagotchiRepository(
        defaultTamagotchiRepository: DefaultTamagotchiRepository,
    ): TamagotchiRepository
}