package ir.hirkancorp.presenter.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.core.theme.RuzmozdProviderTheme


@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    placeholder: String,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    currentValue: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        singleLine = true,
        isError = isError,
        enabled = enabled,
        leadingIcon = leadingIcon,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
            unfocusedBorderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
            placeholderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.3f)
        ),
        shape = MaterialTheme.shapes.medium,
        placeholder = { Text(text = placeholder) },
        value = currentValue,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
    )
}

@Preview(showBackground = true, backgroundColor = 0xff000000)
@Composable
fun TextInputDark() {
    RuzmozdProviderTheme(
        darkTheme = true
    ) {
        OutlinedTextField(modifier = Modifier
            .padding(40.dp),
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Info, contentDescription = "") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
                unfocusedBorderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
                placeholderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.3f)
            ),
            placeholder = {
                Text(text = "متن راهنما")
            },
            shape = MaterialTheme.shapes.medium,
            value = "",
            onValueChange = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun TextInputLight() {
    RuzmozdProviderTheme(
        darkTheme = false
    ) {
        OutlinedTextField(modifier = Modifier
            .padding(40.dp),
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Info, contentDescription = "") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
                unfocusedBorderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
                placeholderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.3f)
            ),
            placeholder = {
                Text(text = "متن راهنما")
            },
            shape = MaterialTheme.shapes.medium,
            value = "",
            onValueChange = {})
    }
}