package com.fungorn.musicapp.data.network

import com.fungorn.musicapp.common.Const
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.lang.reflect.Type

class DateTimeDeserializer : JsonDeserializer<DateTime> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): DateTime {
        return DateTimeFormat
            .forPattern(Const.API_DATE_FORMAT)
            .parseDateTime(json.asJsonPrimitive.asString)
    }
}
