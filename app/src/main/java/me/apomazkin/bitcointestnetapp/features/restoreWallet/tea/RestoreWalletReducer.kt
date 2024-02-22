package me.apomazkin.bitcointestnetapp.features.restoreWallet.tea

import me.apomazkin.bitcointestnetapp.tea.Effect
import me.apomazkin.bitcointestnetapp.tea.ReducerResult
import me.apomazkin.bitcointestnetapp.tea.TeaReducer
import me.apomazkin.bitcointestnetapp.utils.BlockchainAdapter

class RestoreWalletReducer: TeaReducer<RestoreWalletState, RestoreWalletMessage, Effect> {
    override fun reduce(
            state: RestoreWalletState,
            message: RestoreWalletMessage
    ): ReducerResult<RestoreWalletState, Effect> {
        return when(message) {
            is UpdateMnemonicMsg -> ReducerResult(
                    state.copy(
                            mnemonic = message.mnemonic.trim().split(" "),
                            isRestoreButtonEnable = isMnemonicValid(message.mnemonic),
                            wordsCount = getWordsCount(message.mnemonic)
                    ),
                    setOf()
            )
            ResetMnemonicMsg -> ReducerResult(
                    state.copy(
                            mnemonic = BlockchainAdapter.DEFAULT_SEED,
                            isRestoreButtonEnable = isMnemonicValid(BlockchainAdapter.DEFAULT_SEED),
                            wordsCount = getWordsCount(BlockchainAdapter.DEFAULT_SEED)
                    ),
                    setOf()
            )

            RestoreWalletMsg -> ReducerResult(
                    state.copy(isLoading = true),
                    setOf(RestoreWalletEffects.RestoreWallet(state.mnemonic))
            )

            is ShowWalletMsg -> ReducerResult(
                    state.copy(
                            isLoading = false,
                            screenResult = message.address
                    ),
                    setOf()
            )

            is ShowLoadProgressMsg -> ReducerResult(
                    state.copy(loadingProgress = message.value),
                    setOf()
            )
        }
    }

    private fun isMnemonicValid(mnemonic: List<String>): Boolean {
        return mnemonic.size == 12
    }

    private fun isMnemonicValid(mnemonic: String): Boolean {
        return mnemonic.split(" ").size == 12
    }

    private fun getWordsCount(mnemonic: List<String>): Int {
        return mnemonic.size
    }

    private fun getWordsCount(mnemonic: String): Int {
        return mnemonic.split(" ").size
    }
}