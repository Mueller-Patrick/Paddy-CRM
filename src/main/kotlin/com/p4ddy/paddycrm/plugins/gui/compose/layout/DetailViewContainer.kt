package com.p4ddy.paddycrm.plugins.gui.compose.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DetailViewContainer(title: String, content: @Composable () -> Unit) {
	Column(
		modifier = Modifier
			.padding(start = 80.dp, top = 10.dp)
			.fillMaxSize(),
		verticalArrangement = Arrangement.Top,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(title, fontWeight = FontWeight.Bold)

		Spacer(modifier = Modifier.height(20.dp))

		content()
	}
}
