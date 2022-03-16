package com.p4ddy.paddycrm.plugins.persistence.exposed.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object AccountTable : Table("account") {
	val accountId: Column<Int> = integer("account_id").autoIncrement()
	val name: Column<String> = text("name")
	val ownerId: Column<Int> = integer("owner_id").references(
		UserTable.userId,
		onDelete = ReferenceOption.CASCADE,
		onUpdate = ReferenceOption.CASCADE
	)
	val billingAddressCountry: Column<String> = text("billing_address_country")
	val billingAddressCity: Column<String> = text("billing_address_city")
	val billingAddressZipCode: Column<String> = text("billing_address_zip")
	val billingAddressStreetAndNumber: Column<String> = text("billing_address_street_and_number")
	val shippingAddressCountry: Column<String> = text("shipping_address_country")
	val shippingAddressCity: Column<String> = text("shipping_address_city")
	val shippingAddressZipCode: Column<String> = text("shipping_address_zip")
	val shippingAddressStreetAndNumber: Column<String> = text("shipping_address_street_and_number")
	val createdDate: Column<LocalDate> = date("created_date")

	override val primaryKey = PrimaryKey(accountId)
}
