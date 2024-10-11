package `in`.porter.cfms.api.service.utils
import kotlin.random.Random

class CommonUtils {
    companion object {
        // Function to generate a random alphanumeric string of specified length
        fun generateRandomAlphaNumeric(length: Int): String {
            val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
            return (1..length)
                .map { chars.random() }
                .joinToString("")
        }
    }
}