package yocxli.gallerysample.data

import androidx.lifecycle.LiveData
import yocxli.gallerysample.domain.entity.MediaFile
import yocxli.gallerysample.domain.repository.MediaRepository

class MediaRepositoryImpl(
    private val remoteDataStore: MediaDataStore,
    private val localDataStore: MediaDataStore
) : MediaRepository {

    override fun listAll(): LiveData<List<MediaFile>> {
        return localDataStore.listAll()
    }

    override suspend fun sync() {
        localDataStore.register(remoteDataStore.fetch())
    }

    override suspend fun find(uri: String): LiveData<MediaFile> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}