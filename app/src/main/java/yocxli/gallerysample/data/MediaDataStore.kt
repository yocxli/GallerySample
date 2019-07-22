package yocxli.gallerysample.data

import androidx.lifecycle.LiveData
import yocxli.gallerysample.domain.entity.MediaFile

interface MediaDataStore {
    fun listAll(): LiveData<List<MediaFile>>

    suspend fun fetch(): List<MediaFile>
    suspend fun register(list: List<MediaFile>)
}