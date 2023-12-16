package io.myirc.commands

import io.myirc.channels.Channel
import io.myirc.web.JsonBuilder
import io.myirc.web.Response
import io.myirc.web.ResponseCode

class CommandJoin: CommandInterface {
    override val commandName: String = "JOIN"

    override suspend fun handleCommand(commandData: CommandData): Response {
        commandData.command.params.forEach {
            val channel = Channel.parse(it, commandData.server);
            if (channel != null) {
                val serverChannel = commandData.channels.find { it.name == channel.name }
                return if (serverChannel != null) {
                    serverChannel.addUser(commandData.prefix.user)
                    commandData.prefix.user.addChannel(serverChannel)
                    commandData.prefix.user.joinChannel(serverChannel)
                    val msg = "you joined channel ${channel.name}"
                    Response(ResponseCode.CHANNEL_JOINED, commandName, JsonBuilder().append("response", msg))

                } else {
                    commandData.logger.error { "Channel not found: ${channel.name}" }
                    Response.error(commandName, "Channel not found: ${channel.name}")
                }

            } else {
                commandData.logger.error { "Invalid channel name: $it" }
                return  Response.error(commandName, "Invalid channel name: $it")
            }
        }

        return Response.error(commandName, "No channel specified")
    }
}