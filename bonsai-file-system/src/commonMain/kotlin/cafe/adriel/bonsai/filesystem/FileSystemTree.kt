package cafe.adriel.bonsai.filesystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import bonsai.bonsai_file_system.generated.resources.Res
import bonsai.bonsai_file_system.generated.resources.draft_24px
import bonsai.bonsai_file_system.generated.resources.folder_24px
import bonsai.bonsai_file_system.generated.resources.folder_open_24px
import cafe.adriel.bonsai.core.BonsaiStyle
import cafe.adriel.bonsai.core.node.Branch
import cafe.adriel.bonsai.core.node.BranchNode
import cafe.adriel.bonsai.core.node.Leaf
import cafe.adriel.bonsai.core.tree.Tree
import cafe.adriel.bonsai.core.tree.TreeScope
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toOkioPath
import org.jetbrains.compose.resources.painterResource
import java.io.File

@Composable
fun FileSystemTree(
    rootPath: File,
    selfInclude: Boolean = false
): Tree<Path> =
    FileSystemTree(
        rootPath = rootPath.toOkioPath(),
        fileSystem = FileSystem.SYSTEM,
        selfInclude = selfInclude
    )

fun FileSystemBonsaiStyle(): BonsaiStyle<Path> =
    BonsaiStyle(
        nodeNameStartPadding = 4.dp,
        nodeCollapsedIcon = { node ->
            painterResource(
                if (node is BranchNode) Res.drawable.folder_24px
                else Res.drawable.draft_24px
            )
        },
        nodeExpandedIcon = {
            painterResource(Res.drawable.folder_open_24px)
        }
    )

@Composable
public fun FileSystemTree(
    rootPath: Path,
    fileSystem: FileSystem,
    selfInclude: Boolean = false
): Tree<Path> =
    Tree {
        FileSystemTree(
            rootPath = rootPath,
            fileSystem = fileSystem,
            selfInclude = selfInclude
        )
    }

@Composable
private fun TreeScope.FileSystemTree(
    rootPath: Path,
    fileSystem: FileSystem,
    selfInclude: Boolean = false
) {
    if (selfInclude) {
        FileSystemNode(rootPath, fileSystem)
    } else {
        fileSystem
            .listOrNull(rootPath)
            ?.forEach { path -> FileSystemNode(path, fileSystem) }
    }
}

@Composable
private fun TreeScope.FileSystemNode(
    path: Path,
    fileSystem: FileSystem
) {
    if (fileSystem.metadata(path).isDirectory) {
        Branch(
            content = path,
            name = path.name
        ) {
            FileSystemTree(path, fileSystem)
        }
    } else {
        Leaf(
            content = path,
            name = path.name
        )
    }
}
