package me.apomazkin.bitcointestnetapp.features.start.tea

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.apomazkin.bitcointestnetapp.tea.Effect
import me.apomazkin.bitcointestnetapp.tea.TeaEffectHandler
import me.apomazkin.bitcointestnetapp.utils.BlockchainAdapter

sealed interface StartEffects : Effect {

    object CheckWallet : StartEffects
}

internal class StartEffectHandler(
        private val btcLibraryAdapter: BlockchainAdapter,
) : TeaEffectHandler<StartMessage, StartEffects> {

    override suspend fun runEffect(
        effect: StartEffects,
        consumer: (StartMessage) -> Unit
    ) {
        return when (effect) {
            StartEffects.CheckWallet -> {
                withContext(Dispatchers.Default) {
                    val isExist = btcLibraryAdapter.isWalletExist {
                        consumer(ShowLoadProgressMsg(it))
                    }
                    if (isExist) {
                        ShowWallet
                    } else {
                        ShowWalletCreation
                    }
                }
            }
        }.let(consumer)
    }
}