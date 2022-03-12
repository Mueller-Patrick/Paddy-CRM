package com.p4ddy.paddycrm.plugins.persistence.exposed.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object ContactTable: Table("contact") {
	val contactId: Column<Int> = integer("contact_id").autoIncrement()
	val accountId: Column<Int> = integer("account_id")
	val ownerId: Column<Int> = integer("owner_id")
	val salutation: Column<String> = text("salutation")
	val lastName: Column<String> = text("last_name")
	val firstName: Column<String> = text("first_name")
	val addressCountry: Column<String> = text("address_country")
	val addressCity: Column<String> = text("address_city")
	val addressZipCode: Column<String> = text("address_zip")
	val addressStreetAndNumber: Column<String> = text("address_street_and_number")
	val createdDate: Column<LocalDate> = date("created_date")

	override val primaryKey = PrimaryKey(contactId)
}
