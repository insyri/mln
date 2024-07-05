package space.insyri

fun read(prompt: String): String {
    println(prompt)
    print("> ")
    return readln()
}

fun promptStringInList(prompt: String, set: Set<String>): String {
    return Prompt("$prompt R") { x -> x.lowercase() }.apply {
        addPostConversionCheck({ x ->
            set.any {
                it.equals(x, ignoreCase = true)
            }
        }, "must be of valid options: ${set.joinToString(separator = ", ")}")
    }.execute()
}

fun promptInt(prompt: String, min: Int, max: Int): Int {
    return Prompt("$prompt ($min to $max inclusively)") { x ->
        x.toInt()
    }.apply {
        addPreConversionCheck({ x -> !(x.isBlank() || x.toIntOrNull() == null) }, "must be a number")
        addPostConversionCheck(
            { x -> x in min..max },
            "must be between $min and $max inclusively"
        )
    }.execute()
}

fun promptYN(prompt: String): Boolean {
    return Prompt("$prompt (Y/N)") { x ->
        when (x) {
            "Y", "y", "Yes", "yes", "YES" -> true
            "N", "n", "No", "no", "NO" -> false
            else -> false
        }
    }.apply {
        addPreConversionCheck({ x ->
            when (x) {
                "Y", "y", "Yes", "yes", "YES", "N", "n", "No", "no", "NO" -> true
                else -> false
            }
        }, "valid options: yes, no, y, n")
    }.execute()
}

class Prompt<T>(prompt: String, convertStringToType: (String) -> T) {
    private var _prompt = prompt
    private var _convertStringToType = convertStringToType

    private val preConversionChecks = mutableListOf<Pair<(String) -> Boolean, String>>()
    private val postConversionChecks = mutableListOf<Pair<(T) -> Boolean, String>>()

    fun addPreConversionCheck(shouldBe: (String) -> Boolean, invalidResponse: String) {
        preConversionChecks.add(Pair(shouldBe, invalidResponse))
    }

    fun addPostConversionCheck(shouldBe: (T) -> Boolean, invalidResponse: String) {
        postConversionChecks.add(Pair(shouldBe, invalidResponse))
    }

    fun execute(): T {
        var input: String
        while (true) {
            input = read(prompt = _prompt)
            var skipPreStage = false
            for ((check, errString) in preConversionChecks) {
                if (!check(input)) {
                    println("Invalid response: $errString")
                    skipPreStage = true
                    break
                }
            }
            if (skipPreStage) continue

            val selection = _convertStringToType(input)

            var skipPostStage = false
            for ((check, errString) in postConversionChecks) {
                if (!check(selection)) {
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

