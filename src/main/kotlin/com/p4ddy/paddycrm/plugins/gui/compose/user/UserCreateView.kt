package com.p4ddy.paddycrm.plugins.gui.compose.user

import androidx.compose.runtime.Composable
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.NavController
import com.p4ddy.paddycrm.plugins.gui.compose.opportunity.OpportunityDetailView

@Composable
fun UserCreateView(
	navController: NavController
) {
	UserDetailView(navController, true)
}
