
package me.apomazkin.bitcointestnetapp.features.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StartScreen(
        viewModel: StartViewModel = viewModel(
                factory = StartViewModel.Factory()
        ),
        onOpenWallet: () -> Unit,
        onRestoreWallet: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    StartWidget(
            state = state,
            onOpenWallet = onOpenWallet,
            onRestoreWallet = onRestoreWallet,
    )

}
