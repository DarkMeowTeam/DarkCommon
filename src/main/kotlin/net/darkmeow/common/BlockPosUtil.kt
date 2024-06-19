package net.darkmeow.common

object BlockPosUtil {

    private fun isPowerOfTwo(value: Int): Boolean {
        return value != 0 && (value and (value - 1)) == 0
    }

    private fun log2DeBruijn(input: Int): Int {
        var value = input
        value = if (net.darkmeow.common.BlockPosUtil.isPowerOfTwo(value)) value else net.darkmeow.common.BlockPosUtil.smallestEncompassingPowerOfTwo(
            value
        )
        return intArrayOf(
            0,
            1,
            28,
            2,
            29,
            14,
            24,
            3,
            30,
            22,
            20,
            15,
            25,
            17,
            4,
            8,
            31,
            27,
            13,
            23,
            21,
            19,
            16,
            7,
            26,
            12,
            18,
            6,
            11,
            5,
            10,
            9
        )[(value.toLong() * 125613361L shr 27).toInt() and 31]
    }

    private fun log2(value: Int): Int {
        return net.darkmeow.common.BlockPosUtil.log2DeBruijn(value) - (if (net.darkmeow.common.BlockPosUtil.isPowerOfTwo(
                value
            )
        ) 0 else 1)
    }

    private fun smallestEncompassingPowerOfTwo(value: Int): Int {
        var i = value - 1
        i = i or (i shr 1)
        i = i or (i shr 2)
        i = i or (i shr 4)
        i = i or (i shr 8)
        i = i or (i shr 16)
        return i + 1
    }

    private val NUM_X_BITS: Int = 1 + net.darkmeow.common.BlockPosUtil.log2(
        net.darkmeow.common.BlockPosUtil.smallestEncompassingPowerOfTwo(
            30000000
        )
    )
    private val NUM_Z_BITS = net.darkmeow.common.BlockPosUtil.NUM_X_BITS
    private val NUM_Y_BITS = 64 - net.darkmeow.common.BlockPosUtil.NUM_X_BITS - net.darkmeow.common.BlockPosUtil.NUM_Z_BITS

    private const val Z_SHIFT = 0
    private val Y_SHIFT = net.darkmeow.common.BlockPosUtil.Z_SHIFT + net.darkmeow.common.BlockPosUtil.NUM_Z_BITS
    private val X_SHIFT = net.darkmeow.common.BlockPosUtil.Y_SHIFT + net.darkmeow.common.BlockPosUtil.NUM_Y_BITS

    private val X_MASK = (1L shl net.darkmeow.common.BlockPosUtil.NUM_X_BITS) - 1L
    private val Y_MASK = (1L shl net.darkmeow.common.BlockPosUtil.NUM_Y_BITS) - 1L
    private val Z_MASK = (1L shl net.darkmeow.common.BlockPosUtil.NUM_Z_BITS) - 1L

    fun xFromLong(packedPos: Long): Int {
        return (packedPos shl 64 - net.darkmeow.common.BlockPosUtil.X_SHIFT - net.darkmeow.common.BlockPosUtil.NUM_X_BITS shr 64 - net.darkmeow.common.BlockPosUtil.NUM_X_BITS).toInt()
    }

    fun yFromLong(packedPos: Long): Int {
        return (packedPos shl 64 - net.darkmeow.common.BlockPosUtil.Y_SHIFT - net.darkmeow.common.BlockPosUtil.NUM_Y_BITS shr 64 - net.darkmeow.common.BlockPosUtil.NUM_Y_BITS).toInt()
    }

    fun zFromLong(packedPos: Long): Int {
        return (packedPos shl (64 - net.darkmeow.common.BlockPosUtil.NUM_Z_BITS) shr (64 - net.darkmeow.common.BlockPosUtil.NUM_Z_BITS)).toInt()
    }

    fun toLong(x: Int, y: Int, z: Int): Long {
        return (x.toLong() and net.darkmeow.common.BlockPosUtil.X_MASK shl net.darkmeow.common.BlockPosUtil.X_SHIFT) or
            (y.toLong() and net.darkmeow.common.BlockPosUtil.Y_MASK shl net.darkmeow.common.BlockPosUtil.Y_SHIFT) or
            (z.toLong() and net.darkmeow.common.BlockPosUtil.Z_MASK shl net.darkmeow.common.BlockPosUtil.Z_SHIFT)
    }
}