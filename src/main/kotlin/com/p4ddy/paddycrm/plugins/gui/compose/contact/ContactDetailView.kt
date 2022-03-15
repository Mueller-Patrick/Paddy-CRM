package com.p4ddy.paddycrm.plugins.gui.compose.contact

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.p4ddy.paddycrm.adapters.contact.ContactBE
import com.p4ddy.paddycrm.adapters.contact.ContactConverter
import com.p4ddy.paddycrm.application.contact.ContactApplicationService
import com.p4ddy.paddycrm.domain.contact.Contact
import com.p4ddy.paddycrm.plugins.gui.compose.account.getTextfieldBackgroundColor
import com.p4ddy.paddycrm.plugins.gui.compose.layout.*
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.NavController
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.Screen
import com.p4ddy.paddycrm.plugins.persistence.exposed.account.AccountExposedRepo
import com.p4ddy.paddycrm.plugins.persistence.exposed.contact.ContactExposedRepo
import com.p4ddy.paddycrm.plugins.persistence.exposed.user.UserExposedRepo
import java.time.format.DateTimeFormatter

@Composable
fun ContactDetailView(
	navController: NavController,
	startInEditMode: Boolean = false
) {
	val contactRepo = ContactExposedRepo()
	val contactService = ContactApplicationService(contactRepo)
	val contactConverter = ContactConverter(UserExposedRepo(), AccountExposedRepo())

	var thisContact: Contact? = null
	var thisContactBE: ContactBE? = null

	if (navController.navigateParam != -1) {
		thisContact = contactService.findContactById(navController.navigateParam)
		thisContactBE = contactConverter.convertContactToBE(thisContact)
	} else {
		thisContactBE = ContactBE(
			accountName = "",
			accountId = -1,
			ownerName = "",
			ownerId = -1,
			salutation = "",
			lastName = "",
			firstName = "",
			addressCountry = "",
			addressCity = "",
			addressZip = "",
			addressStreetAndNumber = ""
		)
	}

	val accountId = remember { mutableStateOf(TextFieldValue(thisContactBE.accountId.toString())) }
	val salutation = remember { mutableStateOf(TextFieldValue(thisContactBE.salutation)) }
	val lastName = remember { mutableStateOf(TextFieldValue(thisContactBE.lastName)) }
	val firstName = remember { mutableStateOf(TextFieldValue(thisContactBE.firstName)) }
	val addressCountry = remember { mutableStateOf(TextFieldValue(thisContactBE.addressCountry)) }
	val addressCity = remember { mutableStateOf(TextFieldValue(thisContactBE.addressCity)) }
	val addressZip = remember { mutableStateOf(TextFieldValue(thisContactBE.addressZip)) }
	val addressStreetAndNumber = remember { mutableStateOf(TextFieldValue(thisContactBE.addressStreetAndNumber)) }

	val isEditMode = remember { mutableStateOf(startInEditMode) }

	Column {
		ControlButtonSection {
			Button(onClick = {
				if (isEditMode.value) {
					isEditMode.value = false

					thisContactBE.accountId = accountId.value.text.toInt()
					thisContactBE.salutation = salutation.value.text
					thisContactBE.lastName = lastName.value.text
					thisContactBE.firstName = firstName.value.text
					thisContactBE.addressCountry = addressCountry.value.text
					thisContactBE.addressCity = addressCity.value.text
					thisContactBE.addressZip = addressZip.value.text
					thisContactBE.addressStreetAndNumber = addressStreetAndNumber.value.text

					val contact = contactConverter.convertBEToContact(thisContactBE)

					if (navController.navigateParam != -1) {
						contactService.updateContact(contact)
					} else {
						val createdContact = contactService.createContact(contact)
						navController.navigate(Screen.ContactDetailView.name, createdContact.contactId)
					}
				} else {
					isEditMode.value = true
				}
			}) {
				if (isEditMode.value) {
					Text("Save")
				} else {
					Text("Edit")
				}
			}

			if (navController.navigateParam != -1) {
				Spacer(modifier = Modifier.width(10.dp))

				Button(onClick = {
					contactService.deleteContact(thisContact!!)
					navController.navigate(Screen.ContactListView.name)
				}) {
					Text("Delete")
				}
			}
		}

		DetailViewContainer("Contact Details") {
			DetailColumnTemplate {
				if (navController.navigateParam != -1) {
					DetailColumnTemplateLeft("Related Account", modifier = Modifier.weight(1F)) {
						Text(
							text = thisContactBE.accountName
						)
					}
				} else {
					TextField(
						value = accountId.value,
						onValueChange = {
							accountId.value = it
						},
						readOnly = !isEditMode.value,
						colors = getTextfieldBackgroundColor(isEditMode.value),
						label = {
							Text("Account Id")
						},
						modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
					)
				}

				DetailColumnTemplateRight("Contact Owner", modifier = Modifier.weight(1F)) {
					Text(
						text = thisContactBE.ownerName
					)
				}
			}

			Spacer(modifier = Modifier.height(2.dp))

			DetailColumnTemplate {
				DetailColumnTemplateLeft("Contact Id", modifier = Modifier.weight(1F)) {
					Text(
						text = thisContactBE.contactId.toString()
					)
				}

				DetailColumnTemplateRight("Creation Date", modifier = Modifier.weight(1F)) {
					Text(
						text = thisContactBE.createdDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
					)
				}
			}

			Spacer(modifier = Modifier.height(5.dp))

			DetailColumnTemplate {
				TextField(
					value = salutation.value,
					onValueChange = {
						salutation.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Salutation")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)

				TextField(
					value = addressCountry.value,
					onValueChange = {
						addressCountry.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Address Country")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}

			DetailColumnTemplate {
				TextField(
					value = firstName.value,
					onValueChange = {
						firstName.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("First Name")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)

				TextField(
					value = addressCity.value,
					onValueChange = {
						addressCity.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Address City")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}

			DetailColumnTemplate {
				TextField(
					value = lastName.value,
					onValueChange = {
						lastName.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Last Name")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)

				TextField(
					value = addressZip.value,
					onValueChange = {
						addressZip.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Address ZIP Code")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}

			DetailColumnTemplate {
				DetailColumnTemplateLeft("", modifier = Modifier.weight(1F)) {

				}

				TextField(
					value = addressStreetAndNumber.value,
					onValueChange = {
						addressStreetAndNumber.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Address Street and Number")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}
		}
	}
}
