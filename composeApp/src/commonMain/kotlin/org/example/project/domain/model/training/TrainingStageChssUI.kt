package org.example.project.domain.model.training

import cafe.adriel.voyager.core.lifecycle.JavaSerializable

data class TrainingStageChssUI(
    val time: Long, //seconds
    val title: String, //например разминка
    val chss: Int
): JavaSerializable {
    companion object {
        fun parseTrainingStages(input: String): List<TrainingStageChssUI> {
            val stages = mutableListOf<TrainingStageChssUI>()
            val words = input.lowercase().split(" ").let {
                try {
                    it.subList(fromIndex = it.indexOf("первый"), toIndex = it.lastIndex)
                } catch (e: Exception) {
                    return@let emptyList()
                }
            } // Разделяем строку по пробелам

            // Слова, которые нужно игнорировать
            val ignoreWords = setOf(
                "минут",
                "мин",
                "минута",
                "минуты",
                "время",
                "количество",
                "колличество",
                "чсс",
                "чс",
                "css",
                "этап",
                "этапов",
                "этапа",
                "первый",
                "второй",
                "третий",
                "четвертый",
                "пятый",
                "шестой"
            )
            var index = 0

            // Извлекаем этапы
            while (index < words.size) {
                // Пропускаем всё, пока не найдём название этапа (слово, которое не в ignoreWords и не число)
                while (index < words.size && (words[index].toIntOrNull() != null || words[index] in ignoreWords)
                ) {
                    index++
                }
                if (index >= words.size) break
                val title = words[index]
                index++

                // Пропускаем всё, пока не найдём ЧСС (первое число после названия)
                while (index < words.size && words[index].toIntOrNull() == null) {
                    index++
                }
                if (index >= words.size) break
                val chss = words[index].toInt()
                index++

                // Пропускаем всё, пока не найдём время (первое число после ЧСС)
                while (index < words.size && words[index].toIntOrNull() == null) {
                    index++
                }
                if (index >= words.size) break
                val time = words[index].toLong()
                index++

                stages.add(TrainingStageChssUI(time, title, chss))
            }
            println("stages бамс - $stages ")
            return stages
        }

    }
}