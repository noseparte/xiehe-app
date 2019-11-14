package sample

import javafx.scene.control.Alert
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object CollectionUtils {
    var mAlert: Alert? = null

    fun readText(fileName: String): String {
        val input = FileInputStream(File(fileName))
        val str = StringBuilder()
        val bytes = ByteArray(8192)
        do {
            val size = input.read(bytes)
            if (size != -1) {
                str.append(String(bytes.copyOfRange(0, size), Charsets.UTF_8))
            }
        } while (size != -1)
        input.close()
        return str.toString()
    }

    fun writeText(fileName: File, string: String) {
        if (!fileName.exists()) fileName.createNewFile()
        val out = FileOutputStream(fileName)
        out.write(string.toByteArray(Charsets.UTF_8))
        out.close()
    }
}