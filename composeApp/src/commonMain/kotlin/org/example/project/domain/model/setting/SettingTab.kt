package org.example.project.domain.model.setting

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.ok
import maxipuls.composeapp.generated.resources.settings_algorithm_anaerobic_threshold
import maxipuls.composeapp.generated.resources.settings_method_count_trimp
import maxipuls.composeapp.generated.resources.settings_minimum_heart_rate
import maxipuls.composeapp.generated.resources.settings_select_Formula_max_heart_rate
import maxipuls.composeapp.generated.resources.settings_sportsman
import org.example.project.domain.model.sportsman.HeartBit
import org.jetbrains.compose.resources.StringResource

sealed class SettingTab(val text: StringResource) {

    companion object {
        val list = listOf(
            SettingsSportsman(generalInfo = SettingsGeneralInfo.InitState),
            MinimumHeartRate(minimumHeartRateUI = SettingMinimumHeartRateUI.InitState),
            SelectFormulaMaxHeartRate(maxHeartRateUI = SettingsMaxHeartRateUI.InitState),
            MethodCountTrimp,
            AlgorithmAnaerobicThreshold,
        )
    }

    data class SettingsSportsman(val generalInfo: SettingsGeneralInfo) :
        SettingTab(text = Res.string.settings_sportsman)

    data class MinimumHeartRate(val minimumHeartRateUI: SettingMinimumHeartRateUI) :
        SettingTab(text = Res.string.settings_minimum_heart_rate)

    data class SelectFormulaMaxHeartRate(val maxHeartRateUI: SettingsMaxHeartRateUI) :
        SettingTab(text = Res.string.settings_select_Formula_max_heart_rate)

    data object MethodCountTrimp : SettingTab(text = Res.string.settings_method_count_trimp)
    data object AlgorithmAnaerobicThreshold :
        SettingTab(text = Res.string.settings_algorithm_anaerobic_threshold)
}

data class SettingMinimumHeartRateUI(
    val minimun: Int,
    val heartRate: List<HeartBit>
) {
    companion object {
        val InitState = SettingMinimumHeartRateUI(
            0, emptyList()
        )
    }
}

data class SettingsMaxHeartRateUI(
    val items: List<SettingFormulaMaxHeart>,
    val selectItem: SettingFormulaMaxHeart
) {
    companion object {
        val InitState = SettingsMaxHeartRateUI(
            listOf(
                SettingFormulaMaxHeart(
                    title = "Формула Хаскеля-Фокса",
                    desc = "Максимальный пульс = 220 - возраст\nПример: ЧСС для 20 лет равен 200"
                ),
                SettingFormulaMaxHeart(
                    title = "Формула Танака",
                    desc = "Максимальный пульс = 208 - (0.7 * возраст)\nПример: ЧСС для 30 лет равен 187"
                ),
                SettingFormulaMaxHeart(
                    title = "Формула Гельмана",
                    desc = "Максимальный пульс = 205 - (0.5 * возраст)\nПример: ЧСС для 40 лет равен 185"
                ),
                SettingFormulaMaxHeart(
                    title = "Формула Мартинсона",
                    desc = "Максимальный пульс = 210 - (0.65 * возраст)\nПример: ЧСС для 25 лет равен 194"
                ),
                SettingFormulaMaxHeart(
                    title = "Формула Миллера",
                    desc = "Максимальный пульс = 217 - (0.85 * возраст)\nПример: ЧСС для 35 лет равен 187"
                )
            ), SettingFormulaMaxHeart("", "")
        )
    }
}

data class SettingFormulaMaxHeart(
    val title: String,
    val desc: String,
)

data class SettingsGeneralInfo(
    val lastname: Boolean,
    val name: Boolean,
    val middleName: Boolean,
    val birthday: Boolean,
    val sex: Boolean,
    val stageSportReadiness: Boolean,
    val sportCategory: Boolean,
    val sportSpecialization: Boolean,
) {
    companion object {
        val InitState = SettingsGeneralInfo(
            lastname = false,
            name = false,
            middleName = false,
            birthday = false,
            sex = false,
            stageSportReadiness = false,
            sportCategory = false,
            sportSpecialization = false
        )
    }
}