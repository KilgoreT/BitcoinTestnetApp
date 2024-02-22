package me.apomazkin.bitcointestnetapp.features.restoreWallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import me.apomazkin.bitcointestnetapp.features.restoreWallet.tea.RestoreWalletEffectHandler
import me.apomazkin.bitcointestnetapp.features.restoreWallet.tea.RestoreWalletEffects
import me.apomazkin.bitcointestnetapp.features.restoreWallet.tea.RestoreWalletMessage
import me.apomazkin.bitcointestnetapp.features.restoreWallet.tea.RestoreWalletReducer
import me.apomazkin.bitcointestnetapp.features.restoreWallet.tea.RestoreWalletState
import me.apomazkin.bitcointestnetapp.tea.Tea
import me.apomazkin.bitcointestnetapp.tea.TeaStateHolder
import me.apomazkin.bitcointestnetapp.utils.BlockchainAdapter


class RestoreWalletViewModel : ViewModel(), TeaStateHolder<RestoreWalletState, RestoreWalletMessage> {

    private val stateHolder = Tea(
            initState = RestoreWalletState(),
            initEffects = setOf<RestoreWalletEffects>(),
            coroutineScope = viewModelScope,
            reducer = RestoreWalletReducer(),
            effectHandlerSet = setOf(
                    RestoreWalletEffectHandler(btcLibraryAdapter = BlockchainAdapter.getInstance())
            )
    )

    override val state: StateFlow<RestoreWalletState>
        get() = stateHolder.state

    override fun accept(message: RestoreWalletMessage) = stateHolder.accept(message)

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RestoreWalletViewModel() as T
        }
    }
}