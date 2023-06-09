package fr.jhelp.kotlinLight

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.Thread

class DispatchQueueTests {
    @Test
    fun concurrentTest() {
        TaskCounter.resetCounters()
        val dispatchQueue = DispatchQueue(label = "label", qos = DispatchQoS.default, attributes = DispatchQueue.Attributes.concurrent)

        for (time in 0 until 32) {
            dispatchQueue.async { TaskCounter(128L).play() }
        }

        Thread.sleep(4096L)

        Assertions.assertEquals(0, TaskCounter.active)
        Assertions.assertEquals(32, TaskCounter.total)
        Assertions.assertEquals(20, TaskCounter.inSameTime)
    }

    @Test
    fun notConcurrentTest() {
        TaskCounter.resetCounters()
        val dispatchQueue = DispatchQueue(label = "label", qos = DispatchQoS.background)

        for (time in 0 until 32) {
            dispatchQueue.async { TaskCounter(128L).play() }
        }

        Thread.sleep(8192L)

        Assertions.assertEquals(0, TaskCounter.active)
        Assertions.assertEquals(32, TaskCounter.total)
        Assertions.assertEquals(1, TaskCounter.inSameTime)
    }
}