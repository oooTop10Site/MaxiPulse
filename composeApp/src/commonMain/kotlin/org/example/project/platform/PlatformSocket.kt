package org.example.project.platform

//работа с сокетом
internal expect class PlatformSocket(
    url: String,
    token: String
) {
    fun openSocket(listener: PlatformSocketListener)
    fun closeSocket(code: Int, reason: String)
    fun sendMessage(msg: String)
}
interface PlatformSocketListener {
    fun onOpen(){}
    fun onFailure(t: Throwable)
    fun onMessage(msg: String)
    fun onClosing(code: Int, reason: String) {}
    fun onClosed(code: Int, reason: String){}
}