package me.apomazkin.bitcointestnetapp.features.wallet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WalletScreen(
        viewModel: WalletViewModel = viewModel(
                factory = WalletViewModel.Factory()
        ),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    WalletWidget(
            state = state,
            sendMsg = viewModel::accept,
    )
}