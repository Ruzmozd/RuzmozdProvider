package ir.hirkancorp.presenter.core.ImageUtils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale("fa")).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(imageFileName, ".jpg", externalCacheDir)
    return image
}

fun Uri.compressBitmapFile(): ByteArray {
    val inputStream = this.toFile().inputStream()
    val bitmap = BitmapFactory.decodeStream(inputStream)
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
    return ByteArrayInputStream(outputStream.toByteArray()).readBytes()
}

fun Context.compressImage(uri: Uri, quality: Int = 50): File {
    val inputStream = contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)

    val file = File(cacheDir, "compressed_img.jpg")
    val outputStream = FileOutputStream(file)

    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

    outputStream.flush()
    outputStream.close()

    return file
}

fun File.readBytes(): ByteArray {
    val inputStream = FileInputStream(this)
    val byteArrayOutputStream = ByteArrayOutputStream()
    val buffer = ByteArray(1024)
    var bytesRead: Int

    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
        byteArrayOutputStream.write(buffer, 0, bytesRead)
    }

    return byteArrayOutputStream.toByteArray()
}