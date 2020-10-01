package fr.jhelp.kotlinLight

class ByteStream(private val byteList: CommonList<Byte>, private val bigEndian: Boolean)
{
    private var index = 0

    fun remaining(): Int =
        this.byteList.count - this.index

    fun readInt8(): Int
    {
        val value = this.byteList[this.index].toInt() and 0xFF
        this.index++
        return value
    }

    fun readInt16(): Int
    {
        val value =
            if (this.bigEndian)
            {
                ((this.byteList[this.index].toInt() and 0xFF) shl 8) or
                        (this.byteList[this.index + 1].toInt() and 0xFF)
            }
            else
            {
                ((this.byteList[this.index + 1].toInt() and 0xFF) shl 8) or
                        (this.byteList[this.index].toInt() and 0xFF)
            }

        this.index += 2
        return value
    }

    fun readInt32(): Int
    {
        val value =
            if (this.bigEndian)
            {
                ((this.byteList[this.index].toInt() and 0xFF) shl 24) or
                        ((this.byteList[this.index + 1].toInt() and 0xFF) shl 16) or
                        ((this.byteList[this.index + 2].toInt() and 0xFF) shl 8) or
                        (this.byteList[this.index + 3].toInt() and 0xFF)
            }
            else
            {
                ((this.byteList[this.index + 3].toInt() and 0xFF) shl 24) or
                        ((this.byteList[this.index + 2].toInt() and 0xFF) shl 16) or
                        ((this.byteList[this.index + 1].toInt() and 0xFF) shl 8) or
                        (this.byteList[this.index].toInt() and 0xFF)
            }
        this.index += 4
        return value
    }

    fun readInt64(): Long
    {
        val value =
            if (this.bigEndian)
            {
                ((this.byteList[this.index].toLong() and 0xFF) shl 56) or
                        ((this.byteList[this.index + 1].toLong() and 0xFF) shl 48) or
                        ((this.byteList[this.index + 2].toLong() and 0xFF) shl 40) or
                        ((this.byteList[this.index + 3].toLong() and 0xFF) shl 32) or
                        ((this.byteList[this.index + 4].toLong() and 0xFF) shl 24) or
                        ((this.byteList[this.index + 5].toLong() and 0xFF) shl 16) or
                        ((this.byteList[this.index + 6].toLong() and 0xFF) shl 8) or
                        (this.byteList[this.index + 7].toLong() and 0xFF)
            }
            else
            {
                ((this.byteList[this.index + 7].toLong() and 0xFF) shl 56) or
                        ((this.byteList[this.index + 6].toLong() and 0xFF) shl 48) or
                        ((this.byteList[this.index + 5].toLong() and 0xFF) shl 40) or
                        ((this.byteList[this.index + 4].toLong() and 0xFF) shl 32) or
                        ((this.byteList[this.index + 3].toLong() and 0xFF) shl 24) or
                        ((this.byteList[this.index + 2].toLong() and 0xFF) shl 16) or
                        ((this.byteList[this.index + 1].toLong() and 0xFF) shl 8) or
                        (this.byteList[this.index].toLong() and 0xFF)
            }
        this.index += 8
        return value
    }

    fun readFloat(): Float =
        Float.fromBits(this.readInt32())

    fun readDouble(): Double =
        Double.fromBits(this.readInt64())
}