package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.events.MessageEvent
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.extensions.EmbedFactory
import net.dv8tion.jda.api.Permission
import java.util.function.Consumer

class SendEmbedCommand {

    @DiscordCommand(
        commandName = "sendembed",
        description = "Sends an embed to a specified channel on the server.",
        usage = "sendembed",
        permission = Permission.ADMINISTRATOR
    )
    fun sendEmbed(event: CommandEvent) {
        event.reply(MessageType.INFO, "Embed Builder", "What channel would you like to send the embed to?", Consumer {
            event.listener.listen<MessageEvent>({ e -> e.author == event.author && e.message.channel == event.message.channel && e.message != event.message}, {
                e ->
                val text = e.text
                val channel = event.jda.getTextChannelById(text.replace("[\\\\<>@#&!]".toRegex(), ""))
                e.reply(MessageType.INFO, "Embed Builder", "What would you like the title of the embed to be?", Consumer {
                    event.listener.listen<MessageEvent>({ e -> e.author == event.author && e.message.channel == event.message.channel && e.message != event.message }, {
                        e ->
                        val title = e.text
                        e.reply(MessageType.INFO, "Embed Builder", "What would you like the description to be?", Consumer {
                            event.listener.listen<MessageEvent>({ e -> e.author == event.author && e.message.channel == event.message.channel && e.message != event.message }, {
                                e ->
                                val description = e.text
                                channel?.sendMessage(EmbedFactory.getEmbed(MessageType.INFO, title, description))?.queue()
                                e.reply(MessageType.SUCCESS, "Success", "Sent an embed to channel <#${channel?.id}>")
                            })
                        })
                    })
                })
            })
        })
    }


}