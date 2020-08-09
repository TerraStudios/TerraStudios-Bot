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

    val author = messageReceivedEvent.author
    val jda = messageReceivedEvent.jda

    fun reply(message: MessageEmbed, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
        messageReceivedEvent.message.channel.sendMessage(message).queue(success, error)
    }

    fun reply(message: String, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
        messageReceivedEvent.message.channel.sendMessage(message).queue(success, error)
    }

    fun reply(type : MessageType, title: String, description: String, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
        messageReceivedEvent.message.channel.sendMessage(EmbedFactory.getEmbed(type, title, description)).queue(success, error)
    }

    fun edit(newMessage: MessageEmbed, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
        messageReceivedEvent.message.editMessage(newMessage).queue(success, error)
    }

    fun edit(newMessage: String, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
        messageReceivedEvent.message.editMessage(newMessage).queue(success, error)
    }

    override fun getResponseNumber(): Long {
        return -1
    }

    override fun getJDA(): JDA {
        return jda
    }
}