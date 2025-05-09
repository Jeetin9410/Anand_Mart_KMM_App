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
) {
    companion object {
        fun empty() = UsersModel(
            address = Address.empty(),
            email = "",
            id = 0,
            name = Name.empty(),
            password = "",
            phone = "",
            username = "",
            v = 0
        )
    }
}

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
) {
    companion object {
        fun empty() = Address(
            city = "",
            geolocation = Geolocation.empty(),
            number = 0,
            street = "",
            zipcode = ""
            )
    }

    fun completeAddress() = "$number $street, $city, $zipcode"
}

@Serializable
data class Name(
    @SerialName("firstname")
    val firstname: String,
    @SerialName("lastname")
    val lastname: String
) {
    companion object {
        fun empty() = Name(
            firstname = "Test",
            lastname = "User"
        )
    }

    fun getFullName() = "$firstname $lastname"
}

@Serializable
data class Geolocation(
    @SerialName("lat")
    val lat: String,
    @SerialName("long")
    val long: String
) {
    companion object {
        fun empty() = Geolocation(
            lat = "0.0",
            long = "0.0"
        )
    }
}