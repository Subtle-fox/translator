package core.ext

import kotlinx.coroutines.flow.*

fun <T> Flow<T>.log(tag: String) = this
    .onStart { println("$tag start") }
    .onEach { println("$tag each: $it") }
    .onCompletion { println("$tag completed") }
    .catch { System.err.println("$tag error") }
