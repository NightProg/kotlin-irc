package io.myirc.commands

enum class CommandType {
    JOIN,
    PRIVMSG,
    PING,
    QUIT,
    PART,
    PASS,
    NICK,
    USER,
    MODE,
    TOPIC,
    LIST,
    NAMES,
    NOTICE,
    UNKNOWN
}