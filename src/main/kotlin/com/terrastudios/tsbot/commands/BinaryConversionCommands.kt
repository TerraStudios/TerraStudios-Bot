package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.extensions.binaryToText
import com.terrastudios.tsbot.core.util.extensions.toBinary

class BinaryConversionCommands {

    @DiscordCommand(
        commandName = "texttobinary",
        description = "Converts text into base2 (binary).",
        usage = "texttobinary <text>",
        minArgs = 1
    )
    fun textToBinary(event: CommandEvent) {
        event.reply(MessageType.SUCCESS, "Converted Text", event.args.joinToString(" ").toBinary())
    }

    @DiscordCommand(
        commandName = "binarytotext",
        description = "Converts base2 (binary) into text.",
        usage = "binarytotext <binary>"
    )
    fun binaryToText(event: CommandEvent) {
        try {
            event.reply(
                MessageType.INFO,
                "Converted Text",
                event.args.joinToString(" ").binaryToText()
            )

        } catch (exception: IllegalArgumentException) {
            println("Sending the bad")
            println(exception.message)
            event.reply(
                MessageType.ERROR,
                "Invalid Arguments",
                exception.message!!

            )
        }
    }


}