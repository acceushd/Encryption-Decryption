package encryptdecrypt

import java.io.File

fun encrypt(s: String, key: Int): String {
    val offset = key % 26
    if (offset == 0) return s
    var d: Char
    val chars = CharArray(s.length)
    for ((index, c) in s.withIndex()) {
        if (c in 'A'..'Z') {
            d = c + offset
            if (d > 'Z') d -= 26
        }
        else if (c in 'a'..'z') {
            d = c + offset
            if (d > 'z') d -= 26
        }
        else
            d = c
        chars[index] = d
    }
    return chars.joinToString("")
}

fun decrypt(s: String, key: Int): String {
    return encrypt(s, 26 - key)
}

fun encryption(string: String, key: Int): String {
    var string2 = ""
    for (i in string.indices) {
        string2 += (string[i].code + key).toChar()
    }
    return string2
}

fun decryption(string: String, key: Int): String {
    var string2 = ""
    for (i in string.indices) {
        string2 += (string[i].code - key).toChar()
    }
    return string2
}

fun main(args: Array<String>) {
    var myFile: String
    var outFile = ""
    var m = 0
    var mode = "enc"
    var k = 0
    var kValue = 0
    var d = 0
    var dI = 0
    var dO = 0
    var dString = ""
    var alg = "shift"
    for (i in args.indices) {
        when (args[i]) {
            "-mode" -> {
                m += 1
                if (args[i + 1] == "dec") mode = "dec"
            }

            "-key" -> {
                k += 1
                kValue = args[i + 1].toInt()
            }

            "-data" -> {
                d += 1
                dString = args[i + 1]
            }

            "-in" -> {
                dI += 1
                myFile = args[i + 1]
                dString = File(myFile).readText()
            }

            "-out" -> {
                dO += 1
                outFile = args[i + 1]
            }

            "-alg" -> {
                alg = args[i + 1]
            }

            else -> continue
        }
    }
    dString = if (alg == "unicode") {
        if (mode == "enc") encryption(dString, kValue)
        else decryption(dString, kValue)
    } else {
        if (mode == "enc") encrypt(dString, kValue)
        else decrypt(dString, kValue)
    }
    if (d == 1) {
        println(dString)
    } else File(outFile).writeText(dString)
}