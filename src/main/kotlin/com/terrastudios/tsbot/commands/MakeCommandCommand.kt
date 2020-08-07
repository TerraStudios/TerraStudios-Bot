package com.terrastudios.tsbot.commands

import com.beust.klaxon.Klaxon
import com.terrastudios.tsbot.TSBot
import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.data.ColorData
import com.terrastudios.tsbot.core.data.CustomCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.scheduling.RunnableScheduler
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.ResourceUtils
import com.terrastudios.tsbot.core.util.extensions.isInt
import com.terrastudios.tsbot.core.util.extensions.withinBounds
import net.dv8tion.jda.api.Permission
import java.awt.Color
import java.io.FileWriter
import java.util.concurrent.TimeUnit


class MakeCommandCommand {

    init {

        try {
            val parsedObjects = ResourceUtils.getResourceArray<CustomCommand>("/customcommands.json")

            parsedObjects.forEach { TSBot.commandHandler.customCommands[it.name] = it }

            RunnableScheduler.Builder(Runnable {
                val json = Klaxon().toJsonString(TSBot.commandHandler.customCommands.values)

                val writer = FileWriter(ResourceUtils.getFilePath("/customcommands.json"))
                writer.write(json)
                writer.close()
            }).every(10, TimeUnit.SECONDS).after(5, TimeUnit.SECONDS).build().run()


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @DiscordCommand(
        commandName = "makecommand",
        description = "Makes a command that will embed a message on execution.",
        usage = "makecommand <command-name>",
        aliases = ["mckcommand", "makecmd", "createcommand", "createcmd", "addcommand"],
        permission = Permission.ADMINISTRATOR,
        minArgs = 1,
        maxArgs = 1
    )
    fun makeCommand(event: CommandEvent) {

        val newCustomCommand = CustomCommand(name = event.args[0])
        TSBot.commandHandler.customCommands[event.args[0]] = newCustomCommand
        event.reply(
            MessageType.SUCCESS,
            "Success",
            "Added command `${event.args[0]}`. To edit this command, use `${TSBot.config.prefix}editcommand`."
        )
    }

    @DiscordCommand(
        commandName = "editcommand",
        description = "Edits an existing command",
        usage = "editcommand <command-name> <title | text | color | image | name> <new-value>",
        minArgs = 3
    )
    fun editCommand(event: CommandEvent) {
        if (TSBot.commandHandler.customCommands.containsKey(event.args[0])) {
            val command = TSBot.commandHandler.customCommands[event.args[0]]!!
            val newValue = event.args[2]
            when (event.args[1].toLowerCase()) {
                "title" -> {
                    command.title = event.args.copyOfRange(2, event.args.size).joinToString(" ")
                    event.reply(MessageType.SUCCESS, "Success", "Changed title to ${event.args.copyOfRange(2, event.args.size).joinToString(" ")}.")
                }
                "text" -> {
                    command.text = event.args.copyOfRange(2, event.args.size).joinToString(" ")
                    event.reply(MessageType.SUCCESS, "Success", "Changed description to ${event.args.copyOfRange(2, event.args.size).joinToString(" ")}.")
                }
                "color" -> {
                    if (event.args.size == 5) {
                        if (!event.args[2].isInt() || !event.args[2].isInt() || !event.args[4].isInt() ||
                            !event.args[2].toInt().withinBounds(0, 255) || !event.args[2].toInt().withinBounds(0, 255) || !event.args[4].toInt().withinBounds(0, 255)) {
                            event.reply(MessageType.ERROR, "Invalid RGB value", "One of the specified RGB values were either out of bounds or not an Integer.")
                        } else {
                            command.color = ColorData(event.args[2].toInt(), event.args[3].toInt(), event.args[4].toInt())
                            event.reply(
                                MessageType.SUCCESS,
                                "Success",
                                "Changed color to (${event.args[2]}, ${event.args[3]}, ${event.args[4]})."
                            )
                        }
                    } else {
                        event.reply(
                            MessageType.ERROR,
                            "Invalid color parameters",
                            "Please specify the 3 RGB values for the color.\n\n`${TSBot.config.prefix}editmessage ${event.args[0]} color R G B"
                        )
                    }
                }
                "image" -> {
                    command.imageURL = newValue
                    event.reply(MessageType.SUCCESS, "Success", "Changed image URL to $newValue.")
                }
                "name" -> {
                    TSBot.commandHandler.customCommands.remove(command.name)
                    command.name = newValue
                    TSBot.commandHandler.customCommands[newValue] = command
                    event.reply(MessageType.SUCCESS, "Success", "Changed name to ${command.name}.")
                }


                else -> {
                    event.reply(
                        MessageType.ERROR,
                        "Invalid Custom Command Property",
                        "Properties: `title`, `text`, `color`, `image`, `name`"
                    )
                }
            }
            TSBot.commandHandler.customCommands[event.args[0]]
        } else {
            event.reply(MessageType.ERROR, "Invalid Custom Command", "Please specify an existing Custom Command.")
        }
    }
}