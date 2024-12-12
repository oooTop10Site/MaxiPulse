package org.example.project.domain.model.sportsman

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.chss
import maxipuls.composeapp.generated.resources.chss_max
import maxipuls.composeapp.generated.resources.chss_pano
import maxipuls.composeapp.generated.resources.distance
import maxipuls.composeapp.generated.resources.mpk
import maxipuls.composeapp.generated.resources.performance
import maxipuls.composeapp.generated.resources.recovery
import org.jetbrains.compose.resources.StringResource

enum class SportsmanGraph(val text: StringResource) {
    MPK(Res.string.mpk),
    ChssMax(Res.string.chss_max),
    ChssPano(Res.string.chss_pano),
    Distance(Res.string.distance),
    Performance(Res.string.performance),
    Recovery(Res.string.recovery)
}