package org.example.project.data.model.sportsman

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GamerResponse(

    @SerialName("id") val id: String?,
    @SerialName("image") val image: String?,
    @SerialName("firstname") val firstname: String?,
    @SerialName("lastname") val lastname: String?,
    @SerialName("middlename") val middlename: String?,
    @SerialName("age") val age: Int?,
    @SerialName("number") val number: Int?,
    @SerialName("weight") val weight: String?,
    @SerialName("height") val height: String?,
    @SerialName("is_male") val isMale: Boolean?,
    @SerialName("chss_default") val chssDefault: Int?,
    @SerialName("chss_max") val chssMax: Int?,
    @SerialName("chss_pano") val chssPano: Int?,
    @SerialName("chss_pao") val chssPao: Int?,
    @SerialName("imt") val imt: String?,
    @SerialName("mpk") val mpk: String?,
    @SerialName("chest_excursion") val chestExcursion: Int?,
    @SerialName("experimental_respiration_rate") val experimentalRespirationRate: Int?,
    @SerialName("blood_pressure") val bloodPressure: Int?,
    @SerialName("spinometry") val spinometry: Int?,
    @SerialName("spo2") val spo2: Int?,
    @SerialName("rufie_test") val rufieTest: Int?,
    @SerialName("genchi_sample") val genchiSample: Int?,
    @SerialName("maximum_chss_zone") val maximumChssZone: Int?,
    @SerialName("birthday") val birthday: String?,
    @SerialName("percent_muscle_mass") val percentMuscleMass: Int?,
    @SerialName("game_type_id") val gameTypeId: String?,
    @SerialName("training_stage_id") val trainingStageId: String?,
    @SerialName("rank_id") val rankId: String?,

    )