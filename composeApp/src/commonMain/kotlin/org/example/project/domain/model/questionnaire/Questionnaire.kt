package org.example.project.domain.model.questionnaire

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.scale_tampa
import org.jetbrains.compose.resources.StringResource

enum class Questionnaire(val title: StringResource) {

    ScaleTampa(title = Res.string.scale_tampa),
    Temp(title = Res.string.scale_tampa)

}