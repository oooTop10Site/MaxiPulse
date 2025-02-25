package org.example.project.screens.tablet.settings

import org.example.project.domain.model.setting.SettingFormulaMaxHeart
import org.example.project.domain.model.setting.SettingTab
import org.example.project.domain.model.setting.SettingTab.MinimumHeartRate
import org.example.project.domain.model.setting.SettingTab.SelectFormulaMaxHeartRate
import org.example.project.domain.model.setting.SettingTab.SettingsSportsman
import org.example.project.platform.BaseScreenModel
import org.example.project.utils.orEmpty
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class SettingsViewModel :
    BaseScreenModel<SettingsState, SettingsEvent>(SettingsState.InitState) {

    fun changeSelectTab(tab: SettingTab?) = intent {
        reduce {
            state.copy(
                selectTab = if (tab?.let { item ->  state.selectTab?.let { it::class == tab::class } == true} == true ) null else tab
            )
        }
    }

    fun selectLastName() = intent {
        reduce {
            state.copy(
                tabs = state.tabs.map {
                    if (it is SettingsSportsman) {
                        it.copy(generalInfo = it.generalInfo.copy(lastname = !it.generalInfo.lastname))
                    } else it
                }
            )
        }
    }

    fun selectBirthday() = intent {
        reduce {
            state.copy(
                tabs = state.tabs.map {
                    if (it is SettingsSportsman) {
                        it.copy(generalInfo = it.generalInfo.copy(birthday = !it.generalInfo.birthday))
                    } else it
                }
            )
        }
    }

    fun selectSex() = intent {
        reduce {
            state.copy(
                tabs = state.tabs.map {
                    if (it is SettingsSportsman) {
                        it.copy(generalInfo = it.generalInfo.copy(sex = !it.generalInfo.sex))
                    } else it
                }
            )
        }
    }

    fun selectStageReadiness() = intent {
        reduce {
            state.copy(
                tabs = state.tabs.map {
                    if (it is SettingsSportsman) {
                        it.copy(generalInfo = it.generalInfo.copy(stageSportReadiness = !it.generalInfo.stageSportReadiness))
                    } else it
                }
            )
        }
    }


    fun selectSportCategory() = intent {
        reduce {
            state.copy(
                tabs = state.tabs.map {
                    if (it is SettingsSportsman) {
                        it.copy(generalInfo = it.generalInfo.copy(sportCategory = !it.generalInfo.sportCategory))
                    } else it
                }
            )
        }
    }


    fun selectName() = intent {
        reduce {
            state.copy(
                tabs = state.tabs.map {
                    if (it is SettingsSportsman) {
                        it.copy(generalInfo = it.generalInfo.copy(name = !it.generalInfo.name))
                    } else it
                }
            )
        }
    }

    fun selectMiddleName() = intent {
        reduce {
            state.copy(
                tabs = state.tabs.map {
                    if (it is SettingsSportsman) {
                        it.copy(generalInfo = it.generalInfo.copy(middleName = !it.generalInfo.middleName))
                    } else it
                }
            )
        }
    }

    fun selectSportSpecialization() = intent {
        reduce {
            state.copy(
                tabs = state.tabs.map {
                    if (it is SettingsSportsman) {
                        it.copy(generalInfo = it.generalInfo.copy(middleName = !it.generalInfo.middleName))
                    } else it
                }
            )
        }
    }

    fun selectFormulaFormulaChssMax(value: SettingFormulaMaxHeart) = intent {
        reduce {
            state.copy(
                tabs = state.tabs.map {
                    if (it is SelectFormulaMaxHeartRate) {
                        it.copy(maxHeartRateUI = it.maxHeartRateUI.copy(selectItem = value))
                    } else it
                }
            )
        }
    }

    fun selectMinHeartRate(value: String) = intent {
        reduce {
            state.copy(
                tabs = state.tabs.map {
                    if (it is MinimumHeartRate) {
                        it.copy(
                            minimumHeartRateUI = it.minimumHeartRateUI.copy(
                                minimun = value.let {
                                    if (it.firstOrNull()
                                            ?.digitToIntOrNull() == 0
                                    ) it.drop(1) else it
                                }.take(
                                    3
                                ).toIntOrNull().orEmpty()
                            )
                        )
                    } else it
                }
            )
        }
    }


    fun changeHighPerformance() = intent {
        reduce {
            state.copy(useHighPerformance = !state.useHighPerformance)
        }
    }

    fun changeUseRoute() = intent {
        reduce {
            state.copy(useRoute = !state.useRoute)
        }
    }
}