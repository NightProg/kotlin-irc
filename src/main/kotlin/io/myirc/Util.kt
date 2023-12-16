package io.myirc

object Util {
    fun generateUUID(): String {
        return java.util.UUID.randomUUID().toString()
    }
}