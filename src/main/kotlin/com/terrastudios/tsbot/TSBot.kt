package com.terrastudios.tsbot

import com.terrastudios.tsbot.core.commands.CommandHandler
import com.terrastudios.tsbot.core.data.BotConfig
import com.terrastudios.tsbot.core.util.ResourceUtils
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder

fun main() {
    TSBot()
}

class TSBot() {

    companion object {
        lateinit var config : BotConfig
        lateinit var commandHandler: CommandHandler
        lateinit var api : JDA
    }

    init {
        config = ResourceUtils.getResource("/config.json")
        api = JDABuilder.createDefault(config.token).build()
        commandHandler = CommandHandler()

        registerEvents()
    }

    private fun registerEvents() {
        api.addEventListener(commandHandler)
    }
}

