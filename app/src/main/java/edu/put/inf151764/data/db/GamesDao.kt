package edu.put.inf151764.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.put.inf151764.data.db.data.ItemEntity
import edu.put.inf151764.data.db.data.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items:List<ItemEntity>)


    //Czymy to musimy dzielić
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user:UserEntity)

//  @Insert(onConflict = OnConflictStrategy.REPLACE)
//  suspend fun insertDetails(detailsResponse: DetailsResponse)


    @Query("Select * from ItemEntity")
    suspend fun getItems() : List<ItemEntity>

    @Query("Select * from UserEntity")
    fun getUser() : Flow<UserEntity?>

    @Query("Select count(*) from UserEntity")
    suspend fun getUserTableCount(): Int

    @Query("Select count(*) from ItemEntity")
    suspend fun getItemsTableCount(): Int

}