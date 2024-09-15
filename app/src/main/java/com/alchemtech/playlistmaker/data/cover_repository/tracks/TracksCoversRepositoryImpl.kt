package com.alchemtech.playlistmaker.data.cover_repository.tracks

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class TracksCoversRepositoryImpl(private val context: Context) : TracksCoversRepository {

    private companion object {
        const val DIRECTORY_NAME = "playlist_maker"
        const val FILE_NAME = "cover_track_"
        const val FILE_EXTENSION = ".jpg"
        const val COMPRESS_QUALITY = 100
    }

    override suspend fun saveCover(name: String, uri: Uri?): Uri? {
        uri?.let {
            val filePath =
                File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), DIRECTORY_NAME)
            if (!filePath.exists()) {
                filePath.mkdirs()
            }

            var cover: Bitmap? = null
            CoroutineScope(Dispatchers.IO).launch {
                cover = (Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .submit()
                    .get())
            }
            val file = File(filePath, "$FILE_NAME$name$FILE_EXTENSION")
            val outputStream: OutputStream = FileOutputStream(file)
            cover?.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, outputStream)

            outputStream.close()
            return file.toUri()
        }
        return null
    }
}