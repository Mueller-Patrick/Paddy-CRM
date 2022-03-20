package com.p4ddy.paddycrm.plugins.gui.compose.opportunity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.p4ddy.paddycrm.application.opportunity.OpportunityApplicationService
import com.p4ddy.paddycrm.domain.opportunity.OpportunityRepo
import com.p4ddy.paddycrm.plugins.gui.compose.layout.ControlButtonSection
import com.p4ddy.paddycrm.plugins.gui.compose.layout.ScrollView
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.NavController
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.Screen
import com.p4ddy.paddycrm.plugins.persistence.exposed.opportunity.OpportunityExposedRepo

@Composable
fun OpportunityListView(
	navController: NavController
) {
	val opptyRepo: OpportunityRepo = OpportunityExposedRepo()
	val opptyService = OpportunityApplicationService(opptyRepo)

	ControlButtonSection {
		Button(onClick = {
			navController.navigate(Screen.OpportunityCreateView.name)
		}) {
			Text("New")
		}
	}

	ScrollView("All Opportunities") {
		Column(
			modifier = Modifier.fillMaxSize(1F)
		) {
			for (oppty in opptyService.findAllOpportunities()) {
				Button(
					onClick = {
						navController.navigate(Screen.OpportunityDetailView.name, oppty.opportunityId)
					},
					modifier = Modifier.padding(vertical = 5.dp).width(300.dp)
				) {
					OpportunityCompactView(oppty)
				}
			}
		}
	}
}
