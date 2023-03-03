package com.example.alarmgroups.presentation.groups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.example.alarmgroups.presentation.common.TextFieldItem
import com.example.alarmgroups.ui.theme.black2

@Composable
fun NewGroupAlertDialog(
    textFieldValue: String,
    focusRequester: FocusRequester,
    onDismissRequest: () -> Unit,
    onTextFieldValueChange: (value: String) -> Unit,
    onTextFieldValueDeleteClick: () -> Unit,
    onOkButtonClick: () -> Unit
) {

    AlertDialog(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = black2,
        onDismissRequest = onDismissRequest,
        title = {
            TextFieldItem(
                modifier = Modifier.focusRequester(focusRequester),
                value = textFieldValue,
                onValueChange = onTextFieldValueChange,
                label = "Group",
                onDeleteClick = onTextFieldValueDeleteClick,
                singleLine = true
            )
        },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
                TextButton(onClick = onOkButtonClick) {
                    Text(text = "OK")
                }
            }
        }
    )

}