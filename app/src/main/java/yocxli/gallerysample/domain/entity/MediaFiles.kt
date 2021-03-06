package yocxli.gallerysample.domain.entity


interface MediaListItem

data class SectionLabel(
    val title: String,
    val description: String
) : MediaListItem

/**
 * dataクラスは継承できないのでinterfaceで対応
 */
interface MediaFile : MediaListItem {
    val name: String
    val uri: String
    val mimeType: String
    val size: Long
    val takenTime: Unixtime
    val resolution: Resolution
}

data class Resolution(val width: Int, val height: Int)

data class ImageFile(
    override val name: String,
    override val uri: String,
    override val mimeType: String,
    override val size: Long,
    override val takenTime: Unixtime,
    override val resolution: Resolution
) : MediaFile

data class VideoFile(
    override val name: String,
    override val uri: String,
    override val mimeType: String,
    override val size: Long,
    override val takenTime: Unixtime,
    override val resolution: Resolution,
    val duration: Int
) : MediaFile