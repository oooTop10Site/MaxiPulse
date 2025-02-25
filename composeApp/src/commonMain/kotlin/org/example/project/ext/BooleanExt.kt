package org.example.project.ext

import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.female
import maxipuls.composeapp.generated.resources.male
import org.jetbrains.compose.resources.StringResource

fun Boolean.isMaleToString(): StringResource {
    return if (this) Res.string.male else Res.string.female
}

fun Boolean.toInt(): Int {
    return if (this) 1 else 0
}