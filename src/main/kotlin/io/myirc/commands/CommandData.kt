package io.myirc.commands

import io.myirc.IRCServer

data class CommandData(val server: IRCServer, val prefix: Prefix, val command: CommandAst) {
    val logger = server.logger
    val writer = server.writer
    val users = server.getUsers()
    val channels = server.getChannels()
}
