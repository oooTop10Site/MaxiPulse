package org.example.project.platform

import java.util.UUID

actual fun randomUUID() = UUID.randomUUID().toString()