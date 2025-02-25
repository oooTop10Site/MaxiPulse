package org.example.project.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun currentTimeFlow(intervalMillis: Long = 1000L): Flow<LocalDateTime> {
    return flow {
        while (true) {
            val currentTime = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
            emit(currentTime) // Преобразуйте в удобный формат
            delay(intervalMillis)
        }
    }
        .onStart {  } // Первоначальный эмит
        .flowOn(Dispatchers.Default) // Используем Default поток
}