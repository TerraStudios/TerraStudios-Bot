package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.TSBot
import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.extensions.EmbedFactory
import net.dv8tion.jda.api.entities.Role
import java.util.stream.Collectors

class InfoCommand {

    @DiscordCommand(commandName = "info", description = "Displays information about the server", usage = "info", minArgs = 0, maxArgs = 0)
    fun info(event : CommandEvent) {

        val roles = event.message.guild.roles
        val together = roles.stream().map(Role::getName).collect(Collectors.joining("`, `"))

        event.reply(MessageType.INFO,
        "Server Statistics",
        "**Member Count**: ${event.message.guild.memberCount}\n\n" +
             "**Channel Count**: ${event.message.guild.channels.size}\n\n" +
             "**Role List:** `${together}`")
    }


}