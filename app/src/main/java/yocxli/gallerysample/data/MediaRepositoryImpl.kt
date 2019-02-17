package yocxli.gallerysample.data

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import yocxli.gallerysample.domain.entity.ImageFile
import yocxli.gallerysample.domain.entity.MediaFile
import yocxli.gallerysample.domain.entity.Resolution
import yocxli.gallerysample.domain.entity.VideoFile
import yocxli.gallerysample.domain.repository.MediaRepository

class MediaRepositoryImpl(val context: Context) : MediaRepository {
    private val resolver = context.contentResolver
    private val listLiveData = MutableLiveData<List<MediaFile>>()

    override suspend fun listAll(): LiveData<List<MediaFile>> {
        val list: MutableList<MediaFile> = arrayListOf()

        val where = "${MediaStore.Files.FileColumns.MEDIA_TYPE} in (?, ?)"
        val whereArgs = arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )
        val order = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"
        val cursor = resolver.query(MediaStore.Files.getContentUri("external"), arrayOf("*"), where, whereArgs, order)
            ?: return listLiveData
        cursor.use {
            while (it.moveToNext()) {
                list.add(createMediaFile(it))
            }
        }
        listLiveData.value = list
        return listLiveData
    }

    private fun createMediaFile(cursor: Cursor): MediaFile {
        val mediaType = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE))
        return when (mediaType) {
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE -> {
                ImageFile(
                    name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)),
                    uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)),
                    mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)),
                    size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)),
                    takenTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED)),
                    resolution = Resolution(
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.WIDTH)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.HEIGHT))
                    )
                )
            }
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO -> {
                VideoFile(
                    name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)),
                    uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)),
                    mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)),
                    size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)),
                    takenTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED)),
                    resolution = Resolution(
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.WIDTH)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.HEIGHT))
                    ),
                    duration = 0
                )
            }
            else -> throw IllegalStateException("Unknown media type: $mediaType")
        }
    }

    override suspend fun find(uri: String): LiveData<MediaFile> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}