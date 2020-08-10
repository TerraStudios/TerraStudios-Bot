package com.terrastudios.tsbot.events

import com.terrastudios.tsbot.TSBot
import com.terrastudios.tsbot.core.events.MessageEvent
import com.terrastudios.tsbot.core.util.MessageType
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CodeListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return

        val messageEvent = MessageEvent(TSBot.listener, event)

        val regex = "```(.*)```".toRegex()

        messageEvent.reply(MessageType.INFO, "Regex Parse", "Found ${regex.find(messageEvent.text.replace("\n", ""))?.groupValues?.get(0)}")
    }


}