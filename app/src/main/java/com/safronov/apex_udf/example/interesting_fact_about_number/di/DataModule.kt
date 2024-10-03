package com.safronov.apex_udf.example.interesting_fact_about_number.di

import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import com.safronov.apex_udf.example.interesting_fact_about_number.data.db.FactAboutNumberDB
import com.safronov.apex_udf.example.interesting_fact_about_number.data.db.FactAboutNumberDao
import com.safronov.apex_udf.example.interesting_fact_about_number.data.repository.NumberRepositoryImpl
import com.safronov.apex_udf.example.interesting_fact_about_number.data.service.FactAboutNumberService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideFactAboutNumberDB(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context = context,
        klass = FactAboutNumberDB::class.java,
        name = "fact_about_numbers.db"
    ).build()

    @Provides
    @Singleton
    fun provideFactAboutNumberDao(
        factAboutNumberDB: FactAboutNumberDB
    ) = factAboutNumberDB.factAboutNumberDao()

    @Provides
    @Singleton
    fun provideFactAboutNumberSerivce() = Retrofit.Builder()
        .baseUrl(FactAboutNumberService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FactAboutNumberService::class.java)

    @Provides
    @Singleton
    fun provideNumberRepository(
        factAboutNumberDao: FactAboutNumberDao,
        factAboutNumberService: FactAboutNumberService
    ) = NumberRepositoryImpl(
        factAboutNumberService = factAboutNumberService,
        factAboutNumberDao = factAboutNumberDao
    )

}