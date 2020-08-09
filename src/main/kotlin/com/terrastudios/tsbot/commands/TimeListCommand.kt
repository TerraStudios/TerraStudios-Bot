package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.TSBot
import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.events.extensions.edit
import com.terrastudios.tsbot.core.util.MessageType
import org.jpaste.pastebin.Pastebin
import java.time.ZoneId

class TimeListCommand {

    @DiscordCommand(
        commandName = "listtimes",
        usage = "listtimes",
        aliases = ["timeregions"],
        description = "Lists all of the TimeZone regions to select from"
    )
    fun listTime(event: CommandEvent) {
        try {
            event.reply(MessageType.WARNING, "TimeZone Regions", "Fetching...",  {
                val link = Pastebin.pastePaste(
                    TSBot.config.pastebinDevKey,
                    ZoneId.getAvailableZoneIds().joinToString("\n", "Available TimeZone Regions")
                )
                it.edit(MessageType.INFO, "TimeZone Regions", "$link")
            })

        } catch (e: Exception) {
            e.printStackTrace()
            event.reply(MessageType.ERROR, "TimeZone Regions", "An error has occurred when accessing the PastebinAPI.")
        }

    }

}