package fr.jhelp.kotlinLight

fun timeSince1970InMilliseconds(): Long =
    System.currentTimeMillis()

internal fun <T, T1> Iterator<T>.transform(transformation: (T) -> T1) =
    TransformIterator(this, transformation)

internal fun <T, T1> Iterable<T>.transform(transformation: (T) -> T1) =
    TransformIterable(this, transformation)

@Throws(RuntimeException::class)
fun fatalError(message: String)
{
    throw RuntimeException("Fatal Error : $message")
}

fun <T> fatal(message: String): T
{
    throw RuntimeException("Fatal Error : $message")
}