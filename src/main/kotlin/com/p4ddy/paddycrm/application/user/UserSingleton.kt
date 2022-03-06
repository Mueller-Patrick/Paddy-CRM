package com.p4ddy.paddycrm.application.user

import com.p4ddy.paddycrm.domain.user.User

/**
 * https://www.reddit.com/r/ProgrammerHumor/comments/t1p6nh/and_after_a_break_you_dont_remember_anything/
 *
 * This singleton is used to store the currently logged in user so we can access it anywhere in the application
 */
object UserSingleton {
	var user: User? = null
}
