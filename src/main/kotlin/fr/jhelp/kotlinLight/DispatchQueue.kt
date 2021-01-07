package fr.jhelp.kotlinLight

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// @ImportSwift("Dispatch")
// =>
// import Dispatch

class DispatchQueue internal constructor(val label: String)
{
    companion object
    {
        private val dispatchQueueGlobal = DispatchQueue("global")
        fun global() = DispatchQueue.dispatchQueueGlobal
    }

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    // DispatchQueue.global().async { something() }
    // =>
    // DispatchQueue.global().async { something() }
    // ---
    // DispatchQueue.global().async(task)
    // =>
    // DispatchQueue.global().async(execute : task)
    fun async(execute: () -> Unit)
    {
        this.coroutineScope.launch {
            withContext(Dispatchers.Default)
            {
                execute.suspended()()
            }
        }
    }

    // DispatchQueue.global().asyncAfter(DispatchTime.now()+DispatchTimeInterval.milliseconds(128)) { something() }
    // =>
    // DispatchQueue.global().asyncAfter(deadline: DispatchTime.now()+DispatchTimeInterval.milliseconds(128)) { something() }
    // ---
    // DispatchQueue.global().asyncAfter(DispatchTime.now()+DispatchTimeInterval.milliseconds(128), task)
    // =>
    // DispatchQueue.global().asyncAfter(deadline: DispatchTime.now()+DispatchTimeInterval.milliseconds(128), execute : task)
    fun asyncAfter(deadline: DispatchTime, execute: () -> Unit)
    {
        this.coroutineScope.launch {
            withContext(Dispatchers.Default)
            {
                delay(deadline.timeMilliseconds - System.currentTimeMillis())
                execute.suspended()()
            }
        }
    }
}


internal fun (() -> Unit).suspended(): suspend () -> Unit = { this() }