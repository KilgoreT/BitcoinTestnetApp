package me.apomazkin.bitcointestnetapp.features.wallet.tea

import me.apomazkin.bitcointestnetapp.tea.Effect
import me.apomazkin.bitcointestnetapp.tea.ReducerResult
import me.apomazkin.bitcointestnetapp.tea.TeaReducer

class WalletReducer : TeaReducer<WalletState, WalletMessage, Effect> {
    override fun reduce(
            state: WalletState,
            message: WalletMessage,
    ): ReducerResult<WalletState, Effect> {
        return when (message) {
            is TypeAddressMsg -> {
                ReducerResult(
                        state.copy(destinationAddress = message.value),
                        setOf()
                )
            }

            is TypeAmountMsg -> {
                ReducerResult(
                        state.copy(amountToSend = message.value),
                        setOf(),
                )
            }

            is AddressMsg -> {
                ReducerResult(
                        state.copy(sourceAddress = message.value),
                        setOf()
                )
            }

            is BalanceMsg -> {
                ReducerResult(
                        state.copy(balance = message.value),
                        setOf()
                )
            }

            is SendAmount -> {
                ReducerResult(
                        state,
                        setOf(WalletEffects.Send(state.destinationAddress, state.amountToSend))
                )
            }

            is FailSend -> ReducerResult(
                    state.copy(
                            error = state.error.copy(
                                    message = message.error,
                                    showErrorDialog = true
                            )
                    ),
                    setOf(),
            )

            is SuccessSend -> ReducerResult(
                    state.copy(
                            success = state.success.copy(
                                    transactionId = message.txId,
                                    showSuccessDialog = true
                            )
                    ),
                    setOf(),
            )

            is CloseDialog -> ReducerResult(
                    state.copy(
                            error = state.error.copy(showErrorDialog = false),
                            success = state.success.copy(showSuccessDialog = false)
                    ),
                    setOf()
            )
            is DoNothingMsg -> ReducerResult(state, setOf())
        }
    }
}