import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> = File("src", "$name.txt")
    .readLines()

@Suppress("unused")
fun readWholeInput(name: String): String = File("src", "$name.txt")
    .readText()

/**
 * Converts string to md5 hash.
 */
@Suppress("unused")
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun checkEquals(expected: Any, actual: Any, lazyMessage: (Any, Any) -> Any = { e, a -> "expected=$e actual=$a" }) {
    check(expected == actual) { lazyMessage(expected, actual) }
}