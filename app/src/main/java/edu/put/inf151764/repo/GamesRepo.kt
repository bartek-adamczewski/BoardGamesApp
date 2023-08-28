package edu.put.inf151764.repo

import edu.put.inf151764.data.api.GamesApi
import edu.put.inf151764.data.api.data.details.DetailsResponse
import edu.put.inf151764.data.api.data.list.ItemsResponse
import edu.put.inf151764.data.api.util.GamesApiResult
import edu.put.inf151764.data.api.util.wrapApiCall
import edu.put.inf151764.data.db.GamesDao
import edu.put.inf151764.data.db.GamesDatabase
import edu.put.inf151764.data.db.data.ItemEntity
import edu.put.inf151764.data.db.data.UserEntity
import edu.put.inf151764.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GamesRepo @Inject constructor(
    private val gamesDao: GamesDao,
    private val gamesApi: GamesApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val gamesDb: GamesDatabase
) {

    suspend fun getUser(): Flow<UserEntity?> = withContext(ioDispatcher) {
        gamesDao.getUser()
    }

    suspend fun isUserLogged(): Boolean = withContext(ioDispatcher) {
        gamesDao.getUserTableCount() > 0
    }

    suspend fun addUser(name: String) = withContext(ioDispatcher) {
        gamesDao.insertUser(user = UserEntity(name))
    }

    suspend fun clearDb() = withContext(ioDispatcher) {
        gamesDb.clearAllTables()
    }

    suspend fun getItemsFromApi(): List<ItemEntity> = withContext(ioDispatcher) {
        return@withContext if (gamesDao.getItemsTableCount() > 0) {
            gamesDao.getItems()
        } else {
            val apiResult = wrapApiCall {
                gamesApi.getGames(gamesDao.getUser().first()!!.name)
            }
            when (apiResult) {
                is GamesApiResult.Exception -> {
                    emptyList()
                }
                is GamesApiResult.Success -> {
                    val itemEntities = apiResult.data.getAllItems()
                    gamesDao.insertItems(itemEntities)
                    itemEntities
                }
            }
        }
    }

    suspend fun getDetails(id: String): GamesApiResult<DetailsResponse> = withContext(ioDispatcher) {
        val resp = wrapApiCall {
            gamesApi.getGameDetails(id = id.toInt())
        }
        return@withContext resp
    }


    private fun ItemsResponse.getAllItems(): List<ItemEntity> =
        items.map {
            ItemEntity(
                objectType = it.objectType,
                objectId = it.objectId,
                subType = it.subType,
                collId = it.collId,
                name = it.name,
                yearPublished = it.yearPublished,
                image = it.image,
                thumbnail = it.thumbnail,
                numPlays = it.numPlays
            )
        }
}