package com.p4ddy.paddycrm.plugins.gui.compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(
	val label: String,
	val icon: ImageVector,
	val showInNavBar: Boolean
) {
	AccountDetailView("Account", Icons.Filled.AccountCircle, false),
	AccountListView("Accounts", Icons.Filled.AccountCircle, true),
	AccountCreateView("Account", Icons.Filled.AccountCircle, false),

	ContactDetailView("Contact", Icons.Filled.Face, false),
	ContactListView("Contacts", Icons.Filled.Face, true),
	ContactCreateView("Contact", Icons.Filled.Face, false),

	OpportunityDetailView("Opportunity", Icons.Filled.Check, false),
	OpportunityListView("Opportunities", Icons.Filled.Check, true),
	OpportunityCreateView("Opportunity", Icons.Filled.Check, false),

	UserDetailView("User", Icons.Filled.Person, false),
	UserListView("Users", Icons.Filled.Person, true),
	UserCreateView("User", Icons.Filled.Person, false),
}
