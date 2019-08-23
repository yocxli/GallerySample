package yocxli.gallerysample.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import yocxli.gallerysample.domain.entity.ImageFile
import yocxli.gallerysample.domain.entity.MediaFile
import yocxli.gallerysample.domain.entity.Resolution
import yocxli.gallerysample.domain.entity.VideoFile
import yocxli.gallerysample.domain.repository.MediaRepository

class MediaRepositoryImpl(
    private val remoteDataStore: MediaDataStore,
    private val localDataStore: MediaDataStore
) : MediaRepository {

    override fun listAll(): LiveData<List<MediaFile>> {
        return Transformations.map(localDataStore.listAll()) {
            it.map {
                when (it.type) {
                    MediaFileEntityType.IMAGE.ordinal -> createImageFile(it)
                    MediaFileEntityType.VIDEO.ordinal -> createVideoFile(it)
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }

    override suspend fun sync() {
        localDataStore.register(remoteDataStore.fetch())
    }

    override suspend fun find(uri: String): LiveData<MediaFile> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun createImageFile(entity: MediaFileEntity): ImageFile = ImageFile(
        name = entity.name,
        uri = entity.uri,
        mimeType = entity.mimeType,
        size = entity.size,
        takenTime = entity.takenTime,
        resolution = Resolution(entity.width, entity.height)
    )

    private fun createVideoFile(entity: MediaFileEntity) = VideoFile(
        name = entity.name,
        uri = entity.uri,
        mimeType = entity.mimeType,
        size = entity.size,
        takenTime = entity.takenTime,
        resolution = Resolution(entity.width, entity.height),
        duration = entity.duration ?: 0
    )
}