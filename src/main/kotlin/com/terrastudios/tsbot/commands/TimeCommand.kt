package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.ResourceUtils
import com.terrastudios.tsbot.core.util.extensions.EmbedFactory
import org.json.simple.JSONObject
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class TimeCommand {

    private val data = HashMap<String, TimeZone>()

    init {

        val parsedObject = ResourceUtils.getJSONObject("/timezones.json")

        for (element in parsedObject) {
            data[element.key as String] = TimeZone.getTimeZone(element.value as String)
        }

        TimeZone.setDefault(null)

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

    @DiscordCommand(commandName = "addtime", usage = "addtime <name> <timezone>", minArgs = 2, maxArgs = 2)
    fun addTime(command: CommandEvent) {
        val timezone = TimeZone.getTimeZone(command.args[1])

        if (timezone == null) {
            command.reply(
                EmbedFactory.getEmbed(
                    MessageType.ERROR,
                    "Invalid TimeZone",
                    "Please specify a correct timezone."
                )
            )
        } else {
            println("setting ${command.args[0]}")
            data[command.args[0]] = timezone
            command.reply(
                EmbedFactory.getEmbed(
                    MessageType.SUCCESS,
                    "Success",
                    "Added timezone ${timezone.displayName} to ${command.args[0]}."
                )
            )
        }


    }

    @DiscordCommand(commandName = "time", usage = "time <name>", minArgs = 1, maxArgs = 1)
    fun time(command: CommandEvent) {
        if (data.containsKey(command.args[0])) {
            val cal = Calendar.getInstance(data[command.args[0]])
            val format = SimpleDateFormat("HH:mm")
            command.reply(
                EmbedFactory.getEmbed(
                    MessageType.INFO, "Time for ${command.args[0]}",
                    format.format(cal.time).toString()
                )
            )
        } else {
            command.reply(EmbedFactory.getEmbed(MessageType.ERROR, "Invalid User", "That user doesn't exist!"))
        }
    }

}