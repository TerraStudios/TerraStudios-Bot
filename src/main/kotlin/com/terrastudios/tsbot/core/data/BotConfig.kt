package com.terrastudios.tsbot.core.data

import com.beust.klaxon.Json

data class BotConfig(
    @Json(name = "prefix") val prefix : String,
    @Json(name = "token") val token : String,
    @Json(name = "pastebin-dev-key") val pastebinDevKey : String
)