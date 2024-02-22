package me.apomazkin.bitcointestnetapp.features.restoreWallet.tea

sealed interface RestoreWalletMessage

data class UpdateMnemonicMsg(val mnemonic: String) : RestoreWalletMessage
object ResetMnemonicMsg : RestoreWalletMessage
object RestoreWalletMsg : RestoreWalletMessage
data class ShowWalletMsg(val address: String) : RestoreWalletMessage
data class ShowLoadProgressMsg(val value: String) : RestoreWalletMessage