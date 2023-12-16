package io.myirc.commands

import io.myirc.web.JsonBuilder
import io.myirc.web.Response
import io.myirc.web.ResponseCode

class CommandWho: CommandInterface {
    override val commandName: String = "WHO"

    override suspend fun handleCommand(commandData: CommandData): Response {
        commandData.logger.info { "WHO command received" }
        commandData.logger.info { "commandData.prefix.user.currentChannelUUID: ${commandData.prefix.user.currentChannelUUID}" }
        commandData.prefix.user.currentChannelUUID?.let {
            val channel = commandData.channels.find { channel -> channel.uuid == it }
            if (channel != null) {
                return Response(ResponseCode.SUCCESS, commandName, JsonBuilder()
                    .append("channel", channel.name)
                    .append("users", commandData.prefix.user.nick)
                )
            } else {
                commandData.logger.error { "Channel not found: $it" }
                return Response.error(commandName, "Channel not found: $it")
            }

        }
        return Response.error(commandName, "No channel specified")
    }
}