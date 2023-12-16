package io.myirc.commands

import io.myirc.IRCServer
import io.myirc.web.Response
import java.util.regex.Pattern


data class CommandAst(val prefix: Prefix?, val command: String, val params: List<String>, val server: IRCServer) {

    private val allCommand: List<CommandInterface> = listOf(
        CommandJoin(),
        CommandWho()
    )

    companion object {
        // the regex to check if the input is a valid IRC command
        private val SYNTAX_REGEX = Pattern.compile("^(:([A-Za-z0-9_]+)!([A-Za-z0-9_]+)@([A-Za-z0-9.]+)\\s+)?([A-Za-z]+)(\\s+.+)?$")

        /**
         * Check if the input is a valid IRC command
         * @param input The input to check
         * @return true if the input is a valid IRC command, false otherwise
         *
         * Example:
         *
         * ```kotlin
         * CommandAst.check(":Nickname!user@hostname PRIVMSG #Channel :Hello World!") // true
         * CommandAst.check("PRIVMSG #Channel :Hello World!") // true
         * CommandAst.check("dd") // false
         * ```
         *
         * Note:
         * The syntax of an IRC command is defined in RFC 1459 Section 2.3.1
         */
        private fun check(input: String): Boolean = SYNTAX_REGEX.matcher(input).matches() && input.indexOf(':') != input.indexOf(
            ": ", ignoreCase = true)

        /**
         * Parse the input into a CommandAst object
         * @param input The input to parse
         * @return A CommandAst object if the input is a valid IRC command, null otherwise
         *
         * Example:
         *
         * ```kotlin
         * CommandAst.parse(":Nickname!user@hostname PRIVMSG #Channel :Hello World!") // CommandAst(Prefix(nick=Nickname, user=user, host=hostname), PRIVMSG, [#Channel, :Hello World!])
         * CommandAst.parse("PRIVMSG #Channel :Hello World!") // CommandAst(null, PRIVMSG, [#Channel, :Hello World!])
         * CommandAst.parse("dd") // null
         * ```
         */
        fun parse(input: String, server: IRCServer): CommandAst? {
            if (!check(input)) {
                return null
            }
            return if (input.startsWith(":")) {
                val splitInput = input.split(" ")
                val prefix = Prefix.parse(splitInput[0], server)
                prefix?.let {
                    if (splitInput.size < 2) {
                        return null
                    }
                    val command = splitInput[1]
                    val params = splitInput.subList(2, splitInput.size)
                    CommandAst(prefix, command, params, server)
                }

            } else {
                null
            }

        }
    }

    suspend fun handleCommands(): Response {
        var res: Response? = null
        allCommand.forEach {
            if (it.commandName == command) {
                val commandData = CommandData(server, prefix!!, this)
                res = it.handleCommand(commandData)
            }
        }

        if (res == null) {
            res = Response.error(command, "Invalid command: $command")
        }

        return res!!
    }
}