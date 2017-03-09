package pl.kotlidin

import com.vaadin.server.Resource
import com.vaadin.ui.Button
import com.vaadin.ui.Component
import com.vaadin.ui.ComponentContainer

inline fun button(caption: String?, icon: Resource, crossinline listener: (Button.ClickEvent) -> Unit): Button {
	val button = Button(caption, icon)
	button.addClickListener { listener(it) }
	return button
}

inline operator fun ComponentContainer.plusAssign(component: Component) = this.addComponent(component)

inline operator fun ComponentContainer.minusAssign(component: Component) = this.removeComponent(component)