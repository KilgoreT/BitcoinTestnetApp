package me.apomazkin.bitcointestnetapp.features.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import me.apomazkin.bitcointestnetapp.features.wallet.tea.WalletEffectHandler
import me.apomazkin.bitcointestnetapp.features.wallet.tea.WalletEffects
import me.apomazkin.bitcointestnetapp.features.wallet.tea.WalletMessage
import me.apomazkin.bitcointestnetapp.features.wallet.tea.WalletReducer
import me.apomazkin.bitcointestnetapp.features.wallet.tea.WalletState
import me.apomazkin.bitcointestnetapp.network.Network
import me.apomazkin.bitcointestnetapp.tea.Tea
import me.apomazkin.bitcointestnetapp.tea.TeaStateHolder
import me.apomazkin.bitcointestnetapp.utils.BlockchainAdapter


class WalletViewModel : ViewModel(), TeaStateHolder<WalletState, WalletMessage> {


    private val stateHolder = Tea(
            initState = WalletState(isLoading = true),
            initEffects = setOf(
                    WalletEffects.CheckWalletAddress,
                    WalletEffects.CheckWalletBalance
            ),
            coroutineScope = viewModelScope,
            reducer = WalletReducer(),
            effectHandlerSet = setOf(
                    WalletEffectHandler(
                            btcLibraryAdapter = BlockchainAdapter.getInstance(),
                            network = Network.getInstance()
                    )
            )
    )

    override val state: StateFlow<WalletState>
        get() = stateHolder.state

    override fun accept(message: WalletMessage) = stateHolder.accept(message)

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WalletViewModel() as T
        }
    }
}