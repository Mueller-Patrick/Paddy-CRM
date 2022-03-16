package com.p4ddy.paddycrm.plugins.gui.compose.misc

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
import androidx.compose.ui.unit.sp
import com.p4ddy.paddycrm.application.user.UserApplicationService
import com.p4ddy.paddycrm.application.user.UserSingleton
import com.p4ddy.paddycrm.domain.user.User
import com.p4ddy.paddycrm.plugins.persistence.exposed.user.UserExposedRepo

@Composable
fun LoginScreen() {
	val userRepo = UserExposedRepo()
	val userService = UserApplicationService(userRepo)

	var email = remember { mutableStateOf(TextFieldValue("")) }
	var password = remember { mutableStateOf(TextFieldValue("")) }
	var triggerLogin = remember { mutableStateOf(false) }

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = "Paddy CRM",
			fontSize = 20.sp
		)
		Text("Please Log in:")
		Spacer(modifier = Modifier.height(10.dp))
		TextField(
			value = email.value,
			onValueChange = {
				email.value = it
			},
			label = {
				Text("Email address")
			}
		)
		TextField(
			value = password.value,
			onValueChange = {
				password.value = it
			},
			label = {
				Text("Password")
			}
		)
		Button(onClick = {
			val user: User? = try {
				userService.findUserByEmail(email.value.text)
			} catch (e: Exception) {
				println(e.localizedMessage)
				null
			}

			val passwordIsValid: Boolean = try {
				userService.checkIfPasswordIsValid(user!!.userId, password.value.text)
			} catch (e: Exception) {
				println(e.localizedMessage)
				false
			}

			if (passwordIsValid) {
				UserSingleton.user = user
				triggerLogin.value = true
			}
		}) {
			Text("Sign in")
		}
		if (userService.findAllUsers().isEmpty()) {
			Button(onClick = {
				generateDemoData()
				triggerLogin.value = true
			}) {
				Text("Alternatively: Generate Demo Data")
			}
		}
	}

	if (triggerLogin.value) {
		MainView()
	}
}
