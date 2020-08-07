package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.TSBot
import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.extensions.EmbedFactory

class HelpCommand {

    @DiscordCommand(commandName = "help", description = "Displays the information about a command.", usage = "help <command-name>", minArgs = 1, maxArgs = 1)
    fun help(event: CommandEvent) {
        if (TSBot.commandHandler.commandMap.containsKey(event.args[0].toLowerCase())) {
            val command = TSBot.commandHandler.commandMap[event.args[0]]!!.getAnnotation(DiscordCommand::class.java)
            event.reply(EmbedFactory.getEmbed(MessageType.INFO, command.commandName, "*${command.description}*\n\n**Usage:** `${TSBot.config.prefix}${command.usage}`"))
        } else {
            event.reply(EmbedFactory.getEmbed(MessageType.ERROR, "Invalid Command", "Please enter a valid command name."))
        }
    }


}