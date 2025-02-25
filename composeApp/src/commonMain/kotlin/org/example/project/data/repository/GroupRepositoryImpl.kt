package org.example.project.data.repository

import org.example.project.data.api.MaxiPulseApi
import org.example.project.data.mapper.toUI
import org.example.project.di.apiModule
import org.example.project.domain.model.composition.GroupUI
import org.example.project.domain.repository.GroupRepository
import org.example.project.ext.toInt
import org.example.project.ext.toServer
import org.example.project.platform.Either
import org.example.project.platform.Failure
import org.example.project.platform.Form
import org.example.project.platform.MultipartManager
import org.example.project.platform.apiCall
import kotlin.collections.addAll

class GroupRepositoryImpl(
    private val maxiPulseApi: MaxiPulseApi,
    private val multipartManager: MultipartManager
) : GroupRepository {
    override suspend fun getGroups(): Either<Failure, List<GroupUI>> {
        return apiCall(
            call = {
                maxiPulseApi.getGroups()
            },
            mapResponse = {
                it.data?.map { it.toUI() }.orEmpty()
            }
        )
    }

    override suspend fun getGroupById(groupId: String): Either<Failure, GroupUI> {
        return apiCall(
            call = {
                maxiPulseApi.getGroupById(id = groupId)
            },
            mapResponse = {
                it.data?.toUI() ?: GroupUI.Default
            }
        )
    }

    override suspend fun createGroup(
        name: String,
        image: String,
        sportmans: List<String>
    ): Either<Failure, Unit> {
        println(
            "sportmans - $sportmans"
        )
        val list = buildList<Form> {
            add(Form.FormBody("name", name))
            if (!image.startsWith("http") && image.isNotBlank()) {
                add(Form.FormFile("image", image))
            }
            sportmans.map {
                add(Form.FormBody("gamers[][gamer_id]", it))
            }
        }

        val body = multipartManager.createMultipart(
            list
        )
        return apiCall(
            call = {
                maxiPulseApi.createGroup(
                    body
                )
            }
        )
    }

    override suspend fun editGroup(
        groupId: String,
        name: String,
        image: String,
        sportmans: List<String>
    ): Either<Failure, Unit> {
        println(
            "sportmans - $sportmans"
        )
        val sportmansForms = sportmans.map {
            Form.FormBody("gamers[][gamer_id]", it)
        }.toTypedArray()
        val list = buildList<Form> {
            add(Form.FormBody("_method", "PUT"))
            add(Form.FormBody("name", name))
            addAll(sportmansForms)
//            if (!image.startsWith("http") && image.isNotBlank()) {
//                add(Form.FormFile("image", image))
//            }
        }


        val body = multipartManager.createMultipart(
            list
        )
        return apiCall(

            call = {
                maxiPulseApi.editGroup(
                    id = groupId, request = body
                )
            }
        )
    }

    override suspend fun groupDelete(groupId: String): Either<Failure, Unit> {
        val list = buildList<Form> {
            add(Form.FormBody("type", "group"))
        }


        val body = multipartManager.createMultipart(
            list
        )
        return apiCall {
            maxiPulseApi.deleteGroup(id = groupId, body)
        }
    }
}