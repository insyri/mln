package space.insyri

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import space.insyri.plugins.*

data class TitledFunction(val title: String, val fn: Function<Unit>)

fun read(prompt: String): String {
    println(prompt)
    print("> ")
    return readln()
}

fun main() {
    val functions = arrayOf(
        TitledFunction("Search and connect to a service", {}), // connectToService
        TitledFunction("Register a service to the network index", {}), // registerService
        TitledFunction("Manage your service(s)", {}), // manageServices
    )

    println("Welcome to My Little Network! Available actions:")
    functions.forEachIndexed { i, fn ->
        println("${i + 1}. ${fn.title}")
    }
    println()

    var input : String?
    var selection : Int
    do {
        input = read("Please select an action by responding with the according digit.")
        if (input.isBlank() || input.toIntOrNull() == null) {
            println("Invalid response: must be a selection")
            continue
        }

        selection = input.toInt()
        if (!(1..functions.size).contains(selection)) {
            println("Invalid response: must be between 1 and ${functions.size}")
            continue
        }

        break
    } while (true)

    println(functions[selection - 1].fn)
}

//fun Application.module() {
//    configureRouting()
//}
