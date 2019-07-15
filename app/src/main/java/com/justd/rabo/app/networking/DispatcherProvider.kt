package com.justd.rabo.app.networking

import kotlinx.coroutines.CoroutineDispatcher

class DispatcherProvider(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher
)