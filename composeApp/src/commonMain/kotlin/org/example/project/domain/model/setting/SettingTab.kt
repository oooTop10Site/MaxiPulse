package org.example.project.domain.model.setting

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.ok
import maxipuls.composeapp.generated.resources.settings_algorithm_anaerobic_threshold
import maxipuls.composeapp.generated.resources.settings_method_count_trimp
import maxipuls.composeapp.generated.resources.settings_minimum_heart_rate
import maxipuls.composeapp.generated.resources.settings_select_Formula_max_heart_rate
import maxipuls.composeapp.generated.resources.settings_sportsman
import org.jetbrains.compose.resources.StringResource

enum class SettingTab(val text: StringResource) {
    SettingsSportsman(text = Res.string.settings_sportsman),
    MinimumHeartRate(text = Res.string.settings_minimum_heart_rate),
    SelectFormulaMaxHeartRate(text = Res.string.settings_select_Formula_max_heart_rate),
    MethodCountTrimp(text = Res.string.settings_method_count_trimp),
    AlgorithmAnaerobicThreshold(text = Res.string.settings_algorithm_anaerobic_threshold),

}