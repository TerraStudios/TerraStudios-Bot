package com.terrastudios.tsbot.core.events

import com.terrastudios.tsbot.TSBot
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.function.Consumer

class CommandEvent(private val messageReceivedEvent : MessageReceivedEvent, val args : Array<String>) {

    val message = messageReceivedEvent.message

    val author = messageReceivedEvent.author
    val jda = messageReceivedEvent.jda

    fun reply(message: MessageEmbed, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
        messageReceivedEvent.message.channel.sendMessage(message).queue(success, error)
    }

    fun reply(message: String, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
        messageReceivedEvent.message.channel.sendMessage(message).queue(success, error)
    }

    fun edit(newMessage: MessageEmbed, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
        messageReceivedEvent.message.editMessage(newMessage).queue(success, error)
    }

    fun edit(newMessage: String, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
        messageReceivedEvent.message.editMessage(newMessage).queue(success, error)
    }

}