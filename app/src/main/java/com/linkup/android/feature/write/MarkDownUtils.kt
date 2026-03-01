package com.linkup.android.feature.write

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatStrikethrough
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun EditorToolbar(
    content: TextFieldValue,
    onContentChange: (TextFieldValue) -> Unit,
    uploadImageToS3: (Uri) -> Unit,
    uploadedUrl: String?,
) {
    var showLinkDialog by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { uploadImageToS3(it) }
        }
    )

    LaunchedEffect(uploadedUrl) {
        uploadedUrl?.let {
            insertMarkdown(content, onContentChange, "![이미지]($it)")
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ToolbarIcon(Icons.Default.FormatBold) { insertMarkdown(content, onContentChange, "**") }
        ToolbarIcon(Icons.Default.FormatItalic) { insertMarkdown(content, onContentChange, "*") }
        ToolbarIcon(Icons.Default.FormatStrikethrough) { insertMarkdown(content, onContentChange, "~~") }
        ToolbarIcon(Icons.Default.Link) { showLinkDialog = true }
        ToolbarIcon(Icons.Default.Image) { imagePickerLauncher.launch("image/*") }
    }

    if (showLinkDialog) {
        LinkDialog(
            onDismiss = { showLinkDialog = false },
            onConfirm = { displayText, url ->
                insertMarkdown(content, onContentChange, "[$displayText]($url)")
            }
        )
    }
}

@Composable
fun ToolbarIcon(icon: ImageVector, onClick: () -> Unit) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = Color(0xFFADADAD),
        modifier = Modifier
            .size(24.dp)
            .clickable { onClick() }
    )
}

fun insertMarkdown(
    content: TextFieldValue,
    onContentChange: (TextFieldValue) -> Unit,
    markdown: String
) {
    val selection = content.selection
    val newText = if (selection.min != selection.max) {
        buildString {
            append(content.text.substring(0, selection.min))
            append(markdown)
            append(content.text.substring(selection.min, selection.max))
            append(markdown.takeIf { it.length <= 2 } ?: "")
            append(content.text.substring(selection.max))
        }
    } else {
        buildString {
            append(content.text.substring(0, selection.min))
            append(markdown)
            append(content.text.substring(selection.min))
        }
    }
    onContentChange(content.copy(text = newText))
}

@Composable
fun LinkDialog(
    onDismiss: () -> Unit,
    onConfirm: (displayText: String, url: String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("링크 삽입") },
        text = {
            Column {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("표시 텍스트") }
                )
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("URL") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(text, url)
                onDismiss()
            }) { Text("삽입") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("취소") }
        }
    )
}