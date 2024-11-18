package org.example.project.data.api

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.HTTP
import de.jensklingenberg.ktorfit.http.Multipart
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.client.request.forms.MultiPartFormDataContent
import org.example.project.data.model.BaseResponse
import org.example.project.data.model.group.ChangeGroupNameRequest
import org.example.project.data.model.group.GroupResponse
import org.example.project.data.model.login.LoginRequest
import org.example.project.data.model.login.LoginResponse
import org.example.project.data.model.sportsman.SportsmanResponse


interface MaxiPulseApi {
    //login
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    //gamer
    @GET("api/gamer")
    suspend fun getSportsman(): List<SportsmanResponse>

    @GET("api/gamer/{id}")
    suspend fun getSportsmanById(@Path("id") id: String): SportsmanResponse

    @Multipart
    @POST("api/gamer")
    suspend fun createSportsman(@Body request: MultiPartFormDataContent)

    @PUT("api/gamer/{id}")
    suspend fun changeSportsmanFirstname(@Path("id") id: String, @Query("firstname") firstname: String)

    @DELETE("api/gamer/{id}")
    suspend fun deleteSportsman(@Path("id") id: String)


    //group
    @GET("api/group")
    suspend fun getGroup(): List<GroupResponse>

    @GET("api/group/{id}")
    suspend fun getGroupById(@Path("id") id: String): GroupResponse

    @Multipart
    @POST("api/group")
    suspend fun createGroup(@Body request: MultiPartFormDataContent)

    @PUT("api/group/{id}")
    suspend fun changeGroupName(@Path("id") id: String, @Body request: ChangeGroupNameRequest)

    @DELETE("api/group/{id}")
    suspend fun deleteGroup(@Path("id") id: String)
}