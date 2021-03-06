package com.p4ddy.paddycrm.plugins.gui.compose.account

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.p4ddy.paddycrm.adapters.account.AccountBE
import com.p4ddy.paddycrm.adapters.account.AccountConverter
import com.p4ddy.paddycrm.application.account.AccountApplicationService
import com.p4ddy.paddycrm.application.session.SessionManager
import com.p4ddy.paddycrm.domain.account.Account
import com.p4ddy.paddycrm.domain.account.AccountRepo
import com.p4ddy.paddycrm.plugins.gui.compose.layout.*
import com.p4ddy.paddycrm.plugins.gui.compose.misc.DialogDemo
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.NavController
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.Screen
import com.p4ddy.paddycrm.plugins.persistence.exposed.account.AccountExposedRepo
import com.p4ddy.paddycrm.plugins.persistence.exposed.user.UserExposedRepo
import com.p4ddy.paddycrm.plugins.session.SingletonSessionManager
import java.time.format.DateTimeFormatter

@Composable
fun AccountDetailView(
	navController: NavController,
	startInEditMode: Boolean = false
) {
	val sessionManager: SessionManager = SingletonSessionManager()

	val accountRepo: AccountRepo = AccountExposedRepo()
	val accountService = AccountApplicationService(accountRepo)
	val accountConverter = AccountConverter(UserExposedRepo(), sessionManager)

	var thisAccount: Account? = null
	var thisAccountBE: AccountBE? = null

	if (navController.navigateParam != -1) {
		thisAccount = accountService.findAccountById(navController.navigateParam)
		thisAccountBE = accountConverter.convertAccountToBE(thisAccount)
	} else {
		thisAccountBE = AccountBE(
			name = "",
			ownerName = "",
			ownerId = -1,
			billingCountry = "",
			billingCity = "",
			billingZip = "",
			billingStreetAndNumber = "",
			shippingCountry = "",
			shippingCity = "",
			shippingZip = "",
			shippingStreetAndNumber = "",
		)
	}

	val accountName = remember { mutableStateOf(TextFieldValue(thisAccountBE.name)) }
	val accountBillingCountry = remember { mutableStateOf(TextFieldValue(thisAccountBE.billingCountry)) }
	val accountBillingCity = remember { mutableStateOf(TextFieldValue(thisAccountBE.billingCity)) }
	val accountBillingZip = remember { mutableStateOf(TextFieldValue(thisAccountBE.billingZip)) }
	val accountBillingStreetAndNumber =
		remember { mutableStateOf(TextFieldValue(thisAccountBE.billingStreetAndNumber)) }
	val accountShippingCountry = remember { mutableStateOf(TextFieldValue(thisAccountBE.shippingCountry)) }
	val accountShippingCity = remember { mutableStateOf(TextFieldValue(thisAccountBE.shippingCity)) }
	val accountShippingZip = remember { mutableStateOf(TextFieldValue(thisAccountBE.shippingZip)) }
	val accountShippingStreetAndNumber =
		remember { mutableStateOf(TextFieldValue(thisAccountBE.shippingStreetAndNumber)) }

	val isEditMode = remember { mutableStateOf(startInEditMode) }
	val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
	val errorMessage = remember { mutableStateOf("") }

	Column {
		ControlButtonSection {
			Button(onClick = {
				if (isEditMode.value) {
					thisAccountBE.name = accountName.value.text
					thisAccountBE.billingCountry = accountBillingCountry.value.text
					thisAccountBE.billingCity = accountBillingCity.value.text
					thisAccountBE.billingZip = accountBillingZip.value.text
					thisAccountBE.billingStreetAndNumber = accountBillingStreetAndNumber.value.text
					thisAccountBE.shippingCountry = accountShippingCountry.value.text
					thisAccountBE.shippingCity = accountShippingCity.value.text
					thisAccountBE.shippingZip = accountShippingZip.value.text
					thisAccountBE.shippingStreetAndNumber = accountShippingStreetAndNumber.value.text

					val account = try {
						accountConverter.convertBEToAccount(thisAccountBE)
					} catch (e: Exception) {
						errorMessage.value = e.localizedMessage
						setShowDialog(true)
						return@Button
					}

					if (navController.navigateParam != -1) {
						try {
							accountService.updateAccount(account)
						} catch (e: Exception) {
							errorMessage.value = e.localizedMessage
							setShowDialog(true)
							return@Button
						}
					} else {
						try {
							val createdAccount = accountService.createAccount(account)
							navController.navigate(Screen.AccountDetailView.name, createdAccount.accountId)
						} catch (e: Exception) {
							errorMessage.value = e.localizedMessage
							setShowDialog(true)
							return@Button
						}
					}

					isEditMode.value = false
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
					try {
						accountService.deleteAccount(thisAccount!!)
						navController.navigate(Screen.AccountListView.name)
					} catch (e: Exception) {
						errorMessage.value = e.localizedMessage
						setShowDialog(true)
						return@Button
					}
				}) {
					Text("Delete")
				}
			}
		}

		DetailViewContainer("Account Details") {
			DetailColumnTemplate {
				TextField(
					value = accountName.value,
					onValueChange = {
						accountName.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Account Name")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)

				DetailColumnTemplateRight("Account Owner", modifier = Modifier.weight(1F)) {
					Text(
						text = thisAccountBE.ownerName
					)
				}
			}

			Spacer(modifier = Modifier.height(2.dp))

			DetailColumnTemplate {
				DetailColumnTemplateLeft("Account Id", modifier = Modifier.weight(1F)) {
					Text(
						text = thisAccountBE.accountId.toString()
					)
				}

				DetailColumnTemplateRight("Creation Date", modifier = Modifier.weight(1F)) {
					Text(
						text = thisAccountBE.createdDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
					)
				}
			}

			Spacer(modifier = Modifier.height(5.dp))

			DetailColumnTemplate {
				TextField(
					value = accountBillingCountry.value,
					onValueChange = {
						accountBillingCountry.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Billing Country")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)

				TextField(
					value = accountShippingCountry.value,
					onValueChange = {
						accountShippingCountry.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Shipping Country")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}

			DetailColumnTemplate {
				TextField(
					value = accountBillingCity.value,
					onValueChange = {
						accountBillingCity.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Billing City")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)

				TextField(
					value = accountShippingCity.value,
					onValueChange = {
						accountShippingCity.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Shipping City")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}

			DetailColumnTemplate {
				TextField(
					value = accountBillingZip.value,
					onValueChange = {
						accountBillingZip.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Billing ZIP Code")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)

				TextField(
					value = accountShippingZip.value,
					onValueChange = {
						accountShippingZip.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Shipping ZIP Code")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}

			DetailColumnTemplate {
				TextField(
					value = accountBillingStreetAndNumber.value,
					onValueChange = {
						accountBillingStreetAndNumber.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Billing Street and Number")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)

				TextField(
					value = accountShippingStreetAndNumber.value,
					onValueChange = {
						accountShippingStreetAndNumber.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Shipping Street and Number")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}
		}
		DialogDemo(showDialog, errorMessage.value, setShowDialog)
	}
}

@Composable
fun getTextfieldBackgroundColor(isEditMode: Boolean): TextFieldColors {
	return if (isEditMode) {
		textFieldColors(backgroundColor = Color.White)
	} else {
		textFieldColors(backgroundColor = Color.LightGray)
	}
}
