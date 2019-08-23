package yocxli.gallerysample.domain.`object`

data class Unixtime(val value: Long) {
    init {
        require(value >= 0)
    }
}
