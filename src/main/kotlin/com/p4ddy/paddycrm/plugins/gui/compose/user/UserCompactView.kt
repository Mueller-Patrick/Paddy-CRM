package com.p4ddy.paddycrm.plugins.gui.compose.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.p4ddy.paddycrm.domain.user.User

@Composable
fun UserCompactView(user: User) {
	Column(
		modifier = Modifier.fillMaxSize()
	) {
		Text("Name: ${user.firstName} ${user.lastName}")
		Text("Email: ${user.email}")
	}
}
