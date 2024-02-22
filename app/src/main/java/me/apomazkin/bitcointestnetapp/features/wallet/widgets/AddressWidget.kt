package me.apomazkin.bitcointestnetapp.features.wallet.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddressWidget(
        address: String,
        balance: String,
) {
    Text(
            text = "Source wallet address",
            style = MaterialTheme.typography.titleSmall
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
            modifier = Modifier
                    .fillMaxWidth()
                    .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(4.dp)
                    )
                    .padding(12.dp),
            text = address,
            style = MaterialTheme.typography.bodyMedium,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Row(
            modifier = Modifier
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.End,
            ),
    ) {
        Text(
                text = "Balance:",
                style = MaterialTheme.typography.titleMedium
        )
        Text(text = balance, style = MaterialTheme.typography.bodyLarge)
    }
}