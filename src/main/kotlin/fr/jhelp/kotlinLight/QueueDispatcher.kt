package fr.jhelp.kotlinLight

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

internal object QueueDispatcher : CoroutineDispatcher() {
    private var head: QueueDispatcherElement? = null
    private var tail: QueueDispatcherElement? = null
    private var thread: java.lang.Thread? = null
    private val mutex = Mutex()

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        this.enqueue(block)
        this.wakeup()
    }

    private fun enqueue(task: Runnable) {
        this.mutex.safeExecute {
            if (this.head == null) {
                this.head = QueueDispatcherElement(task)
                this.tail = this.head
            } else {
                this.tail?.next = QueueDispatcherElement(task)
                this.tail = this.tail?.next
            }
        }
    }

    private fun dequeue(): java.lang.Runnable? {
        var task: Runnable? = null

        this.mutex.safeExecute {
            task = this.head?.task
            this.head = this.head?.next

            if (this.head == null) {
                this.tail = null
            }
        }

        return task
    }

    private fun wakeup() {
        this.mutex.safeExecute {
            if (this.thread == null) {
                this.thread = java.lang.Thread { this.running() }
                this.thread?.start()
            }
        }
    }

    private fun running() {
        var task: Runnable?

        do {
            task = this.dequeue()

            try {
                task?.run()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        } while (task != null)

        this.mutex.safeExecute {
            this.thread = null
        }
    }
}