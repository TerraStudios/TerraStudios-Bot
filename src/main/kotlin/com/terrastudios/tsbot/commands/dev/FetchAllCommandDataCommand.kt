package com.terrastudios.tsbot.commands.dev

import com.terrastudios.tsbot.TSBot
import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.events.extensions.edit
import com.terrastudios.tsbot.core.util.MessageType
import net.dv8tion.jda.api.Permission
import net.steppschuh.markdowngenerator.table.Table
import org.jpaste.pastebin.Pastebin


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
        event.reply(MessageType.WARNING, "Command Data", "Fetching..", {

            val builder = Table.Builder()
                .withAlignment(Table.ALIGN_CENTER)
                .addRow("Command Name", "Description", "Usage")

            TSBot.commandHandler.commandMap.values.forEach { command ->
                val annotation = command.getAnnotation(DiscordCommand::class.java)

                builder.addRow(annotation.commandName, annotation.description, annotation.usage)
            }


            try {
                val link = Pastebin.pastePaste(
                    TSBot.config.pastebinDevKey,
                    builder.build().toString()
                )

                it.edit(MessageType.INFO, "Command Data", "$link")
            } catch (e: Exception) {
                it.edit(
                    MessageType.ERROR,
                    "Command Data",
                    "An exception occured while sending a POST request to pastebin.\n\n**Error:** ${e.message}"
                )

                println(builder.build())
            }


        })
    }

}