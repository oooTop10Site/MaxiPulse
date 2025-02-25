package org.example.project.ext

import androidx.compose.runtime.Composable
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.lying_position
import maxipuls.composeapp.generated.resources.standing_position
import org.example.project.domain.model.test.ReadiesForUploadTests
import org.jetbrains.compose.resources.stringResource


@Composable
fun ReadiesForUploadTests.toText(): String {
    return when (this) {
        ReadiesForUploadTests.LyingPosition -> {
            stringResource(Res.string.lying_position)
        }

        ReadiesForUploadTests.StandingPosition -> {
            stringResource(Res.string.standing_position)
        }
    }
}