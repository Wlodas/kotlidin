package pl.kotlidin

import java.util.logging.Logger

interface Loggable {
	val logger: Logger
		get() = Logger.getLogger(this.javaClass.name)
}