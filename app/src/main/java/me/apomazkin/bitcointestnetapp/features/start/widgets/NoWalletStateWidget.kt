package me.apomazkin.bitcointestnetapp.features.start.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.NoWalletStateWidget(
        onRestoreWalletClick: () -> Unit,
) {
    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
    ) {
        Text(
                modifier = Modifier
                        .fillMaxWidth(),
                text = "There is no wallet yet.\nPlease, restore it.",
                style = MaterialTheme.typography.bodyMedium
                        .copy(color = Color.DarkGray),
                textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
                modifier = Modifier
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                    modifier = Modifier,
                    onClick = onRestoreWalletClick
            ) {
                Text(text = "Restore")
            }
        }
    }
}