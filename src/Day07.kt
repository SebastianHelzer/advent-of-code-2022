
sealed class FileSystemItem {
    sealed class Command: FileSystemItem() {
        sealed class ChangeDir: Command() {
            object ToRoot: ChangeDir()
            object UpOne: ChangeDir()
            data class DownOne(val dirName: String): ChangeDir()
        }
        object List: Command()
    }
    sealed class Node: FileSystemItem() {
        data class File(val size: Long, val name: String): Node()
        data class Dir(val name: String, val contents: MutableList<Node>): Node()
    }
}

fun main() {

    fun String.toFileSystemItem(): FileSystemItem {
        val (first, second, third) = split(' ') + "" + ""
        return when (first) {
            "$" -> when (second) {
                "ls" -> FileSystemItem.Command.List
                "cd" -> when (third) {
                    "/" -> FileSystemItem.Command.ChangeDir.ToRoot
                    ".." -> FileSystemItem.Command.ChangeDir.UpOne
                    else -> FileSystemItem.Command.ChangeDir.DownOne(third)
                }

                else -> throw Exception()
            }

            "dir" -> FileSystemItem.Node.Dir(second, mutableListOf())
            else -> FileSystemItem.Node.File(first.toLong(), second)
        }
    }

    fun getDirSizesFromInput(input: List<String>): List<FileSystemItem.Node.File> {

        val root = FileSystemItem.Node.Dir("/", mutableListOf())
        val dirStack = ArrayDeque<FileSystemItem.Node.Dir>()
        dirStack.add(root)

        val items = input.map { it.toFileSystemItem() }

        items.forEach { item ->
            val debugPrint = false
            val location = dirStack.joinToString("/") { it.name }
            when (item) {
                is FileSystemItem.Command.ChangeDir.DownOne -> {
                    if (debugPrint) println("$location | going into: ${item.dirName}")
                    dirStack.add(dirStack.last().contents
                        .filterIsInstance<FileSystemItem.Node.Dir>()
                        .first { it.name == item.dirName })
                }

                FileSystemItem.Command.ChangeDir.ToRoot -> {
                    if (debugPrint) println("$location | going up to root")
                    dirStack.clear(); dirStack.add(root)
                }

                FileSystemItem.Command.ChangeDir.UpOne -> {
                    if (debugPrint) println("$location | going up from ${dirStack.removeLast().name}")
                    else dirStack.removeLast()
                    if (dirStack.isEmpty()) {
                        println("trying to go up from root")
                        dirStack.add(root)
                    }
                }

                FileSystemItem.Command.List -> {
                    /*noop*/
                    if (debugPrint) println("$location | list files")
                }

                is FileSystemItem.Node.Dir -> {
                    val currentDir = dirStack.last()
                    if (debugPrint) println("$location | adding a dir ${item.name} to ${currentDir.name}")
                    if (currentDir.contents.filterIsInstance<FileSystemItem.Node.Dir>()
                            .firstOrNull { it.name == item.name } == null
                    ) currentDir.contents.add(item)
                    else {
                        println("trying to add duplicate dir: ${item.name}")
                    }
                }

                is FileSystemItem.Node.File -> {
                    val currentDir = dirStack.last()
                    if (debugPrint) println("$location | adding a file ${item.name} to ${currentDir.name}")
                    if (currentDir.contents.filterIsInstance<FileSystemItem.Node.File>()
                            .firstOrNull { it.name == item.name } == null
                    ) currentDir.contents.add(item)
                    else {
                        println("trying to add duplicate file: ${item.name}")
                    }
                }
            }
        }

        fun FileSystemItem.Node.Dir.getAllFiles(): List<FileSystemItem.Node.File> =
            contents.filterIsInstance<FileSystemItem.Node.Dir>()
                .flatMap { it.getAllFiles() } + contents.filterIsInstance<FileSystemItem.Node.File>()

        fun FileSystemItem.Node.Dir.getDirSizes(): List<FileSystemItem.Node.File> {
            val dirs = contents.filterIsInstance<FileSystemItem.Node.Dir>()
            val inDirectFiles = dirs.flatMap { it.getAllFiles() }
            val directFiles = contents.filterIsInstance<FileSystemItem.Node.File>()

            return dirs.flatMap { it.getDirSizes() } + FileSystemItem.Node.File(
                name = name,
                size = (directFiles + inDirectFiles).sumOf { it.size })
        }

        return root.getDirSizes()
    }

    fun part1(input: List<String>): Long {
        return getDirSizesFromInput(input).filter { it.size <= 100_000 }.sumOf { it.size }
    }

    fun part2(input: List<String>): Long {
        val dirSizes = getDirSizesFromInput(input)
        val totalDiskSpace = 70000000
        val requiredDiskSpace = 30000000
        val usedSpace = dirSizes.maxBy { it.size }.size
        val unusedSpace = totalDiskSpace - usedSpace
        val spaceNeeded = requiredDiskSpace - unusedSpace
        if(spaceNeeded <= 0) return 0L
        return dirSizes.sortedBy { it.size }.first { it.size > spaceNeeded }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    checkEquals(95437L, part1(testInput))
    checkEquals(24933642L, part2(testInput))

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
