package org.example.project.ext

fun String.toToken(): String {
    return "Bearer $this"
}