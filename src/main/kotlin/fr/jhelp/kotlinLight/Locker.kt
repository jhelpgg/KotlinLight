package fr.jhelp.kotlinLight

import java.util.concurrent.Semaphore

class Locker
{
    private val semaphore = Semaphore(0)

    fun lock()
    {
        this.semaphore.acquire()
    }

    fun unlock()
    {
        this.semaphore.release()
    }
}