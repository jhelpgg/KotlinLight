package fr.jhelp.kotlinLight.tasks

import java.util.PriorityQueue
import java.util.concurrent.atomic.AtomicBoolean

private const val EXIT_TIME = 34_567L

/**
 * Delayed task manager
 */
internal object Delay
{
    private val queue = PriorityQueue<DelayElement>()
    private val lock = Object()
    private val running = AtomicBoolean(false)
    private val waiting = AtomicBoolean(false)

    fun delay(milliseconds: Long, action: () -> Unit)
    {
        synchronized(this.queue)
        {
            this.queue.offer(DelayElement(action, System.currentTimeMillis() + milliseconds))
        }

        this.wakeup()
    }

    private fun wakeup()
    {
        synchronized(this.lock)
        {
            when
            {
                !this.running.getAndSet(true) -> parallel(this::run)
                this.waiting.get()            -> this.lock.notify()
            }

            Unit
        }
    }

    private fun run()
    {
        var delayElement: DelayElement?
        var waitBeforeExit = true

        do
        {
            synchronized(this.queue)
            {
                if (this.queue.isEmpty())
                {
                    delayElement = null
                }
                else
                {
                    delayElement = this.queue.peek()
                    waitBeforeExit = true
                }
            }


            if (delayElement != null)
            {
                val element = delayElement as DelayElement
                val timeLeft = element.time - System.currentTimeMillis()

                if (timeLeft <= 0)
                {
                    synchronized(this.queue)
                    {
                        this.queue.remove(element)
                    }

                    parallel(element.action)
                }
                else
                {
                    synchronized(this.lock)
                    {
                        this.waiting.set(true)
                        this.lock.wait(timeLeft)
                        this.waiting.set(false)
                    }
                }
            }
            else if (waitBeforeExit)
            {
                synchronized(this.lock)
                {
                    this.waiting.set(true)
                    this.lock.wait(EXIT_TIME)
                    this.waiting.set(false)
                }

                synchronized(this.queue)
                {
                    waitBeforeExit = this.queue.isNotEmpty()
                }
            }
        }
        while (waitBeforeExit)

        this.running.set(false)
    }
}