package fr.jhelp.kotlinLight

import kotlinx.coroutines.*

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
 * var queue = DispatchQueue(label="memorypie", qos=DispatchQoS.background, attributes = DispatchQueue.Attributes.concurrent)
 * ```
 */
class DispatchQueue(label: String, qos: DispatchQoS, attributes: DispatchQueue.Attributes = DispatchQueue.Attributes()) {
    companion object {
        private val dispatchQueueGlobal = DispatchQueue("global", DispatchQoS.default, DispatchQueue.Attributes.concurrent)
        fun global() = DispatchQueue.dispatchQueueGlobal
    }

    class Attributes(val dispatcher: CoroutineDispatcher = QueueDispatcher) {
        companion object {
            // Indicates a concurrent queue
            val concurrent = Attributes(Dispatchers.Default)
        }
    }

    private val coroutineScope = CoroutineScope(attributes.dispatcher)

    // DispatchQueue.global().async { something() }
    // =>
    // DispatchQueue.global().async { something() }
    // ---
    // DispatchQueue.global().async(execute = task)
    // =>
    // DispatchQueue.global().async(execute : task)
    fun async(execute: () -> Unit) {
        this.coroutineScope.launch {
            execute()
        }
    }

    // DispatchQueue.global().asyncAfter(deadline = DispatchTime.now()+DispatchTimeInterval.milliseconds(128)) { something() }
    // =>
    // DispatchQueue.global().asyncAfter(deadline: DispatchTime.now()+DispatchTimeInterval.milliseconds(128)) { something() }
    // ---
    // DispatchQueue.global().asyncAfter(deadline = DispatchTime.now()+DispatchTimeInterval.milliseconds(128), execute = task)
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
