package com.p4ddy.paddycrm.plugins.persistence.exposed.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object UserTable: Table("user") {
	val userId: Column<Int> = integer("user_id").autoIncrement()
	val managerId: Column<Int> = integer("manager_id").references(userId)
	val lastName: Column<String> = text("last_name")
	val firstName: Column<String> = text("first_name")
	val password: Column<String> = text("password")
	val email: Column<String> = text("email").uniqueIndex()
	val userType: Column<String> = text("user_type")
	val createdDate: Column<LocalDate> = date("created_date")

	override val primaryKey = PrimaryKey(userId)
}
