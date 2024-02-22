package me.apomazkin.bitcointestnetapp.utils

import me.apomazkin.bitcointestnetapp.App
import org.bitcoinj.core.Address
import org.bitcoinj.core.Block
import org.bitcoinj.core.BlockChain
import org.bitcoinj.core.Coin
import org.bitcoinj.core.Context
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.FilteredBlock
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.core.Peer
import org.bitcoinj.core.PeerGroup
import org.bitcoinj.core.Transaction
import org.bitcoinj.core.TransactionOutput
import org.bitcoinj.net.discovery.DnsDiscovery
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.script.Script
import org.bitcoinj.store.SPVBlockStore
import org.bitcoinj.wallet.DeterministicSeed
import org.bitcoinj.wallet.KeyChainGroupStructure
import org.bitcoinj.wallet.Wallet
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

interface BlockchainAdapter {

    suspend fun isWalletExist(onLoadProgress: (String) -> Unit, ): Boolean
    suspend fun createWalletFromMnemonic(mnemonicCode: List<String>, onLoadProgress: (String) -> Unit, ): Wallet
    fun getBalance(): String
    fun getAddress(): String
    fun createTransaction(destinationAddress: String, amountToSend: String): String
    fun getCurrentReceiveAddress(): String

    class BlockchainAdapterImpl constructor(
            private val params: NetworkParameters,
    ): BlockchainAdapter {

        private var wallet: Wallet? = null

        override fun getAddress(): String {
            return wallet?.currentReceiveAddress()?.toString() ?: throw IllegalStateException("Wallet is not created")
        }

        override suspend fun isWalletExist(
                onLoadProgress: (String) -> Unit,
        ): Boolean {
            val directory: File = App.instance.getExternalFilesDir(null)!!
            val walletFile = File(directory, WALLET_FILE_NAME)
            if (walletFile.exists()) {
                val restoredWallet = Wallet.loadFromFile(walletFile)
                wallet = restoredWallet
                startSync(restoredWallet, onLoadProgress)
            }
            return walletFile.exists()
        }

        override suspend fun createWalletFromMnemonic(
                mnemonicCode: List<String>,
                onLoadProgress: (String) -> Unit,
        ): Wallet {

            val directory: File = App.instance.getExternalFilesDir(null)!!
            val walletFile = File(directory, WALLET_FILE_NAME)
            if (walletFile.exists()) {
                val restoredWallet = Wallet.loadFromFile(walletFile)
                wallet = restoredWallet
                startSync(restoredWallet, onLoadProgress)
                return restoredWallet
            } else {
                Context.propagate(Context(params))
                val currentTimeMillis = System.currentTimeMillis()
                val thirtyDaysInMillis = 30L * 24 * 60 * 60 * 1000 //30 days ago in milliseconds
                val time = (currentTimeMillis - thirtyDaysInMillis) / 1000

                val seed = DeterministicSeed(mnemonicCode, null, "", time)
                val restoredWallet = Wallet.fromSeed(
                        params,
                        seed,
                        Script.ScriptType.P2WPKH,
                        KeyChainGroupStructure.DEFAULT
                )
                startSync(restoredWallet, onLoadProgress)
                wallet = restoredWallet
            }
            return wallet ?: throw IllegalStateException("Wallet is not created")
        }

        private suspend fun startSync(
                wallet: Wallet,
                onLoadProgress: (String) -> Unit,
        ) {
            Context.propagate(Context(params))
            val directory: File = App.instance.getExternalFilesDir(null)!!
            val blockFile = File(directory, CHAIN_FILE_NAME)
            val blockStore = SPVBlockStore(params, blockFile)
            val chain = BlockChain(params, wallet, blockStore)
            val peerGroup = PeerGroup(params, chain)
            peerGroup.addWallet(wallet)
            peerGroup.addPeerDiscovery(DnsDiscovery(params))
            peerGroup.addBlocksDownloadedEventListener { _: Peer, block: Block, _: FilteredBlock?, blocksLeft: Int ->
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(block.time)
                onLoadProgress.invoke("Blocks left: $blocksLeft [$formattedDate]")
            }
            peerGroup.start()
            peerGroup.downloadBlockChain()
            peerGroup.stop()

            val walletFile = File(directory, WALLET_FILE_NAME)
            wallet.saveToFile(walletFile)
        }

        override fun getBalance(): String {
            val currentWallet = wallet ?: throw IllegalStateException("Wallet is not created")
            return currentWallet.getBalance(Wallet.BalanceType.AVAILABLE).toFriendlyString()
        }

        override fun createTransaction(destinationAddress: String, amountToSend: String): String {

            val currentWallet = wallet ?: throw IllegalStateException("Wallet is not created")

            val amount: Coin = Coin.parseCoin(amountToSend.replace(',', '.'))
            val toAddress: Address = Address.fromString(params, destinationAddress)
            val utxos = currentWallet.calculateAllSpendCandidates()
            val transaction = Transaction(params)

            val fee = Coin.parseCoin("0.000005")
            val totalAmount = utxos.map { it.value }.fold(Coin.ZERO) { acc, value -> acc.add(value) }
            val outputToRecipient = TransactionOutput(params, transaction, amount, toAddress)
            transaction.addOutput(outputToRecipient)

            val changeAmount = totalAmount.subtract(amount).subtract(fee)
            val freshAddress = currentWallet.freshReceiveAddress()
            val changeOutput = TransactionOutput(params, transaction, changeAmount, freshAddress)
            transaction.addOutput(changeOutput)

            utxos.forEach { utxo: TransactionOutput ->
                val script: Script = utxo.scriptPubKey
                val pubKeyHash: ByteArray = utxo.scriptPubKey.pubKeyHash
                val ecKey: ECKey = currentWallet.findKeyFromPubKeyHash(pubKeyHash, script.scriptType)
                        ?: throw IllegalStateException("No key found for the utxo")
                transaction.addSignedInput(utxo, ecKey, Transaction.SigHash.ALL, true)
            }

            val hex = transaction.toHexString()
            return hex
        }

        override fun getCurrentReceiveAddress(): String {
            return wallet?.currentReceiveAddress()?.toString() ?: throw IllegalStateException("Wallet is not created")
        }
    }

    companion object {
        private var instance: BlockchainAdapterImpl? = null
        fun getInstance(): BlockchainAdapterImpl {
            if (instance == null) {
                val params: NetworkParameters = TestNet3Params.get()
                instance = BlockchainAdapterImpl(params)
            }
            return instance ?: throw AssertionError("BtcLibraryAdapter instance is null")
        }

        val DEFAULT_SEED: List<String> = listOf(
                "guitar", "luxury", "plate", "duty",
                "estate", "creek", "amateur", "summer",
                "ostrich", "note", "sponsor", "negative"
        )
        private const val WALLET_FILE_NAME = "wallet"
        private const val CHAIN_FILE_NAME = "wallet.chain"
    }
}