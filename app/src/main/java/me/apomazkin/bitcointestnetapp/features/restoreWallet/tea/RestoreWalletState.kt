package me.apomazkin.bitcointestnetapp.features.restoreWallet.tea

import me.apomazkin.bitcointestnetapp.utils.BlockchainAdapter


data class RestoreWalletState(
        val isLoading: Boolean = false,
        val loadingProgress: String = "",
        val mnemonic: List<String> = BlockchainAdapter.DEFAULT_SEED,
        val wordsCount: Int = BlockchainAdapter.DEFAULT_SEED.size,
        val isRestoreButtonEnable: Boolean = true,
        val screenResult: String? = null,
)