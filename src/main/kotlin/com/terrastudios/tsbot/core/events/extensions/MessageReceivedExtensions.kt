package com.terrastudios.tsbot.core.events.extensions

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

fun Message.edit(newMessage: String, success: Consumer<in Message> = Consumer { }, error: Consumer<in Throwable> = Consumer { }) {
    this.editMessage(newMessage).queue(success, error)
}

