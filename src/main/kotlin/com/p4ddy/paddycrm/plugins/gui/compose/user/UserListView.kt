package com.p4ddy.paddycrm.plugins.gui.compose.user

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.p4ddy.paddycrm.application.opportunity.OpportunityApplicationService
import com.p4ddy.paddycrm.application.user.UserApplicationService
import com.p4ddy.paddycrm.domain.opportunity.OpportunityRepo
import com.p4ddy.paddycrm.domain.user.UserRepo
import com.p4ddy.paddycrm.plugins.gui.compose.layout.ControlButtonSection
import com.p4ddy.paddycrm.plugins.gui.compose.layout.ScrollView
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.NavController
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.Screen
import com.p4ddy.paddycrm.plugins.gui.compose.opportunity.OpportunityCompactView
import com.p4ddy.paddycrm.plugins.persistence.exposed.opportunity.OpportunityExposedRepo
import com.p4ddy.paddycrm.plugins.persistence.exposed.user.UserExposedRepo

@Composable
fun UserListView(
	navController: NavController
) {
	val userRepo: UserRepo = UserExposedRepo()
	val userService = UserApplicationService(userRepo)

	ControlButtonSection {
		Button(onClick = {
			navController.navigate(Screen.UserCreateView.name)
		}) {
			Text("New")
		}
	}

	ScrollView("All Users") {
		Column(
			modifier = Modifier.fillMaxSize(1F)
		) {
			for (user in userService.findAllUsers()) {
				Button(
					onClick = {
						navController.navigate(Screen.UserDetailView.name, user.userId)
					},
					modifier = Modifier.padding(vertical = 5.dp).width(300.dp)
				) {
					UserCompactView(user)
				}
			}
		}
	}
}
