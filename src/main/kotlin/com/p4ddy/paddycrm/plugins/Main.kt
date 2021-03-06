package com.p4ddy.paddycrm.plugins

import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.p4ddy.paddycrm.application.session.SessionManager
import com.p4ddy.paddycrm.plugins.gui.compose.misc.LoginScreen
import com.p4ddy.paddycrm.plugins.gui.compose.misc.MainView
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.AccountTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.ContactTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.OpportunityTable
import com.p4ddy.paddycrm.plugins.persistence.exposed.tables.UserTable
import com.p4ddy.paddycrm.plugins.session.SingletonSessionManager
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

/**
 * Entry point for the application
 */
fun main() = application {
	prepareDatabase()

	val sessionManager: SessionManager = SingletonSessionManager()

	Window(
		onCloseRequest = ::exitApplication,
		title = "Paddy CRM",
		icon = BitmapPainter(useResource("Paddy_CRM_Logo_Square.png", ::loadImageBitmap))
	) {
		if (!sessionManager.checkIfUserIsLoggedIn()) {
			LoginScreen()
		} else {
			MainView()
		}
	}
}

/**
 * Connects to SQLite DB and creates the required tables if they don't already exist
 */
private fun prepareDatabase() {
	Database.connect("jdbc:sqlite:database.sqlite", "org.sqlite.JDBC")

	TransactionManager.manager.defaultIsolationLevel =
		Connection.TRANSACTION_SERIALIZABLE

	transaction {
		SchemaUtils.create(UserTable, AccountTable, ContactTable, OpportunityTable)
	}
}
