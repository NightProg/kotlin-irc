package io.myirc.web

enum class ResponseCode {
    WRONG_PASSWORD,
    USER_NOT_FOUND,
    USER_ALREADY_EXISTS,
    USER_CREATED,
    USER_LOGGED_IN,
    USER_LOGGED_OUT,
    USER_NOT_LOGGED_IN,
    CHANNEL_NOT_FOUND,
    CHANNEL_ALREADY_EXISTS,
    CHANNEL_CREATED,
    CHANNEL_JOINED,
    CHANNEL_LEFT,
    CHANNEL_NOT_JOINED,
    CHANNEL_NOT_LOGGED_IN,
    CHANNEL_NOT_OWNER,
    CHANNEL_OWNER,
    YOU_ARE_NOT_IN_A_CHANNEL,
    SUCCESS,
    ERROR,
    EMPTY;

    fun serialize(): Int {
        return when (this) {
            WRONG_PASSWORD -> 1
            USER_NOT_FOUND -> 2
            USER_ALREADY_EXISTS -> 3
            USER_CREATED -> 4
            USER_LOGGED_IN -> 5
            USER_LOGGED_OUT -> 6
            USER_NOT_LOGGED_IN -> 7
            CHANNEL_NOT_FOUND -> 8
            CHANNEL_ALREADY_EXISTS -> 9
            CHANNEL_CREATED -> 10
            CHANNEL_JOINED -> 11
            CHANNEL_LEFT -> 12
            CHANNEL_NOT_JOINED -> 13
            CHANNEL_NOT_LOGGED_IN -> 14
            CHANNEL_NOT_OWNER -> 15
            CHANNEL_OWNER -> 16
            YOU_ARE_NOT_IN_A_CHANNEL -> 17
            SUCCESS -> 18
            ERROR -> 19
            EMPTY -> 20
        }
    }
}