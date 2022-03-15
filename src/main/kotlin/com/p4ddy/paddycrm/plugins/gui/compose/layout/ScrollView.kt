package com.p4ddy.paddycrm.plugins.gui.compose.layout

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ScrollView(title: String, content: @Composable () -> Unit) {
	val stateVertical = rememberScrollState(0)

	Column(
		modifier = Modifier
			.padding(start = 80.dp, top = 50.dp)
			.verticalScroll(stateVertical)
			.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(title, fontWeight = FontWeight.Bold)

		Spacer(modifier = Modifier.height(20.dp))

		content()
	}

	VerticalScrollbar(
		modifier = Modifier
			.fillMaxHeight(),
		adapter = rememberScrollbarAdapter(stateVertical)
	)
}
