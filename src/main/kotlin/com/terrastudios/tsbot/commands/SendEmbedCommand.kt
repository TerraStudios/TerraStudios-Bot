package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.extensions.EmbedFactory
import com.terrastudios.tsbot.core.util.extensions.awaitResponse
import net.dv8tion.jda.api.Permission

class SendEmbedCommand {

    @DiscordCommand(
        commandName = "sendembed",
        description = "Sends an embed to a specified channel on the server.",
        usage = "sendembed",
        permission = Permission.ADMINISTRATOR
    )
    fun sendEmbed(event: CommandEvent) {
        event.reply(
            MessageType.INFO,
            "Embed Builder",
            "What channel would you like to send the embed to?",
            { channelMessage ->

                channelMessage.awaitResponse(event) { e ->
                    val text = e.text
                    val channel = event.jda.getTextChannelById(text.replace("[\\\\<>@#&!]".toRegex(), ""))
                    e.reply(
                        MessageType.INFO,
                        "Embed Builder",
                        "What would you like the title of the embed to be?",
                        { titleMessage ->
                            titleMessage.awaitResponse(event) { e ->
                                val title = e.text
                                e.reply(
                                    MessageType.INFO,
                                    "Embed Builder",
                                    "What would you like the description to be?",
                                    { descriptionMessage ->
                                        descriptionMessage.awaitResponse(event) { e ->
                                            val description = e.text
                                            channel?.sendMessage(
                                                EmbedFactory.getEmbed(
                                                    MessageType.INFO,
                                                    title,
                                                    description
                                                )
                                            )?.queue()
                                            e.reply(
                                                MessageType.SUCCESS,
                                                "Success",
                                                "Sent an embed to channel <#${channel?.id}>"
                                            )
                                        }
                                    })
                            }
                        })
                }
            })
    }


}