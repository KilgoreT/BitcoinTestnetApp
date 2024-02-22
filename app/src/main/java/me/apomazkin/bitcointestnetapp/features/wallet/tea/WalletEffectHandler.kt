package me.apomazkin.bitcointestnetapp.features.wallet.tea

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.apomazkin.bitcointestnetapp.network.Network
import me.apomazkin.bitcointestnetapp.tea.Effect
import me.apomazkin.bitcointestnetapp.tea.TeaEffectHandler
import me.apomazkin.bitcointestnetapp.utils.BlockchainAdapter
import retrofit2.Response

internal sealed interface WalletEffects : Effect {

    object CheckWalletAddress : WalletEffects
    object CheckWalletBalance : WalletEffects
    data class Send(val recipient: String, val amount: String) : WalletEffects
}

internal class WalletEffectHandler(
        private val btcLibraryAdapter: BlockchainAdapter,
        private val network: Network,
) : TeaEffectHandler<WalletMessage, Effect> {

    override suspend fun runEffect(
            effect: Effect,
            consumer: (WalletMessage) -> Unit,
    ) {
        return when (val eff = effect as? WalletEffects) {
            is WalletEffects.CheckWalletAddress -> {
                withContext(Dispatchers.Default) {
                    val address = btcLibraryAdapter.getAddress()
                    AddressMsg(value = address)
                }
            }

            is WalletEffects.CheckWalletBalance -> {
                withContext(Dispatchers.Default) {
                    val balance = btcLibraryAdapter.getBalance()
                    BalanceMsg(value = balance)
                }
            }

            is WalletEffects.Send -> {
                withContext(Dispatchers.Default) {
                    val transaction = btcLibraryAdapter.createTransaction(eff.recipient, eff.amount)
                    val result: Response<String> = network.apiService.postTransaction(transaction).execute()

                    if (result.isSuccessful) {
                        result.body()?.let { txid ->
                            SuccessSend(txId = txid)
                        } ?: run {
                            FailSend(error = "Transaction id not given")
                        }
                    } else {
                        FailSend(error = result.errorBody()?.string() ?: "Unknown error")
                    }
                }
            }
            null -> DoNothingMsg
        }.let(consumer)
    }
}