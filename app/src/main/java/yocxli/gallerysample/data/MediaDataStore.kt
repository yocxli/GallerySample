package yocxli.gallerysample.data

import androidx.lifecycle.LiveData

interface MediaDataStore {
    fun listAll(): LiveData<List<MediaFileEntity>>

    suspend fun fetch(): List<MediaFileEntity>
    suspend fun register(list: List<MediaFileEntity>)
}