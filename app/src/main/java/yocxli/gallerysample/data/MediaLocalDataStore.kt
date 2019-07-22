package yocxli.gallerysample.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import yocxli.gallerysample.domain.entity.ImageFile
import yocxli.gallerysample.domain.entity.MediaFile
import yocxli.gallerysample.domain.entity.Resolution
import yocxli.gallerysample.domain.entity.VideoFile

class MediaLocalDataStore(
    private val db: MediaFileDatabase
) : MediaDataStore {

    companion object {
        private var instance: MediaLocalDataStore? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: MediaLocalDataStore(
                Room.databaseBuilder(context, MediaFileDatabase::class.java, "media.db").build()
            )
        }.also { instance = it }
    }

    override fun listAll(): LiveData<List<MediaFile>> = Transformations.map(db.mediaFileDao().listAll()) {
        it.map {
            when (it.type) {
                MediaFileEntityType.IMAGE.ordinal -> createImageFile(it)
                MediaFileEntityType.VIDEO.ordinal -> createVideoFile(it)
                else -> throw IllegalArgumentException()
            }
        }
    }

    private fun createImageFile(entity: MediaFileEntity): ImageFile = ImageFile(
        name = entity.name,
        uri = entity.uri,
        mimeType = entity.mimeType,
        size = entity.size,
        takenTime = entity.shootingTime,
        resolution = Resolution(entity.width, entity.height)
    )

    private fun createVideoFile(entity: MediaFileEntity) = VideoFile(
        name = entity.name,
        uri = entity.uri,
        mimeType = entity.mimeType,
        size = entity.size,
        takenTime = entity.shootingTime,
        resolution = Resolution(entity.width, entity.height),
        duration = entity.duration ?: 0
    )

    override suspend fun fetch(): List<MediaFile> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun register(list: List<MediaFile>) {
        db.mediaFileDao().insertAll(list.map {
            MediaFileEntity(
                type = when (it) {
                    is ImageFile -> MediaFileEntityType.IMAGE.ordinal
                    is VideoFile -> MediaFileEntityType.VIDEO.ordinal
                    else -> throw IllegalArgumentException("Unknown media type: $it")
                },
                uri = it.uri,
                name = it.name,
                mimeType = it.mimeType,
                size = it.size,
                shootingTime = it.takenTime,
                width = it.resolution.width,
                height = it.resolution.height
            )
        })
    }
}