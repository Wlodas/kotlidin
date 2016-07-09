package pl.kotlidin

import org.hibernate.validator.constraints.NotBlank
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull

@Entity
class Person(
	@field:NotBlank
	@Column(nullable = false)
	var firstName: String? = null,
	
	@field:NotBlank
	@Column(nullable = false)
	var lastName: String? = null,
	
	@field:NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var gender: Gender? = null,
	
	@field:NotNull
	@Column(nullable = false)
	var birthDate: LocalDate? = null
) : AbstractEntity<Long>() {
	enum class Gender {
		MALE, FEMALE
	}
	
	fun copy(
		firstName: String? = this.firstName,
		lastName: String? = this.lastName,
		gender: Gender? = this.gender,
		birthDate: LocalDate? = this.birthDate
	): Person {
		return Person(firstName = firstName, lastName = lastName, gender = gender, birthDate = birthDate)
	}
}