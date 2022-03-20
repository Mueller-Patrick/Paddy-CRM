package com.p4ddy.paddycrm.plugins.gui.compose.contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.p4ddy.paddycrm.domain.contact.Contact

@Composable
fun ContactCompactView(contact: Contact) {
	Column(
		modifier = Modifier.fillMaxSize()
	) {
		Text("Name: ${contact.firstName} ${contact.lastName}")
		Text("Billing City: ${contact.address.city}")
	}
}
