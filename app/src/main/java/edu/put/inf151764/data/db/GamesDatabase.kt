package edu.put.inf151764.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.put.inf151764.data.db.data.ItemEntity
import edu.put.inf151764.data.db.data.UserEntity

@Database(entities = [ItemEntity::class, UserEntity::class], version = 1, exportSchema = false)
abstract class GamesDatabase : RoomDatabase() {
    abstract fun gamesDao() : GamesDao
}