package com.p4ddy.paddycrm.plugins.gui.compose.account

import androidx.compose.runtime.Composable
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.NavController

@Composable
fun AccountCreateView(
	navController: NavController
) {
	AccountDetailView(navController, true)
}
