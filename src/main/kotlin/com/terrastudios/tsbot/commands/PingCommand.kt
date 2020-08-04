package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.extensions.edit
import com.terrastudios.tsbot.core.events.extensions.reply
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.internal.utils.PermissionUtil
import java.time.temporal.ChronoUnit
import java.util.function.Consumer


class PingCommand {

    @DiscordCommand(commandName = "ping", description = "Returns the current latency of the bot.")
    fun onMessageReceived(event: MessageReceivedEvent) {
        event.reply("Pong!", Consumer {
            val ping = event.message.timeCreated.until(it.timeCreated, ChronoUnit.MILLIS)
            it.edit("Ping: ${ping}ms\nWebsocket: ${event.jda.gatewayPing}ms")
        })
    }



}