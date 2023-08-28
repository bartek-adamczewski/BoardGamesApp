package edu.put.inf151764.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.put.inf151764.data.db.GamesDao
import edu.put.inf151764.data.db.GamesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun appDatabase(@ApplicationContext context: Context) : GamesDatabase = Room
        .databaseBuilder(context, GamesDatabase::class.java, "MyDb")
        .build()

    @Provides
    @Singleton
    fun gamesDao(gamesDatabase: GamesDatabase) : GamesDao = gamesDatabase.gamesDao()


}