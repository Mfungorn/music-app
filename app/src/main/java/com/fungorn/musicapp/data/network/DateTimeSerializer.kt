package com.fungorn.musicapp.data.network

import com.fungorn.musicapp.common.Const
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.lang.reflect.Type

class DateTimeSerializer : JsonSerializer<DateTime> {
    override fun serialize(
        src: DateTime,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(
            DateTimeFormat
                .forPattern(Const.API_DATE_FORMAT)
                .print(src)
        )
    }
}