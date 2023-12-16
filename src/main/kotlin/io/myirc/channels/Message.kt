package io.myirc.channels

import io.myirc.users.User

data class Message(val sender: User, val content: String) {}