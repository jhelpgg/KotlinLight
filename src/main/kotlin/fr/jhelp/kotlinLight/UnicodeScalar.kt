package fr.jhelp.kotlinLight

class UnicodeScalar(scalar: Int)
{
    private val string = scalar.toChar().toString()

    override fun toString() = this.string
}