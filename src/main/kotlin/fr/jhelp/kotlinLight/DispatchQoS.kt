package fr.jhelp.kotlinLight

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

enum class DispatchQoS(val dispatcher: CoroutineDispatcher) {
    default(Dispatchers.Default),

    /**
     * The quality-of-service class for user-interactive tasks, such as animations, event handling, or updates to your app's user interface.
     */
    userInteractive(Dispatchers.Default),

    /**
     * The quality-of-service class for tasks that prevent the user from actively using your app.
     */
    userInitiated(Dispatchers.Default),

    /**
     * The quality-of-service class for tasks that the user does not track actively.
     */
    utility(Dispatchers.Default),

    /**
     * The quality-of-service class for maintenance or cleanup tasks that you create.
     */
    background(QueueDispatcher)
}