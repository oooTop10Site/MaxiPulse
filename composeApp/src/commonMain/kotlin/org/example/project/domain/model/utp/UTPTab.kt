package org.example.project.domain.model.utp

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.analize_utp
import maxipuls.composeapp.generated.resources.planned_utp
import maxipuls.composeapp.generated.resources.planned_utp_short
import org.jetbrains.compose.resources.StringResource

enum class UTPTab(val title: StringResource) {
    PannedUtp(title = Res.string.planned_utp_short),
    AnalizeUtp(title = Res.string.analize_utp)
}