package com.terrastudios.tsbot.core.util.extensions

import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.events.MessageEvent
import net.dv8tion.jda.api.entities.Message


fun Message.awaitResponse(event : CommandEvent, response: (MessageEvent) -> Unit) {
    event.listener.listen({ e -> e.author == event.author && e.message.channel == event.message.channel && e.message != event.message}, response)
}