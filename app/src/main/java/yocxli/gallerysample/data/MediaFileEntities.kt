package yocxli.gallerysample.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "media_files", indices = [Index(value = ["uri"], unique = true)])
data class MediaFileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "uri") val uri: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "mime_type") val mimeType: String,
    @ColumnInfo(name = "size") val size: Long,
    @ColumnInfo(name = "shooting_time") val shootingTime: Long,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "duration") val duration: Int? = null
)

enum class MediaFileEntityType(val id: Int) {
    IMAGE(1), VIDEO(2)
}
