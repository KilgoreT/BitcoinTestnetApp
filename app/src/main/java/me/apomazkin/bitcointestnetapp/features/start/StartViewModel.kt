package me.apomazkin.bitcointestnetapp.features.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import me.apomazkin.bitcointestnetapp.features.start.tea.StartEffectHandler
import me.apomazkin.bitcointestnetapp.features.start.tea.StartEffects
import me.apomazkin.bitcointestnetapp.features.start.tea.StartMessage
import me.apomazkin.bitcointestnetapp.features.start.tea.StartReducer
import me.apomazkin.bitcointestnetapp.features.start.tea.StartState
import me.apomazkin.bitcointestnetapp.tea.Tea
import me.apomazkin.bitcointestnetapp.tea.TeaStateHolder
import me.apomazkin.bitcointestnetapp.utils.BlockchainAdapter


class StartViewModel : ViewModel(), TeaStateHolder<StartState, StartMessage> {


    private val stateHolder = Tea(
            initState = StartState(isLoading = true),
            initEffects = setOf(
                    StartEffects.CheckWallet
            ),
            coroutineScope = viewModelScope,
            reducer = StartReducer(),
            effectHandlerSet = setOf(
                    StartEffectHandler(
                            btcLibraryAdapter = BlockchainAdapter.getInstance(),
                    )
            )
    )

    override val state: StateFlow<StartState>
        get() = stateHolder.state

    override fun accept(message: StartMessage) = stateHolder.accept(message)

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return StartViewModel() as T
        }
    }
}