package com.p4ddy.paddycrm.plugins.gui.compose.opportunity

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
import com.p4ddy.paddycrm.adapters.opportunity.OpportunityBE
import com.p4ddy.paddycrm.adapters.opportunity.OpportunityConverter
import com.p4ddy.paddycrm.application.opportunity.OpportunityApplicationService
import com.p4ddy.paddycrm.domain.opportunity.Opportunity
import com.p4ddy.paddycrm.domain.opportunity.OpportunityRepo
import com.p4ddy.paddycrm.plugins.gui.compose.account.getTextfieldBackgroundColor
import com.p4ddy.paddycrm.plugins.gui.compose.layout.*
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.NavController
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.Screen
import com.p4ddy.paddycrm.plugins.persistence.exposed.account.AccountExposedRepo
import com.p4ddy.paddycrm.plugins.persistence.exposed.opportunity.OpportunityExposedRepo
import com.p4ddy.paddycrm.plugins.persistence.exposed.user.UserExposedRepo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun OpportunityDetailView(
	navController: NavController,
	startInEditMode: Boolean = false
) {
	val opptyRepo: OpportunityRepo = OpportunityExposedRepo()
	val opptyService = OpportunityApplicationService(opptyRepo)
	val opptyConverter = OpportunityConverter(UserExposedRepo(), AccountExposedRepo())

	var thisOppty: Opportunity? = null
	var thisOpptyBE: OpportunityBE? = null

	if (navController.navigateParam != -1) {
		thisOppty = opptyService.findOpportunityById(navController.navigateParam)
		thisOpptyBE = opptyConverter.convertOpportunityToBE(thisOppty)
	} else {
		thisOpptyBE = OpportunityBE(
			name = "",
			accountName = "",
			accountId = -1,
			amount = 0.00F,
			closeDate = LocalDate.now().plusDays(30),
			ownerName = "",
			ownerId = -1,
			product = "",
			probability = 0,
			quantity = 0
		)
	}

	val name = remember { mutableStateOf(TextFieldValue(thisOpptyBE.name)) }
	val accountId = remember { mutableStateOf(TextFieldValue(thisOpptyBE.accountId.toString())) }
	val amount = remember { mutableStateOf(TextFieldValue(thisOpptyBE.amount.toString())) }
	val closeDate =
		remember { mutableStateOf(TextFieldValue(thisOpptyBE.closeDate.format(DateTimeFormatter.ISO_DATE))) }
	val product = remember { mutableStateOf(TextFieldValue(thisOpptyBE.product)) }
	val probability = remember { mutableStateOf(TextFieldValue(thisOpptyBE.probability.toString())) }
	val quantity = remember { mutableStateOf(TextFieldValue(thisOpptyBE.quantity.toString())) }

	val isEditMode = remember { mutableStateOf(startInEditMode) }

	Column {
		ControlButtonSection {
			Button(onClick = {
				if (isEditMode.value) {
					isEditMode.value = false

					thisOpptyBE.name = name.value.text
					thisOpptyBE.accountId = accountId.value.text.toInt()
					thisOpptyBE.amount = amount.value.text.toFloat()
					thisOpptyBE.closeDate = LocalDate.parse(closeDate.value.text, DateTimeFormatter.ISO_DATE)
					thisOpptyBE.product = product.value.text
					thisOpptyBE.probability = probability.value.text.toInt()
					thisOpptyBE.quantity = quantity.value.text.toInt()

					val oppty = opptyConverter.convertBEToOpportunity(thisOpptyBE)

					if (navController.navigateParam != -1) {
						opptyService.updateOpportunity(oppty)
					} else {
						val createdOppty = opptyService.createOpportunity(oppty)
						navController.navigate(Screen.OpportunityDetailView.name, createdOppty.opportunityId)
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
					opptyService.deleteOpportunity(thisOppty!!)
					navController.navigate(Screen.ContactListView.name)
				}) {
					Text("Delete")
				}
			}
		}

		DetailViewContainer("Opportunity Details") {
			DetailColumnTemplate {
				TextField(
					value = name.value,
					onValueChange = {
						name.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Opportunity Name")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)

				TextField(
					value = amount.value,
					onValueChange = {
						amount.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Amount (€)")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}

			DetailColumnTemplate {
				if (navController.navigateParam != -1) {
					DetailColumnTemplateLeft("Related Account", modifier = Modifier.weight(1F)) {
						Text(
							text = thisOpptyBE.accountName
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
						text = thisOpptyBE.ownerName
					)
				}
			}

			DetailColumnTemplate {
				DetailColumnTemplateLeft("Opportunity Id", modifier = Modifier.weight(1F)) {
					Text(
						text = thisOpptyBE.opportunityId.toString()
					)
				}

				DetailColumnTemplateRight("Creation Date", modifier = Modifier.weight(1F)) {
					Text(
						text = thisOpptyBE.createdDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
					)
				}
			}

			DetailColumnTemplate {
				TextField(
					value = product.value,
					onValueChange = {
						product.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Opportunity Product")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)

				TextField(
					value = closeDate.value,
					onValueChange = {
						closeDate.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Close Date")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}

			DetailColumnTemplate {
				TextField(
					value = quantity.value,
					onValueChange = {
						quantity.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Product Quantity")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)

				TextField(
					value = probability.value,
					onValueChange = {
						probability.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Probability")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}
		}
	}
}
