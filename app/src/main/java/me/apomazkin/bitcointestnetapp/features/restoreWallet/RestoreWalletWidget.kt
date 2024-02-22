@file:OptIn(ExperimentalMaterial3Api::class)

package me.apomazkin.bitcointestnetapp.features.restoreWallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.apomazkin.bitcointestnetapp.features.restoreWallet.tea.ResetMnemonicMsg
import me.apomazkin.bitcointestnetapp.features.restoreWallet.tea.RestoreWalletMessage
import me.apomazkin.bitcointestnetapp.features.restoreWallet.tea.RestoreWalletMsg
import me.apomazkin.bitcointestnetapp.features.restoreWallet.tea.RestoreWalletState
import me.apomazkin.bitcointestnetapp.features.restoreWallet.tea.UpdateMnemonicMsg

@Composable
fun RestoreWalletWidget(
        state: RestoreWalletState,
        sendMsg: (RestoreWalletMessage) -> Unit,
        onBackPress: () -> Unit,
) {
    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text(text = "Restore Wallet") },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        navigationIcon = {
                            IconButton(onClick = { onBackPress.invoke() }) {
                                Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        },
                )
            },
            content = {
                Surface(
                        modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                ) {
                    Box {
                        Column(
                                modifier = Modifier
                                        .fillMaxSize()
                                        .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Spacer(modifier = Modifier.height(24.dp))
                            OutlinedTextField(
                                    modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),
                                    value = state.mnemonic.joinToString(separator = " "),
                                    onValueChange = { value -> sendMsg(UpdateMnemonicMsg(value)) },
                                    label = { Text("Enter 12-word mnemonic phrase") },
                                    maxLines = 6,
                                    supportingText = { Text("Words count ${state.wordsCount}") },

                                    )
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                    modifier = Modifier
                                            .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                TextButton(
                                        enabled = state.isLoading.not(),
                                        onClick = { sendMsg(ResetMnemonicMsg) },
                                        content = { Text("Reset mnemonic") },
                                )
                                Button(
                                        enabled = state.isRestoreButtonEnable && state.isLoading.not(),
                                        onClick = { sendMsg(RestoreWalletMsg) },
                                        content = { Text("Restore Wallet") },
                                )
                            }
                        }
                    }
                    if (state.isLoading) {
                        Box(
                                modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 24.dp)
                                        .background(color = MaterialTheme.colorScheme.background),
                                contentAlignment = Alignment.Center,
                        ) {
                            Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                                Text(text = "Loading wallet data...", modifier = Modifier.padding(top = 16.dp))
                                Text(text = state.loadingProgress, modifier = Modifier.padding(top = 16.dp))
                            }
                        }
                    }
                    if (state.screenResult != null) {
                        onBackPress.invoke()
                    }
                }
            },
    )
}