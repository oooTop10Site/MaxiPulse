package org.example.project.domain.model.test

import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.line_up
import maxipuls.composeapp.generated.resources.readies_for_upload
import maxipuls.composeapp.generated.resources.shuttle_run
import maxipuls.composeapp.generated.resources.shuttle_running
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class TestUI(val isPay: Boolean) :
    JavaSerializable {
    object ShuttleRun :
        TestUI(isPay = false),
        JavaSerializable

    object ReadiesForUpload :
        TestUI(isPay = true),
        JavaSerializable

    val title: StringResource
        get() {
            return when (this) {
                ReadiesForUpload -> Res.string.readies_for_upload
                ShuttleRun -> Res.string.shuttle_run
            }
        }

    val icon: DrawableResource
        get() {
            return when (this) {
                ReadiesForUpload ->Res.drawable.line_up
                ShuttleRun -> Res.drawable.shuttle_running
            }
        }

    companion object {
        val entries = listOf<TestUI>(TestUI.ShuttleRun, TestUI.ReadiesForUpload)
    }
}