package org.example.project.domain.model.option

import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.extended_testing
import maxipuls.composeapp.generated.resources.questionnaire
import maxipuls.composeapp.generated.resources.utp
import org.jetbrains.compose.resources.StringResource

enum class Option(val title: StringResource): JavaSerializable {

    UTP(title = Res.string.utp),
    Questionnaire(title = Res.string.questionnaire),
    ExtendedTesting(title = Res.string.extended_testing)

}