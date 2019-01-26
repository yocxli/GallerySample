package yocxli.gallerysample.domain.entity

/**
 * dataクラスは継承できないのでinterfaceで対応
 */
interface MediaFile {
    val name: String
    val uri: String
    val mimeType: String
    val size: Long
    val takenTime: Long
    val resolution: Resolution
}

data class Resolution(val width: Int, val height: Int)

data class ImageFile(
    override val name: String,
    override val uri: String,
    override val mimeType: String,
    override val size: Long,
    override val takenTime: Long,
    override val resolution: Resolution
) : MediaFile

data class VideoFile(
    override val name: String,
    override val uri: String,
    override val mimeType: String,
    override val size: Long,
    override val takenTime: Long,
    override val resolution: Resolution,
    val duration: Int
) : MediaFile