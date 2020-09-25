package fr.jhelp.kotlinLight

import fr.jhelp.kotlinLight.tasks.delay
import fr.jhelp.kotlinLight.tasks.parallel

// @ImportSwift("Dispatch")
// =>
// import Dispatch

class DispatchQueue private constructor()
{
    companion object
    {
        private val dispatchQueue = DispatchQueue()
        fun global() = DispatchQueue.dispatchQueue
    }

    // DispatchQueue.global().async { something() }
    // =>
    // DispatchQueue.global().async { something() }
    // ---
    // DispatchQueue.global().async(task)
    // =>
    // DispatchQueue.global().async(execute : task)
    fun async(execute: () -> Unit) =
        parallel(execute)

    // DispatchQueue.global().asyncAfter(DispatchTime.now()+DispatchTimeInterval.milliseconds(128)) { something() }
    // =>
    // DispatchQueue.global().asyncAfter(deadline: DispatchTime.now()+DispatchTimeInterval.milliseconds(128)) { something() }
    // ---
    // DispatchQueue.global().asyncAfter(DispatchTime.now()+DispatchTimeInterval.milliseconds(128), task)
    // =>
    // DispatchQueue.global().asyncAfter(deadline: DispatchTime.now()+DispatchTimeInterval.milliseconds(128), execute : task)
    fun asyncAfter(deadline: DispatchTime, execute: () -> Unit) =
        delay(deadline.timeMilliseconds - System.currentTimeMillis(), execute)
}