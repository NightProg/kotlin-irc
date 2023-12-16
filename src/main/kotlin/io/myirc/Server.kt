package io.myirc

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.myirc.channels.Channel
import io.myirc.users.User
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter


class IRCServer {
    val logger: KLogger = KotlinLogging.logger { }
    private val users = mutableListOf<User>(
        User("admin", "admin", "0.0.0.0", "abc")
    )
    private val channels = mutableListOf(
        Channel("test"),
        Channel("foo")
    )

    var writer = PrintWriter(System.out, true)
    private var reader = BufferedReader(InputStreamReader(System.`in`))




    fun getUsers(): List<User> {
        return users
    }

    fun getChannels(): MutableList<Channel> {
        return channels
    }

    fun getChannelByName(name: String): Channel? {
        return channels.find { it.name == name }
    }

    fun getChannel(uuid: String): Channel? {
        return channels.find { it.uuid == uuid }
    }




}

