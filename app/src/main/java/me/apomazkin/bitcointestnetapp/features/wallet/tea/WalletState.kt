package me.apomazkin.bitcointestnetapp.features.wallet.tea


data class WalletState(
        val isLoading: Boolean = false,
        val balance: String = "",
        val sourceAddress: String = "",
        val destinationAddress: String = "tb1qwxmgtw5n7e49d80mykt2uj69dg4c2x04ac2748",
        val amountToSend: String = "0.000001",
        val error: ErrorResult = ErrorResult(),
        val success: SuccessResult = SuccessResult(),
)

data class SuccessResult(
        val transactionId: String? = null,
        val showSuccessDialog: Boolean = false,
)

data class ErrorResult(
        val message: String? = null,
        val showErrorDialog: Boolean = false,
)