package com.terrastudios.tsbot.core.events

import com.terrastudios.tsbot.core.events.listener.EventListener
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.extensions.EmbedFactory
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.GenericEvent

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.function.Consumer

class CommandEvent(listener: EventListener, messageReceivedEvent: MessageReceivedEvent, val args : Array<String>) :
    MessageEvent(listener, messageReceivedEvent) {



    override fun getResponseNumber(): Long {
        return 3
    }

    override fun getJDA(): JDA {
        return messageReceivedEvent.jda
    }

}