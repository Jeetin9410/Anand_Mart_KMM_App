package org.example.project.domain.model
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class UsersModel(
    @SerialName("address")
    val address: Address,
    @SerialName("email")
    val email: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: Name,
    @SerialName("password")
    val password: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("username")
    val username: String,
    @SerialName("__v")
    val v: Int
)

@Serializable
data class Address(
    @SerialName("city")
    val city: String,
    @SerialName("geolocation")
    val geolocation: Geolocation,
    @SerialName("number")
    val number: Int,
    @SerialName("street")
    val street: String,
    @SerialName("zipcode")
    val zipcode: String
)

@Serializable
data class Name(
    @SerialName("firstname")
    val firstname: String,
    @SerialName("lastname")
    val lastname: String
)

@Serializable
data class Geolocation(
    @SerialName("lat")
    val lat: String,
    @SerialName("long")
    val long: String
)