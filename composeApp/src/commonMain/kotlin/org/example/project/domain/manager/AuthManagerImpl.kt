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

    override var isPay: Boolean?
        get() = if (settings.getString(IS_PAY).isBlank()) null else settings.getBoolean(IS_PAY, false)
        set(value) {
            print("value - $value")
            settings.putBoolean(TOKEN, value == true)
        }

    override fun exit() {
        token = null
    }

    companion object {
        private const val TOKEN = "TOKEN"
        private const val IS_PAY = "IS_PAY"
    }
}