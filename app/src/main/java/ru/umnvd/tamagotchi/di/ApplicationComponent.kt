package ru.umnvd.tamagotchi.di

import dagger.Component

@Component(
    modules = [
        DispatchersModule::class,
        RepositoriesBindsModule::class,
    ]
)
interface ApplicationComponent