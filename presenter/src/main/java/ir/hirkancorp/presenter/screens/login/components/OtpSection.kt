package ir.hirkancorp.presenter.screens.login.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ir.hirkancorp.presenter.R
import ir.hirkancorp.presenter.core.LocalSpacing

@Composable
fun OtpSection(
    placeholder: String,
    textFieldMaxChar: Int,
    isLoading: Boolean = false,
    onSubmitClick: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf("") }
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .height(55.dp),
            textStyle = MaterialTheme.typography.h5,
            shape = MaterialTheme.shapes.large,
            value = textFieldValue,
            enabled = !isLoading,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = {
                Text(text = placeholder, style = MaterialTheme.typography.h5)
            },
            singleLine = true,
            onValueChange = {
                if (it.length <= textFieldMaxChar) textFieldValue = it
            }
        )
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            elevation = ButtonDefaults.elevation(0.dp),
            shape = MaterialTheme.shapes.large,
            enabled = textFieldValue.length == textFieldMaxChar && !isLoading,
            onClick = { onSubmitClick(textFieldValue) }
        ) {
            if (isLoading) CircularProgressIndicator()
            else Text(
                text = stringResource(R.string.auth_submit),
                style = MaterialTheme.typography.button
            )
        }
    }
}