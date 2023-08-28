package edu.put.inf151764.data.api

import edu.put.inf151764.data.api.data.details.DetailsResponse
import edu.put.inf151764.data.api.data.list.ItemsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApi {
    @GET("collection")
    suspend fun getGames(
        @Query("username") username: String
    ): Response<ItemsResponse>

    @GET("thing")
    suspend fun getGameDetails(
        @Query("id") id: Int,
        @Query("stats") boolean: Boolean = true
    ): Response<DetailsResponse>
}