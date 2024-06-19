@file:JvmName("BufferUtils")

package net.darkmeow.common

import sun.misc.Unsafe
import java.nio.*
import java.util.function.Consumer

fun allocateByte(capacity: Int): ByteBuffer = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder())

fun allocateShort(capacity: Int): ShortBuffer = net.darkmeow.common.allocateByte(capacity * 4).asShortBuffer()

fun allocateInt(capacity: Int): IntBuffer = net.darkmeow.common.allocateByte(capacity * 4).asIntBuffer()

fun allocateFloat(capacity: Int): FloatBuffer = net.darkmeow.common.allocateByte(capacity * 4).asFloatBuffer()

fun <T : Buffer> T.skip(count: Int): Buffer {
    this.position(position() + count)
    return this
}

val UNSAFE = run {
    val field = Unsafe::class.java.getDeclaredField("theUnsafe")
    field.isAccessible = true
    field.get(null) as Unsafe
}

private val ADDRESS_OFFSET = net.darkmeow.common.UNSAFE.objectFieldOffset(Buffer::class.java.getDeclaredField("address"))
private val POSITION_OFFSET = net.darkmeow.common.UNSAFE.objectFieldOffset(Buffer::class.java.getDeclaredField("position"))
private val MARK_OFFSET = net.darkmeow.common.UNSAFE.objectFieldOffset(Buffer::class.java.getDeclaredField("mark"))
private val LIMIT_OFFSET = net.darkmeow.common.UNSAFE.objectFieldOffset(Buffer::class.java.getDeclaredField("limit"))
private val CAPACITY_OFFSET = net.darkmeow.common.UNSAFE.objectFieldOffset(Buffer::class.java.getDeclaredField("capacity"))

var ByteBuffer.address
    get() = net.darkmeow.common.UNSAFE.getLong(this, net.darkmeow.common.ADDRESS_OFFSET)
    set(value) {
        net.darkmeow.common.UNSAFE.putLong(this, net.darkmeow.common.ADDRESS_OFFSET, value)
    }

var ByteBuffer.position
    get() = position()
    set(value) {
        net.darkmeow.common.UNSAFE.putInt(this, net.darkmeow.common.POSITION_OFFSET, value)
    }

var ByteBuffer.mark
    get() = net.darkmeow.common.UNSAFE.getInt(this, net.darkmeow.common.MARK_OFFSET)
    set(value) {
        net.darkmeow.common.UNSAFE.putInt(this, net.darkmeow.common.MARK_OFFSET, value)
    }

var ByteBuffer.limit
    get() = limit()
    set(value) {
        net.darkmeow.common.UNSAFE.putInt(this, net.darkmeow.common.LIMIT_OFFSET, value)
    }

var ByteBuffer.capacity
    get() = capacity()
    set(value) {
        net.darkmeow.common.UNSAFE.putInt(this, net.darkmeow.common.CAPACITY_OFFSET, value)
    }

val DIRECT_BYTE_BUFFER_CLASS = net.darkmeow.common.allocateByte(0).javaClass

fun wrapDirectByteBuffer(address: Long, capacity: Int): ByteBuffer {
    val buffer = net.darkmeow.common.UNSAFE.allocateInstance(net.darkmeow.common.DIRECT_BYTE_BUFFER_CLASS) as ByteBuffer

    buffer.address = address
    buffer.mark = -1
    buffer.limit = capacity
    buffer.capacity = capacity

    return buffer
}

private val FREE_FUNC = run {
    try {
        val invokeCleanerMethod = net.darkmeow.common.UNSAFE.javaClass.getDeclaredMethod("invokeCleaner", ByteBuffer::class.java)
        Consumer<ByteBuffer> {
            invokeCleanerMethod.invoke(net.darkmeow.common.UNSAFE, it)
        }
    } catch (e: NoSuchMethodException) {
        val cleanerField = net.darkmeow.common.DIRECT_BYTE_BUFFER_CLASS.getDeclaredField("cleaner")
        val cleanerOffset = net.darkmeow.common.UNSAFE.objectFieldOffset(cleanerField)
        val cleanMethod = cleanerField.type.getDeclaredMethod("clean")
        cleanMethod.isAccessible = true
        Consumer<ByteBuffer> { buffer ->
            net.darkmeow.common.UNSAFE.getObject(buffer, cleanerOffset)?.let {
                cleanMethod.invoke(it)
            }
        }
    }
}

fun ByteBuffer.free() {
    net.darkmeow.common.FREE_FUNC.accept(this)
}

