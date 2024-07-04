package space.insyri

data class TitledFunction(val title: String, val fn: Function<Unit>)

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

    val selection = promptInt("Please select an action by responding with the according digit.", 1, functions.size)

    println(functions[selection - 1].fn)
}

//fun Application.module() {
//    configureRouting()
//}
