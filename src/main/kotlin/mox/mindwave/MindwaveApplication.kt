package mox.mindwave

import org.json.JSONObject
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.jackson.JsonObjectDeserializer
import org.springframework.boot.runApplication
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

@SpringBootApplication
class MindwaveApplication : CommandLineRunner {
    override fun run(vararg args: String?) {
        val socket = Socket("127.0.0.1", 13854)
        println("Socket opened")
        val inputStream = socket.getInputStream()
        val outputStream = socket.getOutputStream()
        val reader = BufferedReader(InputStreamReader(inputStream))
        val writer = PrintWriter(outputStream, true)

        // Authorization
        val auth = JSONObject()
        auth.put("appName", "Mindwave Test")
        auth.put("appKey", "9f54141b4b4c567c558d3a76cb8d715cbde03296")
        writer.println(auth.toString())
        println("Authorization sent")
        val authResponse = reader.readLine()
        println("First response $authResponse")

        // Configure
        val config = JSONObject()
        config.put("enableRawOutput", false)
        config.put("format", "Json")
        writer.println(config.toString())

        while (true) {
            println("Message: ${reader.readLine()}")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<MindwaveApplication>(*args)
}
