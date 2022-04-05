package com.p4ddy.paddycrm.application.session

import com.p4ddy.paddycrm.domain.user.User

interface SessionManager {
	/**
	 * Set the currently logged in user for this session
	 *
	 * @param user The user to set as currently logged in
	 */
	fun setCurrentUser(user: User?)

	/**
	 * Get the currently logged in user for this session
	 *
	 * @return The current user
	 * @throws Exception when no user is logged in
	 */
	fun getCurrentUser(): User {
		throw Exception("No user is currently logged in")
	}

	/**
	 * Check, if there is currently a user logged in
	 *
	 * @return If a user is currently logged in
	 */
	fun checkIfUserIsLoggedIn(): Boolean {
		return false
	}
}
