@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package me.apomazkin.bitcointestnetapp.features.wallet

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import me.apomazkin.bitcointestnetapp.features.wallet.tea.CloseDialog
import me.apomazkin.bitcointestnetapp.features.wallet.tea.WalletMessage
import me.apomazkin.bitcointestnetapp.features.wallet.tea.WalletState
import me.apomazkin.bitcointestnetapp.features.wallet.widgets.WalletDataStateWidget

@Composable
fun WalletWidget(
        state: WalletState,
        sendMsg: (WalletMessage) -> Unit,
) {
    val context = LocalContext.current
    val showSuccess = remember { mutableStateOf(false) }
    DisposableEffect(state.success.showSuccessDialog) {
        showSuccess.value = state.success.showSuccessDialog
        onDispose {}
    }
    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text(text = "Bitcoin Wallet") },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                )
            },
            content = { paddingValues ->
                Box(
                        modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .padding(horizontal = 24.dp)
                                .background(color = MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.TopCenter,
                ) {
                    WalletDataStateWidget(
                            state = state,
                            sendMsg = sendMsg,
                    )
                }
            },
    )
    if (state.error.showErrorDialog) {
        Toast.makeText(context, state.error.message, Toast.LENGTH_LONG).show()
        sendMsg.invoke(CloseDialog)
    }

    if (showSuccess.value) {
        AlertDialog(
                title = { Text(text = "Your funds have been sent!") },
                text = {
                    Text(
                            modifier = Modifier
                                    .clickable {
                                        val intent = Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse("https://blockstream.info/testnet/tx/${state.success.transactionId}")
                                        )
                                        context.startActivity(intent)
                                    },
                            text = "Your transaction ID is ${state.success.transactionId ?: ""}"
                    )
                },
                onDismissRequest = { sendMsg.invoke(CloseDialog) },
                confirmButton = {
                    TextButton(onClick = { sendMsg.invoke(CloseDialog) }) {
                        Text(text = "Send more")
                    }
                }
        )
    }
}