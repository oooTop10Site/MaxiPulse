package org.example.project.data.api

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Multipart
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.client.request.forms.MultiPartFormDataContent
import org.example.project.data.model.BaseResponse
import org.example.project.data.model.gameType.GameTypeResponse
import org.example.project.data.model.group.ChangeGroupNameRequest
import org.example.project.data.model.group.GroupResponse
import org.example.project.data.model.login.LoginRequest
import org.example.project.data.model.login.LoginResponse
import org.example.project.data.model.rank.RankResponse
import org.example.project.data.model.sensor.SensorPreviewResponse
import org.example.project.data.model.sportsman.GamerResponse
import org.example.project.data.model.trainingStage.TrainingStageResponse


interface MaxiPulseApi {
    //login
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    //gamer
    @GET("api/gamer")
    suspend fun getSportsmans(): BaseResponse<List<GamerResponse>>

    @GET("api/gamer")
    suspend fun getSportsmansByGroupId(@Query("group_id") groupId: String): BaseResponse<List<GamerResponse>> //todo на бэке тоже надо

    @GET("api/gamer/{id}")
    suspend fun getSportsmanById(@Path("id") id: String): BaseResponse<GamerResponse>

    @Multipart
    @POST("api/gamer")
    suspend fun createSportsman(@Body request: MultiPartFormDataContent)

    @Multipart
    @POST("api/gamer/{id}")
    suspend fun changeSportsman(@Path("id") id: String, @Body request: MultiPartFormDataContent)

    @DELETE("api/gamer/{id}")
    suspend fun deleteSportsman(@Path("id") id: String)

    @GET("api/game-type")
    suspend fun getGameTypes(): BaseResponse<List<GameTypeResponse>>

    @GET("api/training-stage")
    suspend fun getTrainingStages(@Query("game_type_id") gameTypeId: String): BaseResponse<List<TrainingStageResponse>>

    @GET("api/rank")
    suspend fun getRanks(@Query("game_type_id") gameTypeId: String): BaseResponse<List<RankResponse>>

    //group
    @GET("api/group")
    suspend fun getGroups(): BaseResponse<List<GroupResponse>>

    @GET("api/group/{id}")
    suspend fun getGroupById(@Path("id") id: String): BaseResponse<GroupResponse>

    @Multipart
    @POST("api/group")
    suspend fun createGroup(@Body request: MultiPartFormDataContent)

    @Multipart
    @POST("api/group/{id}")
    suspend fun editGroup(@Path("id") id: String, @Body request: MultiPartFormDataContent)

    @Multipart
    @POST("api/group/delete/{id}")
    suspend fun deleteGroup(@Path("id") id: String, @Body request: MultiPartFormDataContent)

    //sensor

    @GET("api/sensor")
    suspend fun getSensor(): BaseResponse<List<SensorPreviewResponse>>

    @POST("api/sensor")
    suspend fun addSensor(@Body request: SensorPreviewResponse): Unit

}