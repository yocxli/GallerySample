package yocxli.gallerysample.domain.repository

import androidx.lifecycle.LiveData
import yocxli.gallerysample.domain.entity.MediaFile

interface MediaRepository {
    fun listAll(): LiveData<List<MediaFile>>
    fun find(uri: String): LiveData<MediaFile>
}
