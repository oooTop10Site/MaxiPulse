package org.example.project.data.repository

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.max_file_size
import org.example.project.data.api.MaxiPulseApi
import org.example.project.data.mapper.toUI
import org.example.project.domain.model.gameType.GameTypeUI
import org.example.project.domain.model.sportsman.SportsmanUI
import org.example.project.domain.repository.GamerRepository
import org.example.project.ext.toServer
import org.example.project.platform.Either
import org.example.project.platform.Failure
import org.example.project.platform.Form
import org.example.project.platform.MultipartManager
import org.example.project.platform.apiCall
import org.example.project.platform.checkFileSize
import org.example.project.utils.Constants
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import kotlin.collections.orEmpty

class GamerRepositoryImpl(
    private val maxiPulseApi: MaxiPulseApi,
    private val multipartManager: MultipartManager
) : GamerRepository, KoinComponent {
    override suspend fun getGamers(): Either<Failure, List<SportsmanUI>> {
        return apiCall(
            call = {
                maxiPulseApi.getSportsmans()
            },
            mapResponse = {
                it.data?.map { it.toUI() }.orEmpty()
            }
        )
    }

    override suspend fun getGamersByGroupId(groupId: String): Either<Failure, List<SportsmanUI>> {
        return apiCall(
            call = {
                maxiPulseApi.getSportsmansByGroupId(/***groupId*/)
            },
            mapResponse = {
                it.data?.map { it.toUI() }.orEmpty()
            }
        )
    }

    override suspend fun getGamer(gamerId: String): Either<Failure, SportsmanUI> {
        return apiCall(
            call = {
                maxiPulseApi.getSportsmanById(id = gamerId)
            },
            mapResponse = {
                it.data?.toUI() ?: SportsmanUI.Default
            }
        )
    }

    override suspend fun editGamer(
        gamerId: String?,
        sportsmanUI: SportsmanUI
    ): Either<Failure, Unit> {
        if (!sportsmanUI.avatar.startsWith("http") && sportsmanUI.avatar.isNotEmpty()) {
            sportsmanUI.avatar.let {
                val formImage = Form.FormFile("image", it)
                if (!checkFileSize(formImage.uri)) {
                    return Either.Left(
                        Failure.LimitedFileSize(
                            getString(
                                Res.string.max_file_size,
                                Constants.FILE_SIZE_MB.toString()
                            )
                        )
                    )
                }
            }
        }
        val list = buildList<Form> {
            if (!sportsmanUI.avatar.startsWith("http") && sportsmanUI.avatar.isNotBlank()) {
                add(Form.FormFile("image", sportsmanUI.avatar))
            }
            if (gamerId != null) {
                add(Form.FormBody("_method", "PUT"))
            }
            add(Form.FormBody("firstname", sportsmanUI.name))
            add(Form.FormBody("lastname", sportsmanUI.lastname))
            add(Form.FormBody("middlename", sportsmanUI.middleName))
            add(Form.FormBodyInt("number", sportsmanUI.number))
            add(Form.FormBody("weight", sportsmanUI.weight.toString()))
            add(Form.FormBody("height", sportsmanUI.height.toString()))
            add(Form.FormBodyInt("chss_default", sportsmanUI.chssResting))
            add(Form.FormBodyInt("chss_max", sportsmanUI.chssMax))
            add(Form.FormBodyInt("chss_pano", sportsmanUI.chssPano))
            add(Form.FormBodyInt("chss_pao", sportsmanUI.chssPao))
            add(Form.FormBody("imt", sportsmanUI.imt.toString()))
            add(Form.FormBody("mpk", sportsmanUI.mpk.toString()))
            add(Form.FormBody("game_type_id", sportsmanUI.gameTypeUI.id))
            add(Form.FormBodyBool("is_male", sportsmanUI.isMale))
            if (sportsmanUI.birthDay != null) {
                add(Form.FormBody("birthday", sportsmanUI.birthDay.toServer()))
            }
        }
        list.forEach {
            println("$it")
        }
        println(sportsmanUI)
        println(gamerId)
        val body = multipartManager.createMultipart(
            list
        )
        return apiCall(
            call = {
                if (gamerId != null) {
                    maxiPulseApi.changeSportsman(id = gamerId, body)
                } else {
                    maxiPulseApi.createSportsman(body)
                }
            }
        )
    }

    override suspend fun getGameTypes(): Either<Failure, List<GameTypeUI>> {
        return apiCall(
            call = {
                maxiPulseApi.getGameTypes()
            },
            mapResponse = {
                it.data?.map { it.toUI() }.orEmpty()
            }
        )
    }

}