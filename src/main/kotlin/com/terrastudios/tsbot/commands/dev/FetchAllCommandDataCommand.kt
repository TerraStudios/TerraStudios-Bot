package com.terrastudios.tsbot.commands.dev

import com.sun.org.apache.bcel.internal.generic.TABLESWITCH
import com.terrastudios.tsbot.TSBot
import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.events.extensions.edit
import com.terrastudios.tsbot.core.util.MessageType
import net.dv8tion.jda.api.Permission
import net.steppschuh.markdowngenerator.table.Table
import org.jpaste.pastebin.Pastebin
import java.time.ZoneId
import java.util.function.Consumer


/**
 * Class is used for fetching data in a table format for the README.md
 */
class FetchAllCommandDataCommand {

    @DiscordCommand(
        commandName = "fetchallcommanddata",
        description = "Retrieves all command data in a table format",
        usage = "fetchallcommanddata",
        permission = Permission.ADMINISTRATOR
    )
    fun fetchAllCommandData(event: CommandEvent) {
        event.reply(MessageType.WARNING, "Command Data", "Fetching..", Consumer {

            val builder = Table.Builder()
                .withAlignment(Table.ALIGN_CENTER)
                .addRow("Command Name", "Description", "Usage")

            TSBot.commandHandler.commandMap.values.forEach {
                val annotation = it.getAnnotation(DiscordCommand::class.java)

                builder.addRow(annotation.commandName, annotation.description, annotation.usage)
            }



            val link = Pastebin.pastePaste(
                TSBot.config.pastebinDevKey,
                builder.build().toString()
            )
            it.edit(MessageType.INFO, "Command Data", "$link")
        })
    }

}