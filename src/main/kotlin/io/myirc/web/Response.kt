package io.myirc.web

data class Response(val code: ResponseCode, val commandName: String, val commandResponse: JsonBuilder) {

    companion object {
        fun error(commandName: String, message: String): Response {
            return Response(ResponseCode.ERROR, commandName, JsonBuilder().append("message", message))
        }

        fun empty(): Response {
            return Response(ResponseCode.EMPTY, "", JsonBuilder())
        }
    }

    fun build(): String {
        return JsonBuilder()
            .append("code", code.serialize())
            .append("commandName", commandName)
            .append("commandResponse", commandResponse)
            .build()
    }


}
