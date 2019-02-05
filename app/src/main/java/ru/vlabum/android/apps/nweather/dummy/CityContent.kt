package ru.vlabum.android.apps.nweather.dummy

import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object CityContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<CityItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, CityItem> = HashMap()

    init {
        addItem(CityItem("1", "Chelyabinsk", ""))
        addItem(CityItem("2", "Moscow", ""))
        addItem(CityItem("3", "London", ""))
        addItem(CityItem("4", "Paris", ""))
        addItem(CityItem("5", "Berlin", ""))
    }

    private fun addItem(item: CityItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(position: Int): CityItem {
        return CityItem(position.toString(), "Item " + position, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class CityItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}
