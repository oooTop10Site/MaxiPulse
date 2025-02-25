package org.example.project.domain.model.training

import cafe.adriel.voyager.core.lifecycle.JavaSerializable

data class TrainingStageChssUI(
    val time: Long, //min
    val title: String, //например разминка
    val chss: Int
) : JavaSerializable {
    companion object {
        fun parseTrainingStages(input: String): List<TrainingStageChssUI> {
            val stages = mutableListOf<TrainingStageChssUI>()
            val words = try {
                input.lowercase().split(" ").filter { it != "пик" }.let {
                    it.subList(
                        fromIndex = it.indexOf(it.first { it.contains("перв") }),
                        toIndex = it.size
                    )
                } // Разделяем строку по пробелам
            } catch (e: Exception) {
                emptyList<String>()
            }
            println(words)
            // Слова, которые нужно игнорировать
            val ignoreWords = setOf(
                "минут",
                "мин",
                "минута",
                "минуты",
                "время",
                "продолжительность",
                "чсс",
                "пик",
                "чсспик",
                "чспик",
                "частей",
                "часть",
                "этап",
                "этапов",
                "этапа",
            )

            var index = 0

            // Извлекаем этапы
            while (index < words.size) {
                // Пропускаем всё, пока не найдём название этапа (слово, которое не в ignoreWords и не число)
                while (index < words.size && (words[index].toIntOrNull() != null || words[index] in ignoreWords)) {
                    index++
                }
                if (index >= words.size) break
                val title = words[index]
                index++

                // Пропускаем всё, пока не найдём продолжительность (число, за которым следует "минут")
                while (index < words.size && words[index].toIntOrNull() == null) {
                    index++
                }
                if (index >= words.size) break
                val time = words[index].toLong()
                index++

                // Пропускаем "минут"
                while (index < words.size && words[index] in setOf(
                        "минут",
                        "мин",
                        "минута",
                        "минуты"
                    )
                ) {
                    index++
                }

                // Пропускаем всё, пока не найдём ЧСС (число после "чсс пик")
                while (index < words.size && !words[index].contains("чс")) {
                    index++
                }
                if (index >= words.size) break
                index++ // Пропускаем "чсс"
                if (index >= words.size) break
                val chss = words[index].toIntOrNull() ?: break
                index++

                stages.add(TrainingStageChssUI(time, title, chss))
            }

            println("stages бамс - $stages")
            return stages
        }

    }

}