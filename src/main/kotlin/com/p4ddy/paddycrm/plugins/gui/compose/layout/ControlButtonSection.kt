package com.p4ddy.paddycrm.plugins.gui.compose.layout

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ControlButtonSection(content: @Composable () -> Unit) {
	Column(
		modifier = Modifier
			.padding(end = 10.dp, top = 10.dp)
			.fillMaxWidth(),
		verticalArrangement = Arrangement.Top,
		horizontalAlignment = Alignment.End
	) {
		Row {
			content()
		}
	}
}
