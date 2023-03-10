package com.example.alarmgroups.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.alarmgroups.ui.theme.black1
import com.example.alarmgroups.ui.theme.black2
import com.example.alarmgroups.ui.theme.grayLight

@Composable
fun TextFieldItem(
    value: String,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onDeleteClick: () -> Unit,
    singleLine: Boolean = false,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = black2,
            cursorColor = grayLight,
            disabledLabelColor = black1,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(8.dp),
        label = { Text(text = label) },
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = {
                    onDeleteClick()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null
                    )
                }
            }
        },
        singleLine = true,
    )

}