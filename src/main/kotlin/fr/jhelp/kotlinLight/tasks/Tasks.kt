package fr.jhelp.kotlinLight.tasks

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.max

internal fun parallel(task: () -> Unit)
{
    globalScope.launch {
        withContext(Dispatchers.Default)
        {
            task.suspended()()
        }
    }
}

internal fun delay(delay: Long, task: () -> Unit)
{
    Delay.delay(max(1L, delay), task)
}

// Coroutines management

private val globalScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

private fun (() -> Unit).suspended(): suspend () -> Unit =
    { this() }

