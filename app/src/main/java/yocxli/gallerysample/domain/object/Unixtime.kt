package yocxli.gallerysample.domain.`object`

data class Unixtime(val value: Long) {
    init {
        require(value >= 0)
    }

    operator fun compareTo(unixtime: Unixtime): Int = value.compareTo(unixtime.value)
}
