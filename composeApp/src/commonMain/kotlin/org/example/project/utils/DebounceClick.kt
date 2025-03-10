package org.example.project.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.Clock
import org.example.project.platform.randomUUID

private const val DEBOUNCE_TIME_MILLIS = 1000L

internal interface EventProcessor {
    fun processEvent(event: () -> Unit)

    companion object {
        val buttonClickMap = mutableMapOf<String, EventProcessor>()
    }
}

internal fun EventProcessor.Companion.get(id: String): EventProcessor {
    return buttonClickMap.getOrPut(
        id
    ) {
        EventProcessorImpl()
    }
}

private class EventProcessorImpl : EventProcessor {
    private val now: Long
        get() = Clock.System.now().toEpochMilliseconds()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= DEBOUNCE_TIME_MILLIS) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}

@Composable
fun debouncedClick(
    id: String = randomUUID(),
    onClick: () -> Unit,
): () -> Unit {
    val multipleEventsCutter = remember { EventProcessor.get(id) }
    val newOnClick: () -> Unit = {
        multipleEventsCutter.processEvent { onClick() }
    }
    return newOnClick
}