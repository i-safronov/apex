package com.safronov.apex_udf.example.interesting_fact_about_number.data.model.output

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts_about_numbers")
data class FactAboutNumberEntity(
    @PrimaryKey val id: Int? = -1,
    @ColumnInfo val text: String?,
    @ColumnInfo val number: Long?
)
