@file:OptIn(ExperimentalMaterial3Api::class)

package me.apomazkin.bitcointestnetapp.features.wallet.widgets

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.apomazkin.bitcointestnetapp.features.wallet.tea.SendAmount
import me.apomazkin.bitcointestnetapp.features.wallet.tea.TypeAddressMsg
import me.apomazkin.bitcointestnetapp.features.wallet.tea.TypeAmountMsg
import me.apomazkin.bitcointestnetapp.features.wallet.tea.WalletMessage
import me.apomazkin.bitcointestnetapp.features.wallet.tea.WalletState

@Composable
fun WalletDataStateWidget(
        state: WalletState,
        sendMsg: (WalletMessage) -> Unit,
) {
    Column(
            modifier = Modifier
                    .fillMaxSize(),
            horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        AddressWidget(
                address = state.sourceAddress,
                balance = state.balance,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
                text = "Send",
                style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
                modifier = Modifier
                        .fillMaxWidth(),
                label = { Text(text = "Address to send") },
                value = state.destinationAddress,
                onValueChange = { sendMsg(TypeAddressMsg(value = it)) },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.End),
                keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OutlinedTextField(
                    modifier = Modifier
                            .fillMaxWidth(0.5F),
                    label = { Text(text = "Amount to send") },
                    value = state.amountToSend,
                    onValueChange = { sendMsg(TypeAmountMsg(value = it)) },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium
                            .copy(textAlign = TextAlign.End),
                    keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                    ),
            )
            Button(
                    modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth(),
                    onClick = { sendMsg(SendAmount) }
            ) {
                Text(text = "Send")
            }
        }
    }

}