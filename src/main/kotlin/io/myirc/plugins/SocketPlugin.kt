package io.myirc.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import io.myirc.IRCServer
import io.myirc.commands.CommandAst
import kotlinx.coroutines.channels.consumeEach
import java.io.IOException
import java.time.Duration

fun Application.installWebSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        webSocket("/myirc") {
            send("Welcome to My IRC Server!")
            val server = IRCServer()
            try {
                incoming.consumeEach { frame ->
                    when (frame) {
                        is Frame.Text -> {
                            val message = frame.readText().trim()
                            server.logger.info { "Received message from client: $message" }
                            val command = CommandAst.parse(message, server)
                            server.logger.info { "command: $command" }
                            if (command != null) {
                                val res = command.handleCommands().build()
                                send(res)
                            } else {
                                server.logger.error { "Invalid command: $message" }
                                send("ERROR :Invalid command: $message")
                            }
                        }

                        is Frame.Binary -> {
                            // Handle binary frames if needed
                        }

                        is Frame.Ping -> {
                            // Handle ping frames
                        }

                        is Frame.Pong -> {
                            // Handle pong frames
                        }

                        is Frame.Close -> {
                            // Handle close frames
                        }

                        else -> {
                            server.logger.warn { "Unsupported frame type: $frame" }
                        }
                    }
                }
            } catch (e: IOException) {
                server.logger.error(e) { "Error during WebSocket communication" }
            } finally {
                server.logger.info { "Client disconnected" }
                // Perform cleanup if needed
            }
        }
    }
}