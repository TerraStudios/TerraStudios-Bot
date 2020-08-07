package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.data.CustomCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.util.ResourceUtils
import net.dv8tion.jda.api.Permission
import org.json.simple.JSONObject
import java.io.FileWriter
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap


class MakeCommandCommand {

    private val data = HashMap<String, CustomCommand>()

    init {

        val parsedObject = ResourceUtils.getResource<CustomCommand>("/customcommands.json")


        //start autosave timer
        val executor = Executors.newScheduledThreadPool(1)
        executor.scheduleAtFixedRate({
            val jsonObject = JSONObject()
            for (entry in data) {
                jsonObject[entry.key] = entry.value.displayName
            }

            val writer = FileWriter(ResourceUtils.getFilePath("/timezones.json"))
            writer.write(jsonObject.toJSONString())
            writer.close()

        }, 10, 10, TimeUnit.SECONDS) //autosave every 30 minutes

    }

    @DiscordCommand(
        commandName = "makecommand",
        description = "Makes a command that will embed a message on execution.",
        usage = "makecommand <command-name>",
        aliases = ["mckcommand", "makecmd"],
        permission = Permission.ADMINISTRATOR,
        minArgs = 1,
        maxArgs = 1
    )
    fun makeCommand(event: CommandEvent) {

    }
}