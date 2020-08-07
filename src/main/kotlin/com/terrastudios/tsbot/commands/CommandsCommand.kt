package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.TSBot
import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.extensions.EmbedFactory
import java.util.stream.Collectors

class CommandsCommand {
    @DiscordCommand("commands", description = "Lists all of the commands for the bot.", usage = "commands")
    fun commands(event : CommandEvent) {
        event.reply(EmbedFactory.getEmbed(MessageType.INFO, "Commands", "`${TSBot.commandHandler.commandMap.keys.stream().collect(Collectors.joining("`, `"))}`"))
    }
}