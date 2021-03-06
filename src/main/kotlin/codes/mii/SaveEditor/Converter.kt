package codes.mii.SaveEditor

import codes.mii.SaveEditor.Types.*
import java.io.File
import java.io.IOException
import java.math.BigInteger
import java.nio.file.Files


class Converter {

    fun compoundToByte(data: NBT_Compound): ByteArray {
        val byteArray = mutableListOf<Byte>()



        val bytes = ByteArray(byteArray.count())
        byteArray.forEachIndexed { index, byte ->

        }

        return
    }

    fun nbtByteToBytes(data: NBT_Byte): ByteArray {

    }




















    @ExperimentalUnsignedTypes
    fun convert(byte: ByteArray): NBT_Compound {
        var result = NBT_Compound(0, "")
        val byteSize = byte.size
        var readingPosition = 0

        try {
            result = byteToCompound(byte, readingPosition).first
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun byteToNbtByte(byte: ByteArray, position: Int, disableName: Boolean = false): Pair<NBT_Byte, Int> {
        var readingPosition = position
        var nameLength = 0
        var name = ""

        val nowByte = byte[readingPosition]

        if (!disableName) {
            nameLength = readMultiByteHex(byte, readingPosition, 2).toInt()
            readingPosition += 2
            name = byteToString(byte, readingPosition, nameLength)
            readingPosition += nameLength
        }
        readingPosition++

        val byteData = NBT_Byte(nameLength.toByte(), name, byte[readingPosition])

        return byteData to readingPosition
    }

    @ExperimentalUnsignedTypes
    fun byteToShort(byte: ByteArray, position:Int, disableName: Boolean = false): Pair<NBT_Short, Int> {
        var readingPosition = position
        var nameLength = 0
        var name = ""

        val nowByte = byte[readingPosition]

        if (!disableName) {
            nameLength = readMultiByteHex(byte, readingPosition, 2).toInt()
            readingPosition += 2
            name = byteToString(byte, readingPosition, nameLength)
            readingPosition += nameLength
        }

        val short = NBT_Short(nameLength.toByte(), name, readMultiByteHex(byte, readingPosition, 2).toInt())
        readingPosition += 2

        return short to readingPosition
    }

    fun byteToInt(byte: ByteArray, position:Int, disableName: Boolean = false): Pair<NBT_Int, Int> {
        var readingPosition = position
        var nameLength = 0
        var name = ""

        val nowByte = byte[readingPosition]

        if (!disableName) {
            nameLength = readMultiByteHex(byte, readingPosition, 2).toInt()
            readingPosition += 2
            name = byteToString(byte, readingPosition, nameLength)
            readingPosition += nameLength
        }

        val int = NBT_Int(nameLength.toByte(), name, readMultiByteHex(byte, readingPosition, 4).toInt())
        readingPosition += 4

        return int to readingPosition
    }

    fun byteToLong(byte: ByteArray, position:Int, disableName: Boolean = false): Pair<NBT_Long, Int> {
        var readingPosition = position
        var nameLength = 0
        var name = ""

        val nowByte = byte[readingPosition]

        if (!disableName) {
            nameLength = readMultiByteHex(byte, readingPosition, 2).toInt()
            readingPosition += 2
            name = byteToString(byte, readingPosition, nameLength)
            readingPosition += nameLength
        }

        val long = NBT_Long(nameLength.toByte(), name, readMultiByteHex(byte, readingPosition, 8))
        readingPosition += 8

        return long to readingPosition
    }

    fun byteToFloat(byte: ByteArray, position:Int, disableName: Boolean = false): Pair<NBT_Float, Int> {
        var readingPosition = position
        var nameLength = 0
        var name = ""

        val nowByte = byte[readingPosition]

        if (!disableName) {
            nameLength = readMultiByteHex(byte, readingPosition, 2).toInt()
            readingPosition += 2
            name = byteToString(byte, readingPosition, nameLength)
            readingPosition += nameLength
        }

        val float = NBT_Float(nameLength.toByte(), name, readMultiByteHex(byte, readingPosition, 4).toFloat())
        readingPosition += 4

        return float to readingPosition
    }

    fun byteToDouble(byte: ByteArray, position:Int, disableName: Boolean = false): Pair<NBT_Double, Int> {
        var readingPosition = position
        var nameLength = 0
        var name = ""

        val nowByte = byte[readingPosition]

        if (!disableName) {
            nameLength = readMultiByteHex(byte, readingPosition, 2).toInt()
            readingPosition += 2
            name = byteToString(byte, readingPosition, nameLength)
            readingPosition += nameLength
        }

        val double = NBT_Double(nameLength.toByte(), name, readMultiByteHex(byte, readingPosition, 8).toDouble())
        readingPosition += 8

        return double to readingPosition
    }

    fun byteToByteArray(byte: ByteArray, position:Int, disableName: Boolean = false): Pair<NBT_Byte_Array, Int> {
        var readingPosition = position
        var nameLength = 0
        var name = ""

        val nowByte = byte[readingPosition]

        if (!disableName) {
            nameLength = readMultiByteHex(byte, readingPosition, 2).toInt()
            readingPosition += 2
            name = byteToString(byte, readingPosition, nameLength)
            readingPosition += nameLength
        }


        val listLength = readMultiByteHex(byte, readingPosition, 4).toInt()
        readingPosition += 4
        val byteList: MutableList<Byte> = mutableListOf()
        repeat(listLength) {
            readingPosition++
            byteList.add(byte[readingPosition])
        }

        return NBT_Byte_Array(nameLength.toByte(), name, byteList) to readingPosition
    }

    fun byteToString(byte: ByteArray, position:Int, disableName: Boolean = false): Pair<NBT_String, Int> {
        var readingPosition = position
        var nameLength = 0
        var name = ""

        val nowByte = byte[readingPosition]

        if (!disableName) {
            nameLength = readMultiByteHex(byte, readingPosition, 2).toInt()
            readingPosition += 2
            name = byteToString(byte, readingPosition, nameLength)
            readingPosition += nameLength
        }


        val textLength = readMultiByteHex(byte, readingPosition, 2).toInt()
        readingPosition += 2

        val text = byteToString(byte, readingPosition, textLength)
        readingPosition += textLength

        return NBT_String(nameLength.toByte(), name, textLength.toByte(), text) to readingPosition
    }

    @ExperimentalUnsignedTypes
    fun byteToList(byte: ByteArray, position:Int, disableName: Boolean = false): Pair<NBT_List, Int> {
        var readingPosition = position
        var nameLength = 0
        var name = ""

        val nowByte = byte[readingPosition]

        if (!disableName) {
            nameLength = readMultiByteHex(byte, readingPosition, 2).toInt()
            readingPosition += 2
            name = byteToString(byte, readingPosition, nameLength)
            readingPosition += nameLength
        }

        readingPosition++
        val list = NBT_List(nameLength.toByte(), name, mutableListOf())

        val dataType = byte[readingPosition]



        val listLength = readMultiByteHex(byte, readingPosition, 4).toInt()
        readingPosition += 4

        repeat(listLength) {
            val (data, pos) = (when(dataType.toInt()) {
                1 -> byteToNbtByte(byte, readingPosition, true)
                2 -> byteToShort(byte, readingPosition, true)
                3 -> byteToInt(byte, readingPosition, true)
                4 -> byteToLong(byte, readingPosition, true)
                5 -> byteToFloat(byte, readingPosition, true)
                6 -> byteToDouble(byte, readingPosition, true)
                7 -> byteToByteArray(byte, readingPosition, true)
                8 -> byteToString(byte, readingPosition, true)
                9 -> byteToList(byte, readingPosition, true)
                10 -> byteToCompound(byte, readingPosition, true)

                else -> NBT_End() to readingPosition
            })

            list.data.add(data)
            readingPosition = pos
        }

        return list to readingPosition
    }

    @ExperimentalUnsignedTypes
    fun byteToIntArray(byte: ByteArray, position:Int, disableName: Boolean = false): Pair<NBT_Int_Array, Int> {
        var readingPosition = position
        var nameLength = 0
        var name = ""

        val nowByte = byte[readingPosition]

        if (!disableName) {
            nameLength = readMultiByteHex(byte, readingPosition, 2).toInt()
            readingPosition += 2
            name = byteToString(byte, readingPosition, nameLength)
            readingPosition += nameLength
        }


        val listLength = readMultiByteHex(byte, readingPosition, 4).toInt()
        readingPosition += 4
        val intList: MutableList<Int> = mutableListOf()
        repeat(listLength) {
            intList.add(readMultiByteHex(byte, readingPosition, 4).toInt())
            readingPosition += 4
        }

        return NBT_Int_Array(nameLength.toByte(), name, intList) to readingPosition
    }

    @ExperimentalUnsignedTypes
    fun byteToCompound(byte: ByteArray, position: Int, disableName: Boolean = false): Pair<NBT_Compound, Int> {
        var readingPosition = position
        var nameLength = 0
        var name = ""

        val nowByte = byte[readingPosition]

        if (!disableName) {
            nameLength = readMultiByteHex(byte, readingPosition, 2).toInt()
            readingPosition += 2
            name = byteToString(byte, readingPosition, nameLength)
            readingPosition += nameLength
        }

        val compound = NBT_Compound(nameLength.toByte(), name)

        readingPosition++
        while(byte[readingPosition] != 0.toByte()) {
            println("Converting id ${byte[readingPosition]}, position $readingPosition")
            when(byte[readingPosition].toInt()) {
                1 -> {
                    val (data, pos) = byteToNbtByte(byte, readingPosition)
                    readingPosition = pos
                    compound.data.add(data)
                }

                2 -> {
                    val (data, pos) = byteToShort(byte, readingPosition)
                    readingPosition = pos
                    compound.data.add(data)
                }

                3 -> {
                    val (data, pos) = byteToInt(byte, readingPosition)
                    readingPosition = pos
                    compound.data.add(data)
                }

                4 -> {
                    val (data, pos) = byteToLong(byte, readingPosition)
                    readingPosition = pos
                    compound.data.add(data)
                }

                5 -> { // FLOAT
                    val (data, pos) = byteToFloat(byte, readingPosition)
                    readingPosition = pos
                    compound.data.add(data)
                }

                6 -> { // DOUBLE
                    val (data, pos) = byteToDouble(byte, readingPosition)
                    readingPosition = pos
                    compound.data.add(data)
                }

                7 -> { // BYTE_ARRAY
                    val (data, pos) = byteToByteArray(byte, readingPosition)
                    readingPosition = pos
                    compound.data.add(data)
                }

                8 -> { // STRING
                    val (data, pos) = byteToString(byte, readingPosition)
                    readingPosition = pos
                    compound.data.add(data)
                }

                9 -> {
                    val (data, pos) = byteToList(byte, readingPosition)
                    readingPosition = pos
                    compound.data.add(data)
                }

                10 -> {
                    val (data, pos) = byteToCompound(byte, readingPosition)
                    readingPosition = pos
                    compound.data.add(data)
                }

                11 -> {
                    val (data, pos) = byteToIntArray(byte, readingPosition)
                    readingPosition = pos
                    compound.data.add(data)
                }
            }

            readingPosition++
        }

        return compound to readingPosition
    }



    @ExperimentalUnsignedTypes
    fun readMultiByteHex(byte: ByteArray, position: Int, count: Int): Long {
        var readingPosition = position
        val multiByteText = StringBuilder()

        println(byte.copyOfRange(position + 1, position + count + 1).joinToString())
        println(BigInteger(byte.copyOfRange(position + 1, position + count + 1)).toString())

        return BigInteger(byte.copyOfRange(position + 1, position + count + 1)).toLong()
    }

    private fun hexToAscii(hexStr: String): String? {
        val output = StringBuilder("")
        var i = 0
        while (i < hexStr.length) {
            val str = hexStr.substring(i, i + 2)
            output.append(str.toInt(16).toChar())
            i += 2
        }
        return output.toString()
    }

    fun byteToString(byte: ByteArray, position: Int, count: Int): String {
        var readingPosition = position
        val name = StringBuilder()

        repeat(count) {
            readingPosition++
            name.append(hexToAscii(byte[readingPosition].toString(16)))
        }

        return name.toString()
    }

    @Throws(IOException::class)
    fun convertFile(file: File): ByteArray? {
        return Files.readAllBytes(file.toPath())
    }

    fun regionToByte(file: File): ByteArray {
        return ByteArray(0)
    }
}