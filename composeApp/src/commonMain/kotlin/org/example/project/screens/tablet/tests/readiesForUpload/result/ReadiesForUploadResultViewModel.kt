package org.example.project.screens.tablet.tests.readiesForUpload.result

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.domain.model.test.SportsmanTestResultUI
import org.example.project.platform.BaseScreenModel
import org.example.project.utils.Constants
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

@OptIn(OrbitExperimental::class)
internal class ReadiesForUploadResultViewModel :
    BaseScreenModel<ReadiesForUploadResultState, ReadiesForUploadResultEvent>(
        ReadiesForUploadResultState.InitState
    ) {

    fun loadResult(sportmans: List<SportsmanTestResultUI>) = intent {
        reduce {
            state.copy(
                users = sportmans, filterSportsmans = sportmans,
                search = ""
            )
        }
    }

    fun changeSearch(value: String) = blockingIntent() {
        reduce {
            state.copy(
                search = value
            )
        }
    }

    var job: Job? = null
    fun search(value: String) = intent {
        job?.cancel()
        job = screenModelScope.launch {
            delay(Constants.Debounce)
            val queryWords = value.trim().lowercase().split(" ") // Разбиваем запрос на слова
            reduce {
                state.copy(
                    filterSportsmans = state.users.filter { sportman ->
                        val fio =
                            sportman.fio.lowercase() // Приводим ФИО спортсмена к нижнему регистру
                        queryWords.all { word -> fio.contains(word) } // Проверяем, что все слова из запроса есть в ФИО
                    }
                )
            }
        }
    }


    fun changeSelect(value: String) = intent {
        reduce {
            state.copy(
                filter = value
            )
        }
    }

}