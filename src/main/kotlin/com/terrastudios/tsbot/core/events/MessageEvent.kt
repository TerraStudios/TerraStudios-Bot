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

open class MessageEvent(val listener: EventListener, val messageReceivedEvent : MessageReceivedEvent): GenericEvent {
    val message = messageReceivedEvent.message
    open val text = message.contentRaw

    val author = messageReceivedEvent.author
    val jda = messageReceivedEvent.jda

    fun reply(message: MessageEmbed, success: (Message) -> Unit = { }, error: (Throwable) -> Unit = { }) {
        messageReceivedEvent.message.channel.sendMessage(message).queue(Consumer(success), Consumer(error))
    }

    fun reply(message: String, success: (Message) -> Unit = { }, error:(Throwable) -> Unit = { }) {
        messageReceivedEvent.message.channel.sendMessage(message).queue(Consumer(success), Consumer(error))
    }

    fun reply(type : MessageType, title: String, description: String, success: (Message) -> Unit = { }, error: (Throwable) -> Unit = { }) {
        messageReceivedEvent.message.channel.sendMessage(EmbedFactory.getEmbed(type, title, description)).queue(Consumer(success), Consumer(error))
    }

    fun edit(newMessage: MessageEmbed, success: (Message) -> Unit = { }, error: (Throwable) -> Unit = { }) {
        messageReceivedEvent.message.editMessage(newMessage).queue(Consumer(success), Consumer(error))
    }

    fun edit(newMessage: String, success: (Message) -> Unit = { }, error: (Throwable) -> Unit = { }) {
        messageReceivedEvent.message.editMessage(newMessage).queue(Consumer(success), Consumer(error))
    }

    override fun getResponseNumber(): Long {
        return -1
    }

    override fun getJDA(): JDA {
        return jda
    }
}