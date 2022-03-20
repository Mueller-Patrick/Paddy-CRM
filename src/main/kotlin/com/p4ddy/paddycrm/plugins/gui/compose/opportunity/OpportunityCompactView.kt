package com.p4ddy.paddycrm.plugins.gui.compose.opportunity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.p4ddy.paddycrm.domain.opportunity.Opportunity

@Composable
fun OpportunityCompactView(oppty: Opportunity) {
	Column(
		modifier = Modifier.fillMaxSize()
	) {
		Text("Name: ${oppty.name}")
		Text("Amount: ${oppty.amount}€")
		Text("Stage: ${oppty.stage}")
	}
}
