package com.alchemtech.playlistmaker.data.cover_repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class CoversRepositoryImpl(private val context: Context) : CoversRepository {

    private companion object {
        const val DIRECTORY_NAME = "playlist_maker"
        const val FILE_NAME = "cover_of_Playlist_"
        const val FILE_EXTENSION = ".jpg"
        const val COMPRESS_QUALITY = 30
    }

    override suspend fun saveCover(name: String, uri: Uri?): Uri? {
        uri?.let {
            val filePath =
                File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), DIRECTORY_NAME)
            if (!filePath.exists()) {
                filePath.mkdirs()
            }
            val file = File(filePath, "$FILE_NAME$name$FILE_EXTENSION")
            val inputStream = context.contentResolver.openInputStream(it)
            val outputStream = withContext(Dispatchers.IO) {
                FileOutputStream(file)
            }
            BitmapFactory
                .decodeStream(inputStream)
                .compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, outputStream)
            inputStream?.close()
            outputStream.close()
            return file.toUri()
        }
        return null
    }
}