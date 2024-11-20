package org.example.project.platform

import org.koin.core.component.KoinComponent

expect fun KoinComponent.checkFileSize(uri: String, maxSizeMb: Int = 10): Boolean