package com.example.swing.core.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val swDispatcher: SwDispatchers)

enum class SwDispatchers {
    Default,
    IO,
}