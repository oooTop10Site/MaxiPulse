package org.example.project.domain.manager

interface AuthManager {
    var token: String?
    var isPay: Boolean?

    fun exit()
}