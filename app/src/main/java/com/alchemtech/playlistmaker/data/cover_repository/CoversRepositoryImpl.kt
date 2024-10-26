package com.alchemtech.playlistmaker.data.cover_repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

    override suspend fun saveCover(id: Long, uri: String?): String? {
        if (!uri.isNullOrEmpty()) if (uri.contains( fileNameRule(id).toRegex()) ) {
            deleteCover(id)
            val filePath =
                File(
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    DIRECTORY_NAME
                )
            if (!filePath.exists()) {
                filePath.mkdirs()
            }
            val file = File(filePath, fileNameRule(id))
            val inputStream = context.contentResolver.openInputStream(uri.toUri())
            val outputStream = withContext(Dispatchers.IO) {
                FileOutputStream(file)
            }
            BitmapFactory
                .decodeStream(inputStream)
                .compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, outputStream)
            inputStream?.close()
            outputStream.close()
            return file.toString()
        } else {
            return uri
        } else return null
    }

    override suspend fun deleteCover(id: Long): Boolean {
        var deleted = false
        val filePath =
            File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                DIRECTORY_NAME
            )

        filePath.listFiles()?.map {
            if (it.name == fileNameRule(id)) {
                deleted = it.delete()
            }
        }
        return deleted
    }
    private fun fileNameRule(id: Long):String {
        return "$FILE_NAME$id$FILE_EXTENSION" }
}