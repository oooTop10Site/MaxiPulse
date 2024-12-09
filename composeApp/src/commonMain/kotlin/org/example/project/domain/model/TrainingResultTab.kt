package org.example.project.domain.model

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.heart_rate_avg_max
import maxipuls.composeapp.generated.resources.sheet
import maxipuls.composeapp.generated.resources.trimp
import org.jetbrains.compose.resources.StringResource

enum class TrainingResultTab(val text: StringResource) {
    Sheet(text = Res.string.sheet),
    Trimp(text = Res.string.trimp),
    HeartRate(text = Res.string.heart_rate_avg_max)
}