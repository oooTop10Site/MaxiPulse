package org.example.project.domain.model.task

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.borg_scale
import maxipuls.composeapp.generated.resources.running_bro
import maxipuls.composeapp.generated.resources.sun
import org.jetbrains.compose.resources.DrawableResource

sealed class MainTaskUI(
    val image: DrawableResource,
    val title: String,
    val description: String,
) {

    object GoodMorning: MainTaskUI(image = Res.drawable.sun, "Бодрое утро!", "Утреннее восстановление")
    object BorgScale: MainTaskUI(image = Res.drawable.borg_scale, "Отмечай своё состояние", "Шкала Борга")
    object Training: MainTaskUI(image = Res.drawable.running_bro, "Тренировка", "Время позаниматься")

}