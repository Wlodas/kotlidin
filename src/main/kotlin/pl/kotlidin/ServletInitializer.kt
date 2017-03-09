package com.example

import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer
import pl.kotlidin.DemoApplication

class ServletInitializer : SpringBootServletInitializer() {
	override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
		return application.sources(DemoApplication::class.java)
	}
}