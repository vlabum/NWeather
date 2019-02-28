package ru.vlabum.android.apps.nweather.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.vlabum.android.apps.nweather.DataStorage

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "nweather.db"
        val DATABASE_VERSION = 1

        private val TABLE_CITY = "city"

        val COLUMN_CITY_ID = "_id"
        val COLUMN_CITY_NAME = "name"
        val COLUMN_CITY_TEMP = "temerature"
        val COLUMN_CITY_HUMIDITY = "humidity"
        val COLUMN_CITY_PRESSURE = "pressure"
        val COLUMN_CITY_DESCR = "descr"

        val COLUMNS_CITY = arrayOf(
            COLUMN_CITY_ID,
            COLUMN_CITY_NAME,
            COLUMN_CITY_TEMP,
            COLUMN_CITY_HUMIDITY,
            COLUMN_CITY_PRESSURE,
            COLUMN_CITY_DESCR
        )
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlString =
            "create table " + TABLE_CITY + " (" +
                    COLUMN_CITY_ID + " integer primary key autoincrement," +
                    COLUMN_CITY_NAME + " text not null," +
                    COLUMN_CITY_TEMP + " integer," +
                    COLUMN_CITY_HUMIDITY + " integer," +
                    COLUMN_CITY_PRESSURE + " integer," +
                    COLUMN_CITY_DESCR + " text)"
        db?.execSQL(sqlString)

        val sqlInitData = "insert into %s (%s) values ('%s')"
        db?.execSQL(String.format(sqlInitData, TABLE_CITY, COLUMN_CITY_NAME, "Chelyabinsk"))
        db?.execSQL(String.format(sqlInitData, TABLE_CITY, COLUMN_CITY_NAME, "Moscow"))
        db?.execSQL(String.format(sqlInitData, TABLE_CITY, COLUMN_CITY_NAME, "London"))
        db?.execSQL(String.format(sqlInitData, TABLE_CITY, COLUMN_CITY_NAME, "Paris"))
        db?.execSQL(String.format(sqlInitData, TABLE_CITY, COLUMN_CITY_NAME, "Berlin"))
        db?.execSQL(String.format(sqlInitData, TABLE_CITY, COLUMN_CITY_NAME, "Madrid"))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addCity(sqliteWritableDatabase: SQLiteDatabase, name: String) {
        val values = ContentValues()
        values.put(COLUMN_CITY_NAME, name)
        val sqlStr = StringBuilder()
            .append("insert into ")
            .append(TABLE_CITY)
            .append("(")
            .append(COLUMN_CITY_NAME)
            .append(")")
            .append(" select '")
            .append(name)
            .append("' where not exists(select * from ")
            .append(TABLE_CITY)
            .append(" where ")
            .append(COLUMN_CITY_NAME)
            .append("= '")
            .append(name)
            .append("'")
        sqliteWritableDatabase.execSQL(sqlStr.toString())
    }

    fun getAllCities(database: SQLiteDatabase): ArrayList<CityItem> {
        CityContent.addItem(CityItem(DataStorage.CITY_GPS, 0, 0, 0, ""))
        val cityItems = ArrayList<CityItem>()
        var cursor: Cursor? = null
        try {
            cursor = database.query(TABLE_CITY, COLUMNS_CITY, null, null, null, null, null, null)
            cursor.moveToFirst()
            if (!cursor.isAfterLast) {
                do {
                    val item = CityItem(
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5)
                    )
                    CityContent.addItem(item)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return cityItems
    }

    fun updateCityWeather(database: SQLiteDatabase, cityItem: CityItem) {
        val values = ContentValues()
        values.put(COLUMN_CITY_TEMP, cityItem.temerature)
        values.put(COLUMN_CITY_HUMIDITY, cityItem.humidity)
        values.put(COLUMN_CITY_PRESSURE, cityItem.pressure)
        values.put(COLUMN_CITY_DESCR, cityItem.descr)
        database.update(TABLE_CITY, values, "name='" + cityItem.name + "'", null)
    }

}