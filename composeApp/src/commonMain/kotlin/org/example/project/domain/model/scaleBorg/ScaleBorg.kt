package org.example.project.domain.model.scaleBorg

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.scale_0
import maxipuls.composeapp.generated.resources.scale_1
import maxipuls.composeapp.generated.resources.scale_10
import maxipuls.composeapp.generated.resources.scale_1_text
import maxipuls.composeapp.generated.resources.scale_2
import maxipuls.composeapp.generated.resources.scale_2_text
import maxipuls.composeapp.generated.resources.scale_3
import maxipuls.composeapp.generated.resources.scale_3_text
import maxipuls.composeapp.generated.resources.scale_4
import maxipuls.composeapp.generated.resources.scale_4_text
import maxipuls.composeapp.generated.resources.scale_5_text
import maxipuls.composeapp.generated.resources.scale_5_to_6
import maxipuls.composeapp.generated.resources.scale_6_text
import maxipuls.composeapp.generated.resources.scale_7_text
import maxipuls.composeapp.generated.resources.scale_7_to_8
import maxipuls.composeapp.generated.resources.scale_8_text
import maxipuls.composeapp.generated.resources.scale_9
import maxipuls.composeapp.generated.resources.scale_9_text
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class ScaleBorg(val title: StringResource, val icon: DrawableResource, val rating: List<Int>) {

    object Scale1: ScaleBorg(title = Res.string.scale_1_text, icon = Res.drawable.scale_0, rating = listOf(0))
    object Scale2: ScaleBorg(title = Res.string.scale_2_text, icon = Res.drawable.scale_1, rating = listOf(1))
    object Scale3: ScaleBorg(title = Res.string.scale_3_text, icon = Res.drawable.scale_2, rating = listOf(2))
    object Scale4: ScaleBorg(title = Res.string.scale_4_text, icon = Res.drawable.scale_3, rating = listOf(3))
    object Scale5: ScaleBorg(title = Res.string.scale_5_text, icon = Res.drawable.scale_4, rating = listOf(4))
    object Scale6: ScaleBorg(title = Res.string.scale_6_text, icon = Res.drawable.scale_5_to_6, rating = listOf(5, 6))
    object Scale7: ScaleBorg(title = Res.string.scale_7_text, icon = Res.drawable.scale_7_to_8, rating = listOf(7, 8))
    object Scale8: ScaleBorg(title = Res.string.scale_8_text, icon = Res.drawable.scale_9, rating = listOf(9))
    object Scale9: ScaleBorg(title = Res.string.scale_9_text, icon = Res.drawable.scale_10, rating = listOf(10))

}