package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.extensions.EmbedFactory

class PlayCommand {
    @DiscordCommand(
        commandName = "play",
        description = "Sends a DM to the user about how to access the game.",
        usage = "play",
        minArgs = 0,
        maxArgs = 0
    )
    fun play(event: CommandEvent) {
        event.reply(MessageType.INFO, "Play", "haha play command go brr")
    }
}