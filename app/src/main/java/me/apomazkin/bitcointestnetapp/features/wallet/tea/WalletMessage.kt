package me.apomazkin.bitcointestnetapp.features.wallet.tea

sealed interface WalletMessage

data class TypeAddressMsg(val value: String) : WalletMessage
data class TypeAmountMsg(val value: String) : WalletMessage
data class AddressMsg(val value: String): WalletMessage
data class BalanceMsg(val value: String): WalletMessage
object SendAmount: WalletMessage
data class SuccessSend(val txId: String): WalletMessage
data class FailSend(val error: String): WalletMessage
object CloseDialog: WalletMessage
object DoNothingMsg : WalletMessage