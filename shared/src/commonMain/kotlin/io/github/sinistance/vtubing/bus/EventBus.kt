package io.github.sinistance.vtubing.bus

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

object EventBus {

    private val _events = MutableSharedFlow<Any>(extraBufferCapacity = 64)
    val events = _events.asSharedFlow()

    fun post(event: Any) {
        _events.tryEmit(event)
    }

    inline fun <reified T> subscribe(
        scope: CoroutineScope,
        noinline action: suspend (T) -> Unit
    ) {
        scope.launch {
            events.filterIsInstance<T>().collect { action(it) }
        }
    }
}