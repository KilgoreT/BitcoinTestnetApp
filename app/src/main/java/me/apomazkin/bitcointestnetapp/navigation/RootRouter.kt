package me.apomazkin.bitcointestnetapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.apomazkin.bitcointestnetapp.features.restoreWallet.RestoreWalletScreen
import me.apomazkin.bitcointestnetapp.features.start.StartScreen
import me.apomazkin.bitcointestnetapp.features.wallet.WalletScreen

enum class RootPoint(
        val route: String,
) {
    START_SCREEN("START_SCREEN"),
    WALLET_SCREEN("WALLET_SCREEN"),
    RESTORE_WALLET_SCREEN("RESTORE_WALLET_SCREEN"),
}

class RootRouter {
    companion object {
        val START_DESTINATION = RootPoint.START_SCREEN
    }
}

@Composable
fun RootRouter(
        navController: NavHostController,
) {

    var navigator: RootRouterNavigation? = null

    NavHost(
            navController = navController,
            startDestination = RootRouter.START_DESTINATION.route
    ) {
        composable(RootPoint.START_SCREEN.route) {
            StartScreen(
                    onOpenWallet = { navigator?.openWallet() },
                    onRestoreWallet = { navigator?.openRestoreWallet() },
            )
        }
        composable(RootPoint.RESTORE_WALLET_SCREEN.route) {
            RestoreWalletScreen {
                navigator?.openWallet()
            }
        }
        composable(RootPoint.WALLET_SCREEN.route) {
            WalletScreen()
        }
    }

    navigator = object : RootRouterNavigation {
        override fun openRestoreWallet() {
            navController.navigate(RootPoint.RESTORE_WALLET_SCREEN.route) {
                launchSingleTop = true
            }
        }

        override fun openWallet() {
            navController.navigate(RootPoint.WALLET_SCREEN.route) {
                launchSingleTop = true
            }
        }
    }
}

interface RootRouterNavigation {
    fun openRestoreWallet()
    fun openWallet()
}