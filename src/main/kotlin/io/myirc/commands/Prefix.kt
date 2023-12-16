package io.myirc.commands

import io.myirc.IRCServer
import io.myirc.users.User


data class Prefix(val nick: String, val user: User, val host: String) {

    companion object {
        fun parse(prefix: String, server: IRCServer): Prefix? {

            if (prefix.startsWith(":")) {
                // :Nickname!user@hostname
                val prefixWithoutColon = prefix.substring(1)
                val indexOfExclamation = prefixWithoutColon.indexOf("!")
                val indexOfAt = prefixWithoutColon.indexOf("@")

                if (indexOfExclamation != -1 && indexOfAt != -1) {
                    // :Nickname!user@hostname
                    val nick = prefixWithoutColon.substring(0, indexOfExclamation)
                    val userStr = prefixWithoutColon.substring(indexOfExclamation + 1, indexOfAt)
                    val user = server.getUsers().find { it.nick == userStr } ?: return null

                    val host = prefixWithoutColon.substring(indexOfAt + 1)
                    return Prefix(nick, user, host)
                }
            }
            return null
        }
    }

}
