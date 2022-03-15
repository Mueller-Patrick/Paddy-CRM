package com.p4ddy.paddycrm.plugins.gui.compose.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.p4ddy.paddycrm.domain.account.Account

@Composable
fun AccountCompactView(account: Account) {
	Column(
		modifier = Modifier.fillMaxSize()
	) {
		Text("Name: ${account.name}", modifier = Modifier.align(Alignment.Start))
		Text("Billing City: ${account.billingAddress.city}")
	}
}
