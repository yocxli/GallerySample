package yocxli.gallerysample.domain.usecase

import androidx.lifecycle.LiveData
import yocxli.gallerysample.domain.entity.MediaFile
import yocxli.gallerysample.domain.repository.MediaRepository

class ListAll(val mediaRepository: MediaRepository) : UseCase<LiveData<List<MediaFile>>> {
    override suspend fun execute(): LiveData<List<MediaFile>> {
        return mediaRepository.listAll()
    }
}