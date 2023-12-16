package io.myirc.web

class JsonBuilder {
    private val builder = StringBuilder()

    private fun buildInt(i: Int): String {
        return i.toString()
    }

    private fun buildString(s: String): String {
        return "\"$s\""
    }

    private fun buildBoolean(b: Boolean): String {
        return b.toString()
    }

    private fun buildAny(value: Any?): String {
        return when {
            value is Int -> buildInt(value)
            value is String -> buildString(value)
            value is Boolean -> buildBoolean(value)
            value is List<*> -> buildList(value)
            value is Map<*, *> && value.keys.all { it is String } -> buildMap(value as Map<String, Any?>)
            else -> throw Exception("Unknown type")
        }
    }
    private fun<T> buildList(l: List<T>): String {
        builder.append("[")
        l.forEach {
            builder.append(buildAny(it))
            builder.append(",")
        }
        builder.append("]")
        return builder.toString()
    }

    private fun <T> buildMap(m: Map<String, T>): String {
        builder.append("{")
        m.forEach {
            builder.append("\"${it.key}\":${buildAny(it.value)},")
        }
        builder.append("}")
        return builder.toString()
    }

    fun build(): String {
        return "{${builder.toString().dropLast(1)}}"
    }

    fun append(key: String, value: String): JsonBuilder {
        builder.append("\"$key\":\"$value\",")
        return this
    }

    fun append(key: String, value: Int): JsonBuilder {
        builder.append("\"$key\":$value,")
        return this
    }

    fun append(key: String, value: Boolean): JsonBuilder {
        builder.append("\"$key\":$value,")
        return this
    }

    fun <T> append(key: String, value: List<T>): JsonBuilder {
        builder.append("\"$key\": ${buildList(value)},")
        return this
    }

    fun <T> append(key: String, value: Map<String, T>): JsonBuilder {
        builder.append("\"$key\": ${buildMap(value)},")
        return this
    }

    fun append(key: String, value: JsonBuilder): JsonBuilder {
        builder.append("\"$key\": ${value.build()},")
        return this
    }

}