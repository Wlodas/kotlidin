package pl.kotlidin

import com.vaadin.data.util.BeanItemContainer
import com.vaadin.server.Resource
import com.vaadin.ui.AbstractOrderedLayout
import com.vaadin.ui.Button
import com.vaadin.ui.Component
import com.vaadin.ui.ComponentContainer

inline fun Button(caption: String?, icon: Resource, crossinline listener: (Button.ClickEvent) -> Unit): Button {
	val button = Button(caption, icon)
	button.addClickListener { listener(it) }
	return button
}

inline operator fun ComponentContainer.plusAssign(component: Component) = this.addComponent(component)

inline operator fun ComponentContainer.minusAssign(component: Component) = this.removeComponent(component)

class ComponentWithExpandRatio(val component: Component, val expandRatio: Float)

inline infix fun Component.withExpandRatio(expandRatio: Float) = ComponentWithExpandRatio(this, expandRatio)

inline operator fun AbstractOrderedLayout.plusAssign(wrapper: ComponentWithExpandRatio) {
	this.addComponent(wrapper.component)
	this.setExpandRatio(wrapper.component, wrapper.expandRatio)
}

inline fun <reified T : Any> BeanItemContainer(): BeanItemContainer<T> = BeanItemContainer(T::class.java)