package yocxli.gallerysample.ui.list.dummy

import yocxli.gallerysample.domain.entity.ImageFile
import yocxli.gallerysample.domain.entity.MediaFile
import yocxli.gallerysample.domain.entity.Resolution
import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    val ITEMS: MutableList<Any> = ArrayList()

    val ITEM_MAP: MutableMap<String, Any> = HashMap()

    private val COUNT = 25

    init {
        ITEMS.add("Section 1")
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(
                createDummyItem(
                    i
                )
            )
        }
        ITEMS.add("Section 2")
        for (i in (COUNT + 1)..(COUNT + COUNT)) {
            addItem(
                createDummyItem(i)
            )
        }
    }

    private fun addItem(item: MediaFile) {
        ITEMS.add(item)
        ITEM_MAP.put(item.uri, item)
    }

    private fun createDummyItem(position: Int): MediaFile {
        return ImageFile(
            "${position.toString()}.jpg",
            "Item " + position,
            "image/jpeg",
            3000,
            System.currentTimeMillis(),
            Resolution(300, 600)
        )
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

}
