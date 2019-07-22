package yocxli.gallerysample.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MediaFileEntity::class], version = 1)
abstract class MediaFileDatabase : RoomDatabase() {
    abstract fun mediaFileDao(): MediaFileDao
}