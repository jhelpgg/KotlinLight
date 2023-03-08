package fr.jhelp.kotlinLight

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// @ImportSwift("Foundation")
// =>
// import Foundation

/**
 * Label and qos must be used explicitly
 *
 * ie:
 *
 * ```kotlin
 * var queue = DispatchQueue(label="memorypie", qos=DispatchQoS.background)
 * ```
 */
class DispatchQueue(val label: String, val qos: DispatchQoS) {
    companion object {
        private val dispatchQueueGlobal = DispatchQueue("global", DispatchQoS.default)
        fun global() = DispatchQueue.dispatchQueueGlobal
    }

    private val coroutineScope = CoroutineScope(this.qos.dispatcher)


    // DispatchQueue.global().async { something() }
    // =>
    // DispatchQueue.global().async { something() }
    // ---
    // DispatchQueue.global().async(task)
    // =>
    // DispatchQueue.global().async(execute : task)
    fun async(execute: () -> Unit) {
        this.coroutineScope.launch {
            execute()
        }
    }

    // DispatchQueue.global().asyncAfter(DispatchTime.now()+DispatchTimeInterval.milliseconds(128)) { something() }
    // =>
    // DispatchQueue.global().asyncAfter(deadline: DispatchTime.now()+DispatchTimeInterval.milliseconds(128)) { something() }
    // ---
    // DispatchQueue.global().asyncAfter(DispatchTime.now()+DispatchTimeInterval.milliseconds(128), task)
    // =>
    // DispatchQueue.global().asyncAfter(deadline: DispatchTime.now()+DispatchTimeInterval.milliseconds(128), execute : task)
    fun asyncAfter(deadline: DispatchTime, execute: DispatchWorkItem) {
        execute.job = this.coroutineScope.launch {
            delay(deadline.timeMilliseconds - System.currentTimeMillis())

            if (!execute.isCancelled) {
                execute.block()
            }
        }
    }
}


internal fun (() -> Unit).suspended(): suspend () -> Unit = { this() }