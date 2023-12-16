package io.myirc.users

import io.myirc.Util
import io.myirc.channels.Channel

data class User(val nick: String, val user: String, val host: String, val password: String) {
    // Todo: change password to a hash
    private var channels = mutableListOf<Channel>()
    var currentChannelUUID: String? = null
    val uuid = Util.generateUUID()

    fun addChannel(channel: Channel) {
        channels.add(channel)
    }

    fun removeChannel(channel: Channel) {
        channels.remove(channel)
    }

    fun getChannels(): List<Channel> {
        return channels
    }

    fun getChannelByName(name: String): Channel? {
        return channels.find { it.name == name }
    }


    fun joinChannel(channel: Channel) {
        val index = channels.map {
            it.name
        }.indexOf(channel.name)
        if (index != -1) {
            currentChannelUUID = channels[index].uuid
        }
    }

}