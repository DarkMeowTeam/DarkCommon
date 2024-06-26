package net.darkmeow.common.collection

interface MutableIntIterator : MutableIterator<Int> {
    override fun next(): Int {
        return nextInt()
    }

    fun nextInt(): Int
}