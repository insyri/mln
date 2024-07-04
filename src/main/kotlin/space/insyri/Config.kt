package space.insyri

import kotlinx.serialization.encodeToString
import java.nio.file.Files
import java.nio.file.Paths
import kotlinx.serialization.json.Json

const val programDirectoryName = "mln"

class Config {
    companion object { // Java equivalent of `static`
        private val scriptsDirectory = Paths.get("~/$programDirectoryName/scripts")
        private val clientSaveFile = Paths.get("~/$programDirectoryName/save.json")
        private val clientInternalFile = Paths.get("~/$programDirectoryName/internal.json")
        private val dummyServiceHostFile = Paths.get("~/$programDirectoryName/dummyServiceHost.json")

        fun createMlnDirectory() {
            Files.createDirectories(scriptsDirectory)
            Files.createFile(clientSaveFile)
            Files.createFile(clientInternalFile)
        }

        fun writeInternal(
            services: Map<String, Int>
        ) {
            val json = Json.encodeToString(services)
            Files.write(scriptsDirectory, json.toByteArray())
        }

        fun readInternal(): Map<String, Int> {
            val raw = Files.readString(clientInternalFile)
            return Json.decodeFromString(raw)
        }

        fun readSave(): String {
            return Files.readString(clientSaveFile)
        }

        fun writeSave(input: String) { Files.write(scriptsDirectory, input.toByteArray()) }
    }
}
