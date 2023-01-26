package com.example.alarmgroups.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.alarmgroups.data.model.AlarmEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

@Database(
    entities = [AlarmEntity::class],
    version = 1,
)
@TypeConverters(Converter::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract val dao: AlarmDao
}

class Converter {

    private val moshi = Moshi.Builder().build()
    private val type: ParameterizedType =
        Types.newParameterizedType(List::class.java, Int::class.javaObjectType)
    private val jsonAdapter: JsonAdapter<List<Int>> = moshi.adapter<List<Int>>(type)


    @TypeConverter
    fun listToString(list: List<Int>?): String {
        return jsonAdapter.toJson(list)
    }

    @TypeConverter
    fun stringToList(jsonString: String): List<Int>? {
        return jsonAdapter.fromJson(jsonString)
    }

}