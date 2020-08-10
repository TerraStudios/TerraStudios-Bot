package com.terrastudios.tsbot.events

import com.terrastudios.tsbot.TSBot
import com.terrastudios.tsbot.core.events.MessageEvent
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.RequestType
import com.terrastudios.tsbot.core.util.WebRequestSender
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.lang.StringBuilder
import java.util.stream.Collectors

class CodeListener : ListenerAdapter() {

    val textFileTypes = listOf("txt", "log", "properties", "text")

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return

        val messageEvent = MessageEvent(TSBot.listener, event)

        if (event.message.attachments.isNotEmpty()) {
            for (attachment in event.message.attachments) {
                if (textFileTypes.contains(attachment.fileExtension)) {
                    attachment.retrieveInputStream().thenAccept {
                        val builder = StringBuilder()
                        val buf = ByteArray(1024)
                        var count = it.read(buf)
                        while (count > 0)
                        {
                            builder.append(String(buf, 0, count));
                            count = it.read(buf)
                        }
                        it.close();

                        val sender = WebRequestSender("https://paste.feed-the-beast.com/api/create", RequestType.POST)

                        sender.addParam("text", builder.toString())
                        sender.addParam("title", attachment.fileName)
                        sender.addParam("name", event.author.name)
                        sender.addParam("expire", (60 * 24).toString())

                        sender.send {
                            messageEvent.reply(
                                MessageType.INFO,
                                "Text File Detected",
                                "Paste of text file: ${it.response}."
                            )
                        }



                        println(builder);
                    }.exceptionally { t ->// handle failure
                        t.printStackTrace()
                        return@exceptionally null
                    }
                }
            }
        } else {

            val regex = "```(.*)```".toRegex()

            val result = regex.find(messageEvent.text.replace("\n", " ")) ?: return


            val textRaw = result.groupValues[0]

            val split = textRaw.split(" ")

            var language = split[0].replace("```", "")

            val text = split.stream().skip(1).collect(Collectors.joining(" ")).replace("```", "")

            if (text.length > 300) {
                val sender = WebRequestSender("https://paste.feed-the-beast.com/api/create", RequestType.POST)

                if (language == "cs") language = "csharp"

                sender.addParam("text", text)
                sender.addParam("lang", language)
                sender.addParam("name", event.author.name)
                sender.addParam("expire", (60 * 24).toString())

                sender.send {
                    messageEvent.reply(
                        MessageType.WARNING,
                        "Long Block of Code Detected!",
                        "Because your code block was so long, I've gone ahead and [uploaded it for you](${it.response})."
                    )
                }


            }


            //split(" ").stream().skip(1).toArray { size -> arrayOfNulls<String>(size) }
            //messageEvent.reply(MessageType.INFO, "Regex Parse", "Found $result")

            //
        }
    }


}