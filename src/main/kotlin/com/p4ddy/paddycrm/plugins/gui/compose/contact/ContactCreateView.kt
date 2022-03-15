package com.p4ddy.paddycrm.plugins.gui.compose.contact

import androidx.compose.runtime.Composable
import com.p4ddy.paddycrm.plugins.gui.compose.account.AccountDetailView
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.NavController

@Composable
fun ContactCreateView(
	navController: NavController
) {
	ContactDetailView(navController, true)
}
