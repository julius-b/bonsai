package cafe.adriel.bonsai.filesystem

import androidx.compose.runtime.Composable
import cafe.adriel.bonsai.core.tree.Tree
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toOkioPath
import java.nio.file.Path as NioPath

@Composable
public fun FileSystemTree(
    rootPath: NioPath,
    selfInclude: Boolean = false
): Tree<Path> =
    FileSystemTree(
        rootPath = rootPath.toOkioPath(),
        fileSystem = FileSystem.SYSTEM,
        selfInclude = selfInclude
    )
