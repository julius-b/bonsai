package cafe.adriel.bonsai.sample.tree

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import cafe.adriel.bonsai.R
import cafe.adriel.bonsai.core.Bonsai
import cafe.adriel.bonsai.core.node.BranchNode
import cafe.adriel.bonsai.core.tree.Tree
import cafe.adriel.bonsai.filesystem.FileSystemBonsaiStyle
import cafe.adriel.bonsai.filesystem.FileSystemTree
import okio.Path

object FileSystemTreeScreen : TreeScreen<Path> {

    override val title = "File System Tree"

    @Composable
    override fun composeTree(): Tree<Path> {
        val context = LocalContext.current
        val rootDirectory = remember {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) context.dataDir
            else context.codeCacheDir
        }

        return FileSystemTree(rootDirectory, selfInclude = true)
    }

    @Composable
    override fun BonsaiContent(
        tree: Tree<Path>,
        modifier: Modifier
    ) {
        Bonsai(
            tree = tree,
            style = FileSystemBonsaiStyle().copy(
                nodeCollapsedIcon = { node ->
                    getIcon(
                        path = node.content,
                        default = if (node is BranchNode) R.drawable.folder_24px
                        else R.drawable.draft_24px
                    )
                },
                nodeExpandedIcon = { node ->
                    getIcon(path = node.content, default = R.drawable.folder_open_24px)
                },
                nodeCollapsedIconColorFilter = ColorFilter.tint(Color.Black),
                nodeExpandedIconColorFilter = ColorFilter.tint(Color.Black)
            ),
            modifier = modifier,
            onDoubleClick = null,
            onLongClick = null
        )
    }

    @Composable
    private fun getIcon(path: Path, @DrawableRes default: Int) =
        painterResource(
            when (path.toFile().extension) {
                "apk" -> R.drawable.android_24px
                "jar" -> R.drawable.local_cafe_24px
                "studio" -> R.drawable.adb_24px
                "so" -> R.drawable.memory_24px
                "xml" -> R.drawable.description_24px
                "png", "webp", "jpg" -> R.drawable.image_24px
                "mp4", "webm", "gif" -> R.drawable.videocam_24px
                "wav", "mp3", "ogg" -> R.drawable.mic_24px
                else -> default
            }
        )
}
