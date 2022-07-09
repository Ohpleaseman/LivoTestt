package tj.emin.livotestt.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun TextField(
    field: MutableState<String>,
    label: String,
    imeAction: ImeAction,
    imeActionLambda: () -> Unit
) {
    TextFieldValue()
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = field.value,
        onValueChange = { field.value = it },
        shape = RectangleShape,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (imeAction == ImeAction.Done) {
                    imeActionLambda()
                }
            },
            onNext = {
                if (imeAction == ImeAction.Next) {
                    imeActionLambda()
                }
            }),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            textColor = Color.DarkGray
        ),
        label = {
            Text(
                text = label,
                color = Color.Black
            )
        },
    )
}
