package space.insyri

import kotlin.system.exitProcess

data class TitledFunction(val title: String, val fn: () -> Unit)

fun main() {
    Config.createMlnDirectory()
    val functions = arrayOf(
        TitledFunction("Exit") { println("Goodbye!"); exitProcess(0) },
        TitledFunction("Search and connect to a service") {}, // connectToService
        TitledFunction("Register a service to the network index", ::registerService), // registerService
        TitledFunction("Manage your service(s)", ::manageServices), // ::x denotes a function reference
    )

    println("Welcome to My Little Network! Available actions:")
    while (true) {
        functions.forEachIndexed { i, fn ->
            println("${i}. ${fn.title}")
        }
        println()

        val selection = promptInt("Please select an action by responding with the according digit.", 0, functions.size)
        functions[selection].fn()
    }

}

//fun Application.module() {
//    configureRouting()
//}
