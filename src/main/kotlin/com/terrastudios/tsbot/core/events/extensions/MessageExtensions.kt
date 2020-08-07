package com.terrastudios.tsbot.core.events.extensions

import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.extensions.EmbedFactory
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.function.Consumer

fun MessageReceivedEvent.reply(message: MessageEmbed, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
    this.message.channel.sendMessage(message).queue(success, error)
}

fun MessageReceivedEvent.reply(message: String, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
    this.message.channel.sendMessage(message).queue(success, error)
}

fun Message.edit(type: MessageType, newTitle: String, newDescription: String, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
    this.editMessage(EmbedFactory.getEmbed(type, newTitle, newDescription)).queue(success, error)
}

fun Message.edit(newMessage: MessageEmbed, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
    this.editMessage(newMessage).queue(success, error)
}


fun Message.edit(newMessage: String, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
    this.editMessage(newMessage).queue(success, error)
}

