package org.example.project.domain.model

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.growth
import maxipuls.composeapp.generated.resources.load
import maxipuls.composeapp.generated.resources.monotony
import maxipuls.composeapp.generated.resources.tension
import org.jetbrains.compose.resources.StringResource


enum class AnalizeGraph(val title: StringResource) {
    MONOTONY(Res.string.monotony),
    GROWTH(Res.string.growth),
    LOAD(Res.string.load),
    TENSION(Res.string.tension)
}