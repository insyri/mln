package space.insyri

fun read(prompt: String): String {
    println(prompt)
    print("> ")
    return readln()
}

val x = Prompt("123") { x -> x.toInt() }

class Prompt<T>(
    prompt: String,
    convertStringToType: (String) -> T
) {
    private var _prompt = prompt
    private var _convertStringToType = convertStringToType

    private val preConversionChecks = mutableListOf<Pair<(String) -> Boolean, String>>()
    private val postConversionChecks = mutableListOf<Pair<(T) -> Boolean, String>>()

    fun addPreConversionCheck(expression: (String) -> Boolean, invalidResponse: String) {
        preConversionChecks.add(Pair(expression, invalidResponse))
    }

    fun addPostConversionCheck(expression: (T) -> Boolean, invalidResponse: String) {
        postConversionChecks.add(Pair(expression, invalidResponse))
    }

    fun execute(): T {
        var input: String
        while(true) {
            input = read(prompt = _prompt)
            var skipPreStage = false
            for ((check, errString) in preConversionChecks) {
                if (check(input)) {
                    println("Invalid response: $errString")
                    skipPreStage = true
                    break
                }
            }
            if (skipPreStage) continue

            val selection = _convertStringToType(input)

            var skipPostStage = false
            for ((check, errString) in postConversionChecks) {
                if (check(selection)) {
                    println("Invalid response: $errString")
                    skipPostStage = true
                    break
                }
            }
            if (skipPostStage) continue

            return selection
        }
    }
}

