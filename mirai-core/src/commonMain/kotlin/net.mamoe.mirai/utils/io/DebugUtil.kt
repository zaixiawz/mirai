package net.mamoe.mirai.utils.io

import kotlinx.io.core.*
import net.mamoe.mirai.utils.DefaultLogger
import net.mamoe.mirai.utils.MiraiLogger


internal object DebugLogger : MiraiLogger by DefaultLogger("Packet Debug")

internal fun debugPrintln(any: Any?) = DebugLogger.debug(any)

internal fun ByteArray.debugPrint(name: String): ByteArray {
    DebugLogger.debug(name + "=" + this.toUHexString())
    return this
}

@Deprecated("Low efficiency, only for debug purpose", ReplaceWith("this"))
internal fun IoBuffer.debugPrint(name: String): IoBuffer {
    val readBytes = this.readBytes()
    DebugLogger.debug(name + "=" + readBytes.toUHexString())
    return readBytes.toIoBuffer()
}

@Deprecated("Low efficiency, only for debug purpose", ReplaceWith("discardExact(n)"))
internal fun Input.debugDiscardExact(n: Number, name: String = "") {
    DebugLogger.debug("Discarded($n) $name=" + this.readBytes(n.toInt()).toUHexString())
}

@Deprecated("Low efficiency, only for debug purpose", ReplaceWith("this"))
internal fun ByteReadPacket.debugPrint(name: String = ""): ByteReadPacket {
    val bytes = this.readBytes()
    DebugLogger.debug("ByteReadPacket $name=" + bytes.toUHexString())
    return bytes.toReadPacket()
}

@Deprecated("Low efficiency, only for debug purpose", ReplaceWith("this"))
internal fun ByteReadPacket.debugColorizedPrint(name: String = "", ignoreUntilFirstConst: Boolean = false): ByteReadPacket {
    val bytes = this.readBytes()
    bytes.printColorizedHex(name, ignoreUntilFirstConst)
    return bytes.toReadPacket()
}

@Deprecated("Low efficiency, only for debug purpose", ReplaceWith(" "))
internal fun BytePacketBuilder.debugColorizedPrintThis(name: String = "") {
    val data = this.build().readBytes()
    data.printColorizedHex(name)
    this.writeFully(data)
}

@Deprecated("Low efficiency, only for debug purpose", ReplaceWith(" "))
internal fun BytePacketBuilder.debugColorizedPrintThis(name: String = "", compareTo: String? = null) {
    val data = this.build().readBytes()
    data.printColorizedHex(name, compareTo = compareTo)
    this.writeFully(data)
}

@Deprecated("Low efficiency, only for debug purpose", ReplaceWith(" "))
internal fun BytePacketBuilder.debugPrintThis(name: String = "") {
    val data = this.build().readBytes()
    data.debugPrint(name)
    this.writeFully(data)
}

internal fun String.printStringFromHex() {
    println(this.hexToBytes().stringOfWitch())
}

internal fun ByteArray.printColorizedHex(name: String = "", ignoreUntilFirstConst: Boolean = false, compareTo: String? = null) {
    println("Hex比较 `$name`")
    if (compareTo != null) {
        println(printCompareHex(toUHexString(), compareTo))
    } else {
        println(toUHexString().printColorize(ignoreUntilFirstConst))
    }
    println()
}