package yocxli.gallerysample.data

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import yocxli.gallerysample.domain.entity.ImageFile
import yocxli.gallerysample.domain.entity.MediaFile
import yocxli.gallerysample.domain.entity.Resolution
import yocxli.gallerysample.domain.entity.VideoFile

class MediaRemoteDataStore(
    private val resolver: ContentResolver
) : MediaDataStore {

    override fun listAll(): LiveData<List<MediaFile>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun fetch(): List<MediaFile> {
        val list = arrayListOf<MediaFile>()
        fetchImages(list)
        fetchVideos(list)
        return list
    }

    private fun fetchImages(list: ArrayList<MediaFile>) {
        val cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf("*"), null, null, null)
        cursor?.use {
            val displayNameIndex = it.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME)
            val idIndex = it.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
            val mimeTypeIndex = it.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.MIME_TYPE)
            val sizeIndex = it.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.SIZE)
            val dateTakenIndex = it.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)
            val widthIndex = it.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.WIDTH)
            val heightIndex = it.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.HEIGHT)
            while (it.moveToNext()) {
                list.add(
                    ImageFile(
                        name = it.getString(displayNameIndex),
                        uri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            it.getLong(idIndex)
                        ).toString(),
                        mimeType = it.getString(mimeTypeIndex),
                        size = it.getLong(sizeIndex),
                        takenTime = it.getLong(dateTakenIndex),
                        resolution = Resolution(it.getInt(widthIndex), it.getInt(heightIndex))
                    )
                )
            }
        }
    }

    private fun fetchVideos(list: ArrayList<MediaFile>) {
        val cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, arrayOf("*"), null, null, null)
        cursor?.use {
            val displayNameIndex = it.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME)
            val idIndex = it.getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID)
            val mimeTypeIndex = it.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.MIME_TYPE)
            val sizeIndex = it.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.SIZE)
            val dateTakenIndex = it.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATE_TAKEN)
            val widthIndex = it.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.WIDTH)
            val heightIndex = it.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.HEIGHT)
            val durationIndex = it.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION)
            while (it.moveToNext()) {
                list.add(
                    VideoFile(
                        name = it.getString(displayNameIndex),
                        uri = ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            it.getLong(idIndex)
                        ).toString(),
                        mimeType = it.getString(mimeTypeIndex),
                        size = it.getLong(sizeIndex),
                        takenTime = it.getLong(dateTakenIndex),
                        resolution = Resolution(it.getInt(widthIndex), it.getInt(heightIndex)),
                        duration = it.getInt(durationIndex)
                    )
                )
            }
        }
    }

    override suspend fun register(list: List<MediaFile>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}