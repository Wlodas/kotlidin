package pl.kotlidin

inline fun Person.copy(
	firstName: String? = this.firstName,
	lastName: String? = this.lastName
): Person {
	return Person(firstName = firstName, lastName = lastName)
}