package com.example.uikit.utills

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun CoroutineScope.launchWithCatch(
    catch: suspend (Throwable) -> Unit,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch {
        try {
            block()
        } catch (th: Throwable) {
            if (th !is CancellationException) {
                catch(th)
            }
        }
    }
}
