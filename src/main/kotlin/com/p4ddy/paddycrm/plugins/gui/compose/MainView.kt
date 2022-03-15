package com.p4ddy.paddycrm.plugins.gui.compose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.p4ddy.paddycrm.plugins.gui.compose.account.AccountCreateView
import com.p4ddy.paddycrm.plugins.gui.compose.account.AccountDetailView
import com.p4ddy.paddycrm.plugins.gui.compose.account.AccountListView
import com.p4ddy.paddycrm.plugins.gui.compose.contact.ContactCreateView
import com.p4ddy.paddycrm.plugins.gui.compose.contact.ContactDetailView
import com.p4ddy.paddycrm.plugins.gui.compose.contact.ContactListView
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.*
import com.p4ddy.paddycrm.plugins.gui.compose.opportunity.OpportunityCreateView
import com.p4ddy.paddycrm.plugins.gui.compose.opportunity.OpportunityDetailView
import com.p4ddy.paddycrm.plugins.gui.compose.opportunity.OpportunityListView
import com.p4ddy.paddycrm.plugins.gui.compose.user.UserCreateView
import com.p4ddy.paddycrm.plugins.gui.compose.user.UserDetailView
import com.p4ddy.paddycrm.plugins.gui.compose.user.UserListView

@Composable
fun MainView() {
	val screens = Screen.values().toList()
	val navController by rememberNavController(Screen.AccountListView.name)
	val currentScreen by remember {
		navController.currentScreen
	}

	MaterialTheme {
		Surface(
			modifier = Modifier.background(color = MaterialTheme.colors.background)
		) {
			Box(
				modifier = Modifier.fillMaxSize()
			) {
				NavigationRail(
					modifier = Modifier.align(Alignment.CenterStart).fillMaxHeight()
				) {
					screens.forEach {
						if(it.showInNavBar) {
							NavigationRailItem(
								selected = currentScreen == it.name,
								icon = {
									Icon(
										imageVector = it.icon,
										contentDescription = it.label
									)
								},
								label = {
									Text(it.label)
								},
								alwaysShowLabel = false,
								onClick = {
									navController.navigate(it.name)
								}
							)
						}
					}
				}

				Box(modifier = Modifier.fillMaxHeight()) {
					CustomNavigationHost(navController = navController)
				}
			}
		}
	}
}

@Composable
fun CustomNavigationHost(
	navController: NavController
) {
	NavHost(navController) {
		composable(Screen.AccountListView.name) {
			AccountListView(navController)
		}

		composable(Screen.AccountDetailView.name) {
			AccountDetailView(navController)
		}

		composable(Screen.AccountCreateView.name) {
			AccountCreateView(navController)
		}

		composable(Screen.ContactListView.name) {
			ContactListView(navController)
		}

		composable(Screen.ContactDetailView.name) {
			ContactDetailView(navController)
		}

		composable(Screen.ContactCreateView.name) {
			ContactCreateView(navController)
		}

		composable(Screen.OpportunityListView.name) {
			OpportunityListView(navController)
		}

		composable(Screen.OpportunityDetailView.name) {
			OpportunityDetailView(navController)
		}

		composable(Screen.OpportunityCreateView.name) {
			OpportunityCreateView(navController)
		}

		composable(Screen.UserListView.name) {
			UserListView(navController)
		}

		composable(Screen.UserDetailView.name) {
			UserDetailView(navController)
		}

		composable(Screen.UserCreateView.name) {
			UserCreateView(navController)
		}
	}.build()
}
