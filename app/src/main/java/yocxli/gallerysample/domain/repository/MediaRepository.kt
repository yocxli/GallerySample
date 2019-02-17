package yocxli.gallerysample.domain.repository

import androidx.lifecycle.LiveData
import yocxli.gallerysample.domain.entity.MediaFile

interface MediaRepository {
    suspend fun listAll(): LiveData<List<MediaFile>>
    suspend fun find(uri: String): LiveData<MediaFile>
}
