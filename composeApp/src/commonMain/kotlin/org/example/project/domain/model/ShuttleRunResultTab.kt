package org.example.project.domain.model

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.heart_rate_avg_max
import maxipuls.composeapp.generated.resources.mpk
import maxipuls.composeapp.generated.resources.overall_result
import maxipuls.composeapp.generated.resources.sheet
import maxipuls.composeapp.generated.resources.trimp
import org.jetbrains.compose.resources.StringResource

enum class ShuttleRunResultTab(val text: StringResource) {
    OverallResult(text = Res.string.overall_result),
    MPK(text = Res.string.mpk),
}