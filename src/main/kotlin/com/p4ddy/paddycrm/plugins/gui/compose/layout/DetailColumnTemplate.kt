package com.p4ddy.paddycrm.plugins.gui.compose.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailColumnTemplate(content: @Composable () -> Unit) {
	Column(
		modifier = Modifier.fillMaxWidth()
	) {
		Row {
			Spacer(modifier = Modifier.width(10.dp))

			content()

			Spacer(modifier = Modifier.width(10.dp))
		}
	}
}

@Composable
fun DetailColumnTemplateLeft(title: String, modifier: Modifier, content: @Composable () -> Unit) {
	Box(
		modifier = modifier.wrapContentWidth(Alignment.Start)
	) {
		Column(
			modifier = Modifier.fillMaxWidth()
		) {
			Text(
				text = title,
				fontSize = 10.sp
			)

			content()
		}
	}
}

@Composable
fun DetailColumnTemplateRight(title: String, modifier: Modifier, content: @Composable () -> Unit) {
	Box(
		modifier = modifier.wrapContentWidth(Alignment.End)
	) {
		Column(
			modifier = Modifier.fillMaxWidth()
		) {
			Text(
				text = title,
				fontSize = 10.sp
			)

			content()
		}
	}
}
