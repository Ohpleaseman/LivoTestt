package tj.emin.livotestt.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpacerDp(dp: Dp) = Spacer(modifier = Modifier.size(dp))

@Composable
fun SpacerBetweenObjects() = SpacerDp(dp = 16.dp)

@Composable
fun MediumSpacer() = SpacerDp(dp = 32.dp)

@Composable
fun HugeSpacer() = SpacerDp(dp = 64.dp)