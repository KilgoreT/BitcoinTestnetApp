package me.apomazkin.bitcointestnetapp.features.start.tea

import me.apomazkin.bitcointestnetapp.tea.ReducerResult
import me.apomazkin.bitcointestnetapp.tea.TeaReducer

class StartReducer : TeaReducer<StartState, StartMessage, StartEffects> {
    override fun reduce(
            state: StartState,
            message: StartMessage,
    ): ReducerResult<StartState, StartEffects> {
        return when (message) {
            is DoNothingMsg -> ReducerResult(state, setOf<StartEffects>())
            ShowWallet -> ReducerResult(
                    state.copy(isWalletCreated = true),
                    setOf()
            )
            ShowWalletCreation -> ReducerResult(
                    state.copy(isLoading = false, isWalletCreated = false),
                    setOf()
            )
            is ShowLoadProgressMsg -> ReducerResult(
                    state.copy(loadingProgress = message.value),
                    setOf()
            )
        }
    }
}