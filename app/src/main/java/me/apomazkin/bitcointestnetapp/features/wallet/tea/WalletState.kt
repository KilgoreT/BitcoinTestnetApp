package me.apomazkin.bitcointestnetapp.features.wallet.tea


data class WalletState(
        val isLoading: Boolean = false,
        val balance: String = "",
        val sourceAddress: String = "",
        val destinationAddress: String = "",
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