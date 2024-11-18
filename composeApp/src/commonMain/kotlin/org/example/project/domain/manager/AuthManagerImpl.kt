package org.example.project.domain.manager

import com.russhwolf.settings.Settings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class AuthManagerImpl : AuthManager, KoinComponent {

    private val settings by inject<Settings>()

    override var token: String?
        get() = if (settings.getString(TOKEN).isBlank()) null else settings.getString(TOKEN, "")
        set(value) {
            print("value - $value")
            settings.putString(TOKEN, value.orEmpty())
        }

    override fun exit() {
        token = null
    }

    companion object {
        private const val TOKEN = "TOKEN"
    }
}