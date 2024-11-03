package com.safronov.apex.example.interesting_fact_about_number.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.safronov.apex.example.interesting_fact_about_number.data.model.output.FactAboutNumberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FactAboutNumberDao {

    @Insert
    fun insertFactAboutNumber(fact: FactAboutNumberEntity)

    @Query("SELECT * FROM facts_about_numbers ORDER BY uid DESC")
    fun getCachedFacts(): Flow<List<FactAboutNumberEntity>>

}