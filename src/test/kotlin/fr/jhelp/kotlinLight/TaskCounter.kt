package fr.jhelp.kotlinLight

import java.lang.Thread
import kotlin.math.max

class TaskCounter(val durationMillisecond: Long) {
    companion object {
        var active = 0
            private set
        var inSameTime = 0
            private set
        var total = 0
        private set
        private val mutex = Mutex()

        fun resetCounters() {
            TaskCounter.mutex.safeExecute {
                TaskCounter.active = 0
                TaskCounter.inSameTime = 0
                TaskCounter.total = 0
            }
        }
    }

    fun play() {
        TaskCounter.mutex.safeExecute {
            TaskCounter.total++
            TaskCounter.active++
            TaskCounter.inSameTime = max(TaskCounter.inSameTime, TaskCounter.active)
        }

        Thread.sleep(this.durationMillisecond)

        TaskCounter.mutex.safeExecute {
            TaskCounter.active--
            TaskCounter.inSameTime = max(TaskCounter.inSameTime, TaskCounter.active)
        }
    }
}