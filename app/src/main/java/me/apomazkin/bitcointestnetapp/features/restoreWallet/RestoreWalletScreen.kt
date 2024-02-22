package me.apomazkin.bitcointestnetapp.features.restoreWallet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RestoreWalletScreen(
        viewModel: RestoreWalletViewModel = viewModel(
                factory = RestoreWalletViewModel.Factory()
        ),
        onBackPress: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    RestoreWalletWidget(
            state = state,
            sendMsg = viewModel::accept,
            onBackPress = onBackPress,
    )
}