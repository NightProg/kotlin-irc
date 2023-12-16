package io.myirc.channels

import io.myirc.IRCServer
import io.myirc.Util
import io.myirc.users.User

class Channel(val name: String) {
    private val users = mutableListOf<User>()
    private val messages = mutableListOf<Message>()
    val uuid = Util.generateUUID()

    companion object {
        fun parse(name: String, server: IRCServer): Channel? {
            if (name.startsWith("#")) {
                val channelName = name.substring(1)
                return server.getChannelByName(channelName)
            }
            return null
        }
    }

    fun addUser(user: User) {
        users.add(user)
    }

    fun removeUser(user: User) {
        users.remove(user)
    }

    fun addMessage(message: Message) {
        messages.add(message)
    }

    fun getMessages(): List<Message> {
        return messages
    }

    fun getMessagesSince(lastMessage: Message): List<Message> {
        val index = messages.indexOf(lastMessage)
        return messages.subList(index + 1, messages.size)
    }



}
