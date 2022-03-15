package com.p4ddy.paddycrm.plugins.gui.compose.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.p4ddy.paddycrm.adapters.user.UserBE
import com.p4ddy.paddycrm.adapters.user.UserConverter
import com.p4ddy.paddycrm.application.user.UserApplicationService
import com.p4ddy.paddycrm.domain.user.User
import com.p4ddy.paddycrm.domain.user.UserTypes
import com.p4ddy.paddycrm.plugins.gui.compose.account.getTextfieldBackgroundColor
import com.p4ddy.paddycrm.plugins.gui.compose.layout.*
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.NavController
import com.p4ddy.paddycrm.plugins.gui.compose.navigation.Screen
import com.p4ddy.paddycrm.plugins.persistence.exposed.user.UserExposedRepo
import java.time.format.DateTimeFormatter

@Composable
fun UserDetailView(
	navController: NavController,
	startInEditMode: Boolean = false
) {
	val userRepo = UserExposedRepo()
	val userService = UserApplicationService(userRepo)
	val userConverter = UserConverter(userRepo)

	var thisUser: User? = null
	var thisUserBE: UserBE? = null

	if (navController.navigateParam != -1) {
		thisUser = userService.findUserById(navController.navigateParam)
		thisUserBE = userConverter.convertUserToBE(thisUser)
	} else {
		thisUserBE = UserBE(
			lastName = "",
			firstName = "",
			password = "",
			email = "",
			managerName = "",
			managerId = -1
		)
	}

	val lastName = remember { mutableStateOf(TextFieldValue(thisUserBE.lastName)) }
	val firstName = remember { mutableStateOf(TextFieldValue(thisUserBE.firstName)) }
	val password = remember { mutableStateOf(TextFieldValue(thisUserBE.password)) }
	val email = remember { mutableStateOf(TextFieldValue(thisUserBE.email)) }
	val managerId = remember { mutableStateOf(TextFieldValue(thisUserBE.managerId.toString())) }
	val userType = remember { mutableStateOf(TextFieldValue(thisUserBE.userType.toString())) }

	val isEditMode = remember { mutableStateOf(startInEditMode) }

	Column {
		ControlButtonSection {
			Button(onClick = {
				if (isEditMode.value) {
					isEditMode.value = false

					thisUserBE.lastName = lastName.value.text
					thisUserBE.firstName = firstName.value.text
					thisUserBE.password = password.value.text
					thisUserBE.email = email.value.text
					thisUserBE.managerId = managerId.value.text.toInt()
					thisUserBE.userType = UserTypes.valueOf(userType.value.text)

					val user = userConverter.convertBEToUser(thisUserBE)

					if (navController.navigateParam != -1) {
						userService.updateUser(user)
					} else {
						val createdUser = when (thisUserBE.userType) {
							UserTypes.SALESREP -> {
								userService.createSalesRep(
									thisUserBE.lastName,
									thisUserBE.firstName,
									thisUserBE.password,
									thisUserBE.email,
									thisUserBE.managerId
								)
							}
							UserTypes.MANAGER -> {
								userService.createManager(
									thisUserBE.lastName,
									thisUserBE.firstName,
									thisUserBE.password,
									thisUserBE.email
								)
							}
							UserTypes.ADMIN -> {
								userService.createAdmin(
									thisUserBE.lastName,
									thisUserBE.firstName,
									thisUserBE.password,
									thisUserBE.email
								)
							}
						}
						navController.navigate(Screen.UserDetailView.name, createdUser.userId)
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
		}

		DetailViewContainer("User Details") {
			DetailColumnTemplate {
				DetailColumnTemplateLeft("User Id", modifier = Modifier.weight(1F)) {
					Text(
						text = thisUserBE.userId.toString()
					)
				}

				if(navController.navigateParam != -1) {
					DetailColumnTemplateRight("Manager", modifier = Modifier.weight(1F)) {
						Text(
							text = thisUserBE.managerName
						)
					}
				} else {
					TextField(
						value = managerId.value,
						onValueChange = {
							managerId.value = it
						},
						readOnly = !isEditMode.value,
						colors = getTextfieldBackgroundColor(isEditMode.value),
						label = {
							Text("Manager Id")
						},
						modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
					)
				}
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
			}

			DetailColumnTemplate {
				TextField(
					value = email.value,
					onValueChange = {
						email.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Email")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)

				TextField(
					value = userType.value,
					onValueChange = {
						userType.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("User Type")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}

			DetailColumnTemplate {
				DetailColumnTemplateLeft("Created Date", modifier = Modifier.weight(1F)) {
					Text(
						text = thisUserBE.createdDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
					)
				}

				TextField(
					value = password.value,
					onValueChange = {
						password.value = it
					},
					readOnly = !isEditMode.value,
					colors = getTextfieldBackgroundColor(isEditMode.value),
					label = {
						Text("Password")
					},
					modifier = Modifier.wrapContentWidth(Alignment.Start).weight(1F)
				)
			}
		}
	}
}