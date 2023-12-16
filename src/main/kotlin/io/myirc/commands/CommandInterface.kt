package io.myirc.commands

import io.myirc.web.Response

interface CommandInterface {
    val commandName: String

    suspend fun handleCommand(commandData: CommandData): Response
}