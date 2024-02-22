package me.apomazkin.bitcointestnetapp.features.start.tea

sealed interface StartMessage
object ShowWalletCreation : StartMessage
object ShowWallet : StartMessage
object DoNothingMsg : StartMessage
data class ShowLoadProgressMsg(val value: String) : StartMessage