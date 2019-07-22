package yocxli.gallerysample.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MediaFileDao {
    @Query("SELECT * FROM media_files ORDER BY shooting_time DESC")
    fun listAll(): LiveData<List<MediaFileEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<MediaFileEntity>)
}