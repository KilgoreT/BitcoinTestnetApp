package me.apomazkin.bitcointestnetapp.features.restoreWallet.tea

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.apomazkin.bitcointestnetapp.tea.Effect
import me.apomazkin.bitcointestnetapp.tea.TeaEffectHandler
import me.apomazkin.bitcointestnetapp.utils.BlockchainAdapter

internal sealed interface RestoreWalletEffects : Effect {

    data class RestoreWallet(val mnemonic: List<String>) : RestoreWalletEffects
}

internal class RestoreWalletEffectHandler(
        private val btcLibraryAdapter: BlockchainAdapter,
) : TeaEffectHandler<RestoreWalletMessage, Effect> {

    override suspend fun runEffect(
        effect: Effect,
        consumer: (RestoreWalletMessage) -> Unit
    ) {
        return when (val eff = effect as? RestoreWalletEffects) {
            is RestoreWalletEffects.RestoreWallet -> {
                withContext(Dispatchers.Default) {
                    btcLibraryAdapter.createWalletFromMnemonic(
                            mnemonicCode = eff.mnemonic,
                            onLoadProgress = { value ->
                                consumer(ShowLoadProgressMsg(value))
                            }
                    )
                    ShowWalletMsg(btcLibraryAdapter.getCurrentReceiveAddress())
                }
            }
            null -> ResetMnemonicMsg
        }.let(consumer)
    }
}