package com.safronov.apex_udf.example.interesting_fact_about_number.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.safronov.apex_udf.example.interesting_fact_about_number.data.db.FactAboutNumberDB.Companion.DB_VERSION
import com.safronov.apex_udf.example.interesting_fact_about_number.data.model.output.FactAboutNumberEntity

@Database(entities = [FactAboutNumberEntity::class], version = DB_VERSION)
abstract class FactAboutNumberDB: RoomDatabase() {

    abstract fun factAboutNumberDao(): FactAboutNumberDao

    companion object {
        const val DB_VERSION = 1
    }

}