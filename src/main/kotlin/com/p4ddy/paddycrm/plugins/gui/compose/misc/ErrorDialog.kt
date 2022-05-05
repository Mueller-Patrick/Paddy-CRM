package com.p4ddy.paddycrm.plugins.gui.compose.misc

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DialogDemo(showDialog: Boolean, message: String, setShowDialog: (Boolean) -> Unit) {
	if (showDialog) {
		AlertDialog(
			onDismissRequest = {
			},
			title = {
				Text("Error")
			},
			confirmButton = {
				Button(
					onClick = {
						// Change the state to close the dialog
						setShowDialog(false)
					},
				) {
					Text("Ok")
				}
			},
			text = {
				Text(message)
			}
		)
	}
}
