package com.p4ddy.paddycrm.plugins.gui.compose.contact

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.p4ddy.paddycrm.application.account.AccountApplicationService
import com.p4ddy.paddycrm.application.contact.ContactApplicationService
import com.p4ddy.paddycrm.domain.account.AccountRepo
import com.p4ddy.paddycrm.domain.contact.ContactRepo
import com.p4ddy.paddycrm.plugins.gui.compose.account.AccountCompactView
import com.p4ddy.paddycrm.plugins.gui.compose.layout.ControlButtonSection
import com.p4ddy.paddycrm.plugins.gui.compose.layout.ScrollView
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.NavController
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.Screen
import com.p4ddy.paddycrm.plugins.persistence.exposed.account.AccountExposedRepo
import com.p4ddy.paddycrm.plugins.persistence.exposed.contact.ContactExposedRepo
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable.contactId

@Composable
fun ContactListView(
	navController: NavController
) {
	val contactRepo: ContactRepo = ContactExposedRepo()
	val contactService = ContactApplicationService(contactRepo)

	ControlButtonSection {
		Button(onClick = {
			navController.navigate(Screen.ContactCreateView.name)
		}) {
			Text("New")
		}
	}

	ScrollView("All Contacts") {
		Column(
			modifier = Modifier.fillMaxSize(1F)
		) {
			for (contact in contactService.findAllContacts()) {
				Button(
					onClick = {
						navController.navigate(Screen.ContactDetailView.name, contact.contactId)
					},
					modifier = Modifier.padding(vertical = 5.dp).width(300.dp)
				) {
					ContactCompactView(contact)
				}
			}
		}
	}
}
