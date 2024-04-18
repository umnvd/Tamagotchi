package ru.umnvd.tamagotchi.di

import dagger.Component
import ru.umnvd.tamagotchi.common.data.di.RepositoriesBindsModule

@Component(
    modules = [
        DispatchersModule::class,
        RepositoriesBindsModule::class,
    ]
)
interface ApplicationComponent