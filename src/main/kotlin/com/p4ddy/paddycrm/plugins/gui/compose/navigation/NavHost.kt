package com.p4ddy.paddycrm.plugins.gui.compose.navigation

import androidx.compose.runtime.Composable

class NavHost(
	val navController: NavController,
	val contents: @Composable NavigationGraphBuilder.() -> Unit
) {
	@Composable
	fun build() {
		NavigationGraphBuilder().renderContents()
	}

	inner class NavigationGraphBuilder(
		val navController: NavController = this@NavHost.navController
	) {
		@Composable
		fun renderContents() {
			this@NavHost.contents(this)
		}
	}
}

@Composable
fun NavHost.NavigationGraphBuilder.composable(
	route: String,
	content: @Composable () -> Unit
) {
	if (navController.currentScreen.value == route) {
		content()
	}
}
