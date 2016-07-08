package pl.kotlidin

import com.vaadin.server.FontAwesome
import com.vaadin.server.FontIcon

enum class KotlidinIcon(fontIcon: FontIcon) : FontIcon by fontIcon {
	CLOSE(FontAwesome.CLOSE),
	COPY(FontAwesome.COPY),
	CREATE(FontAwesome.PLUS),
	DELETE(FontAwesome.TRASH),
	EDIT(FontAwesome.PENCIL),
	PERSON(FontAwesome.CHILD),
	SUBMIT(FontAwesome.CHECK)
}