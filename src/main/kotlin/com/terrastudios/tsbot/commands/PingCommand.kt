package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.events.extensions.edit
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.extensions.EmbedFactory
import java.time.temporal.ChronoUnit
import java.util.function.Consumer


class PingCommand {

    @DiscordCommand(commandName = "ping", description = "Returns the current latency of the bot.", usage = "ping", maxArgs = 0)
    fun onMessageReceived(event: CommandEvent) {
        event.reply(
            EmbedFactory.getEmbed(MessageType.INFO, "Pong!", ""),
            Consumer {
                val ping = event.message.timeCreated.until(it.timeCreated, ChronoUnit.MILLIS)
                it.edit(
                    EmbedFactory.getEmbed(
                        MessageType.SUCCESS,
                        "Pong!",
                        "Ping: ${ping}ms\nWebsocket: ${event.jda.gatewayPing}ms"
                    )
                )
            })
    }


}