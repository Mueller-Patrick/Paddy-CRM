package com.p4ddy.paddycrm.plugins.gui.compose.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.p4ddy.paddycrm.application.account.AccountApplicationService
import com.p4ddy.paddycrm.domain.account.AccountRepo
import com.p4ddy.paddycrm.plugins.gui.compose.layout.ControlButtonSection
import com.p4ddy.paddycrm.plugins.gui.compose.layout.ScrollView
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.NavController
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.Screen
import com.p4ddy.paddycrm.plugins.persistence.exposed.account.AccountExposedRepo
import org.jetbrains.skia.Color
import org.jetbrains.skia.FontStyle

@Composable
fun AccountListView(
	navController: NavController
) {
	val accountRepo: AccountRepo = AccountExposedRepo()
	val accountService: AccountApplicationService = AccountApplicationService(accountRepo)

	ControlButtonSection {
		Button(onClick = {
			navController.navigate(Screen.AccountCreateView.name)
		}) {
			Text("New")
		}
	}

	ScrollView("All Accounts") {
		Column(
			modifier = Modifier.fillMaxSize(1F)
		) {
			for (account in accountService.findAllAccounts()) {
				Button(
					onClick = {
						navController.navigate(Screen.AccountDetailView.name, account.accountId)
					},
					modifier = Modifier.padding(vertical = 5.dp).width(300.dp)
				) {
					AccountCompactView(account)
				}
			}
		}
	}
}
