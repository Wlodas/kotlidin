package pl.kotlidin

import java.util.*

inline fun <reified E : Enum<E>> setOfAll(): Set<E> = EnumSet.allOf(E::class.java)

inline fun <E : Enum<E>> setOf(e: E): Set<E> = kotlin.collections.setOf(e)

inline fun <E : Enum<E>> setOf(e1: E, e2: E): Set<E> = EnumSet.of(e1, e2)

inline fun <E : Enum<E>> setOf(e1: E, e2: E, e3: E): Set<E> = EnumSet.of(e1, e2, e3)

inline fun <E : Enum<E>> setOf(e1: E, e2: E, e3: E, e4: E): Set<E> = EnumSet.of(e1, e2, e3, e4)

inline fun <E : Enum<E>> setOf(e1: E, e2: E, e3: E, e4: E, e5: E): Set<E> = EnumSet.of(e1, e2, e3, e4, e5)

inline fun <E : Enum<E>> setOf(first: E, vararg rest: E): Set<E> = EnumSet.of(first, *rest)

fun <E : Enum<E>> Array<out E>.toSet(): Set<E> {
	return when (size) {
		0 -> emptySet()
		1 -> kotlin.collections.setOf(this[0])
		else -> toCollection(EnumSet.noneOf(this[0].declaringClass))
	}
}