package com.terrastudios.tsbot.core.util

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

enum class RequestType { POST, GET }

class WebRequestSender(private val url: String, private val requestType: RequestType) {

    data class WebResponse(val responseCode: Int, val response: String)

    private var paramString = ""


    fun addParam(key: String, value: String) {
        paramString += (if (paramString != "") "&" else "") + URLEncoder.encode(
            key,
            "UTF-8"
        ) + "=" + URLEncoder.encode(value, "UTF-8")
    }

    fun send(response: (WebResponse) -> Unit) {

        with(URL(url).openConnection() as HttpURLConnection) {
            setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11"
            )
            requestMethod = requestType.name
            doOutput = true

            val wr = OutputStreamWriter(outputStream)
            wr.write(paramString)
            wr.flush()

            println("URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                println("Response : $response")
                response(
                    WebResponse(responseCode, response.toString())
                )
            }

        }


    }


}