package org.example.project.domain.model.widget

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.borg_scale
import maxipuls.composeapp.generated.resources.morning_recovery
import maxipuls.composeapp.generated.resources.remote_training
import maxipuls.composeapp.generated.resources.scale_rpe
import maxipuls.composeapp.generated.resources.self_training
import org.jetbrains.compose.resources.StringResource

enum class MinipulseWidgetTab(val title: StringResource) {
    MorningRecovery(title = Res.string.morning_recovery),
    BorgScale(title = Res.string.scale_rpe),
    RemoteTraining(title = Res.string.self_training)
}