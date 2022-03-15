package com.p4ddy.paddycrm.plugins.gui.compose.opportunity

import androidx.compose.runtime.Composable
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.NavController

@Composable
fun OpportunityCreateView(
	navController: NavController
) {
	println("Here")
	OpportunityDetailView(navController, true)
}
