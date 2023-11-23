package fr.jhelp.kotlinLight

internal class QueueDispatcherElement(val task:Runnable) {
    var next : QueueDispatcherElement? = null
}