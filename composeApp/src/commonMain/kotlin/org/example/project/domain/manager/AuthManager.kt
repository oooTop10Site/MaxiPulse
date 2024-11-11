package org.example.project.domain.manager

interface AuthManager {
    var token: String?
    fun exit()
}