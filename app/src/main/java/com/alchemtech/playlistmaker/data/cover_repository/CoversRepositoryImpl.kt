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
        deleteCover(id).let {
return withContext(Dispatchers.IO){
            if (uri != "null" && !uri.isNullOrEmpty()) if (!uri.contains(fileNameRule(id).toRegex())) {
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
                val outputStream = FileOutputStream(file)

                BitmapFactory
                    .decodeStream(inputStream)
                    .compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, outputStream)
                    inputStream?.close()
                    outputStream.close()
                println(file)
                 file.toString()
            } else {
                 uri
            } else  null
        }}
    }
    override suspend fun deleteCover(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            var deleted = false
            val filePath =
                File(
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    DIRECTORY_NAME
                )

            filePath.listFiles()?.map {
                if (it.name == fileNameRule(id)) {
                    println(it)
                    deleted = it.delete()
                    println(deleted)
                }
            }
            deleted
        }
    }

    private fun fileNameRule(id: Long): String {
        return "$FILE_NAME$id$FILE_EXTENSION"
    }
}