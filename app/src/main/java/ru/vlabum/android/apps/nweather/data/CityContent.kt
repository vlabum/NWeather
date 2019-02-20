package ru.vlabum.android.apps.nweather.data

import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 */
object CityContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<CityItem> = ArrayList()

    init {
    }

    fun addItem(item: CityItem) {
        ITEMS.add(item)
    }

    fun getItemByName(name: String?): CityItem? {
        for (item in ITEMS) {
            if (item.name == name)
                return item
        }
        return null
    }

}
