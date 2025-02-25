package org.example.project.platform

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

actual fun locationStatusFlow(): Flow<Boolean> = callbackFlow {
    send(true)
}