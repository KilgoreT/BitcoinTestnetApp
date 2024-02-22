package me.apomazkin.bitcointestnetapp.features.start.tea


data class StartState(
        val isLoading: Boolean = true,
        val loadingProgress: String = "",
        val isWalletCreated: Boolean = false,
)