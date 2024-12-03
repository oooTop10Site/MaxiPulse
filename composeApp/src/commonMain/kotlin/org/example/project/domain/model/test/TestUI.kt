package org.example.project.domain.model.test

import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.line_up
import maxipuls.composeapp.generated.resources.readies_for_upload
import maxipuls.composeapp.generated.resources.shuttle_run
import maxipuls.composeapp.generated.resources.shuttle_running
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class TestUI(val title: StringResource, val icon: DrawableResource, val isPay: Boolean): JavaSerializable {
    object ShuttleRun :
        TestUI(title = Res.string.shuttle_run, icon = Res.drawable.shuttle_running, isPay = false)

    object ReadiesForUpload :
        TestUI(title = Res.string.readies_for_upload, icon = Res.drawable.line_up, isPay = true)

    companion object {
        val entries = listOf<TestUI>(TestUI.ShuttleRun, TestUI.ReadiesForUpload)
    }
}