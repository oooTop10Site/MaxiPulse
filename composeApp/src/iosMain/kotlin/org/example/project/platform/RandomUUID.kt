package org.example.project.platform

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()