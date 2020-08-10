package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.events.extensions.edit
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.RequestType
import com.terrastudios.tsbot.core.util.WebRequestSender
import com.terrastudios.tsbot.core.util.extensions.EmbedFactory

class TestWebCommand {

    @DiscordCommand(
        commandName = "testweb",
        usage = "testweb",
        description = "Sends a test web request to a website and returns the response."
    )
    fun testWeb(command : CommandEvent) {
        val requestSender = WebRequestSender("https://paste.feed-the-beast.com/api/create", RequestType.POST)

        requestSender.addParam("text", command.text)

        command.reply(MessageType.WARNING, "Web Request", "Sending POST request...", { message ->
            requestSender.send {
                message.edit(MessageType.SUCCESS, "Response", it.response)
            }
        })

    }



}