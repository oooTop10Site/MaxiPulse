package org.example.project

class JVMPlatform: Platform {
    override val name: String = "Java"
}

actual fun getPlatform(): Platform = JVMPlatform()