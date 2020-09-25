package fr.jhelp.kotlinLight.tasks

internal class DelayElement(val action: () -> Unit, val time: Long) :
    Comparable<DelayElement>
{
    override fun compareTo(other: DelayElement): Int
    {
        val difference = this.time - other.time

        return when
        {
            difference > 0 -> 1
            difference < 0 -> -1
            else           -> 0
        }
    }
}