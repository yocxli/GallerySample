package yocxli.gallerysample.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room

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

    override fun listAll(): LiveData<List<MediaFileEntity>> = db.mediaFileDao().listAll()

    override suspend fun fetch(): List<MediaFileEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun register(list: List<MediaFileEntity>) {
        db.mediaFileDao().insertAll(list)
    }
}