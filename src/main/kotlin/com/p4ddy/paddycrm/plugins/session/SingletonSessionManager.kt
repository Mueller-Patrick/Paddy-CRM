package com.p4ddy.paddycrm.plugins.session

import com.p4ddy.paddycrm.application.session.SessionManager
import com.p4ddy.paddycrm.domain.user.User

class SingletonSessionManager : SessionManager {
	override fun setCurrentUser(user: User?) {
		SessionSingleton.user = user
	}

	override fun checkIfUserIsLoggedIn(): Boolean {
		return SessionSingleton.user != null
	}

	override fun getCurrentUser(): User {
		SessionSingleton.user ?: throw Exception("No user is currently logged in")

		return SessionSingleton.user!!
	}
}
