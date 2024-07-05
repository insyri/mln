package space.insyri

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.lang.System
/**
 * @param status `"on"` or `"off"`
 */
@Serializable
data class ServiceDetails(var port: Int, var status: String)
typealias Service = Map<String, ServiceDetails>


class Config {
    companion object { // Java equivalent of `static`

        private val mlnDir = System.getProperty("user.home") + "\\mln\\"
        val scriptsDirectory = mlnDir + "scripts"
        private val clientSaveFile = mlnDir + "save.json"
        private val clientInternalFile = mlnDir + "internal.json"
        //debug
        private val dummyServiceHostFile = mlnDir + "dummyServiceHost.json"

        fun createMlnDirectory() {
            File(scriptsDirectory).mkdirs()
            File(clientSaveFile).createNewFile()
            File(clientInternalFile).createNewFile()

            File(clientSaveFile).writeText("{}")
            File(clientInternalFile).writeText("{}")
        }

        fun writeInternal(
            services: Service
        ) {
            val json = Json.encodeToString(services)
            File(clientInternalFile).writeText(json)
        }

        fun readInternal(): Service {
            val raw = File(clientInternalFile).readText()
            return Json.decodeFromString(raw)
        }

        fun readSave(): String {
            return File(clientSaveFile).readText()
        }

//        fun writeSave(input: String) {
//            File(scriptsDirectory).read
//        }
    }
}
