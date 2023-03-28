package fr.jhelp.kotlinLight

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.Thread

class DispatchQueueTests {
    @Test
    fun concurrentText() {
        TaskCounter.resetCounters()
        val dispatchQueue = DispatchQueue(label = "label", qos = DispatchQoS.default, attributes = DispatchQueue.Attributes.concurrent)

        for(time in 0 until 16) {
            dispatchQueue.async { TaskCounter(128L).play() }
        }

        Thread.sleep(2048L)

        Assertions.assertEquals(0, TaskCounter.active)
        Assertions.assertEquals(16, TaskCounter.total)
        Assertions.assertEquals(16, TaskCounter.inSameTime)
    }

    @Test
    fun notConcurrentTest() {
        TaskCounter.resetCounters()
        val dispatchQueue = DispatchQueue(label = "label", qos = DispatchQoS.background)

        for(time in 0 until 16) {
            dispatchQueue.async { TaskCounter(128L).play() }
        }

        Thread.sleep(4096L)

        Assertions.assertEquals(0, TaskCounter.active)
        Assertions.assertEquals(16, TaskCounter.total)
        Assertions.assertEquals(1, TaskCounter.inSameTime)
    }
}