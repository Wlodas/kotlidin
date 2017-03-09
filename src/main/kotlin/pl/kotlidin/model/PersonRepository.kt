package pl.kotlidin.model

import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, Long>