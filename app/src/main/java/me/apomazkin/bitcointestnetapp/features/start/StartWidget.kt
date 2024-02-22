@file:OptIn(ExperimentalMaterial3Api::class)

package me.apomazkin.bitcointestnetapp.features.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.apomazkin.bitcointestnetapp.features.start.tea.StartState
import me.apomazkin.bitcointestnetapp.features.start.widgets.NoWalletStateWidget

@Composable
fun StartWidget(
        state: StartState,
        onOpenWallet: () -> Unit,
        onRestoreWallet: () -> Unit,
) {

    DisposableEffect(state.isWalletCreated) {
        if (state.isWalletCreated) {
            onOpenWallet()
        }
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
                if (state.isLoading) {
                    Box(
                            modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
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
                } else {
                    Box(
                            modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                                    .padding(horizontal = 24.dp)
                                    .background(color = MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.TopCenter,
                    ) {
                        NoWalletStateWidget(
                                onRestoreWalletClick = onRestoreWallet
                        )
                    }
                }
            },
    )
}