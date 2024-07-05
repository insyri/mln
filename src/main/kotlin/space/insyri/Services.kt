package space.insyri

fun isOn(x: String) = x.equals("on", ignoreCase = true)
fun isOff(x: String) = !isOn(x)

fun manageServices() {
    val services = Config.readInternal().toMutableMap()

    println("You have ${services.size} service${if (services.size > 1) "s" else ""}")
    if (services.isEmpty()) {
        if(promptYN("You cannot manage services you don't have." + " " +
                    "Would you like to register a service instead?")) {
            // register service
        } else {
            println("Aborting.")
        }
        return
    }

    for ((key, value) in services) {
        println("$key: $value")
    }

    val selectedServiceName = promptStringInList("Which service would you like to manage?", services.keys)
    var serviceName = selectedServiceName // modifiable by user
    val service = services[selectedServiceName] ?: throw Error("Service is Null")

    while (true) {
        println("Name:  $selectedServiceName")
        println("Port:  ${service.port}")
        println("Status: ${service.status}")

        when (promptStringInList("Select a property you wish to modify. When done, enter `done`.",
            setOf("name", "port,", "status", "done")
        )) {
            "done" -> break
            "name" -> serviceName = Prompt("Enter a name") { x -> x }.apply {
                addPostConversionCheck({ x -> x.length <= 25 }, "length cannot exceed 25")
            }.execute()
            "port" -> service.port = promptInt("Enter a port number", 0,65535 /* 2^16 - 1 */)
            "status" -> {
                service.status = promptStringInList("Enter a status", setOf("on", "off"))
                // contact index to set to service.status
            }
        }

        println()
    }

    services.remove(selectedServiceName)
    services[serviceName] = service
}
