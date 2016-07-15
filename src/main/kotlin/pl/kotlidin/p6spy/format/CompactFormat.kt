package pl.kotlidin.p6spy.format

import com.p6spy.engine.spy.appender.MessageFormattingStrategy

class CompactFormat : MessageFormattingStrategy {
	override fun formatMessage(connectionId: Int, now: String?, elapsed: Long, category: String?, prepared: String?, sql: String?): String? {
		return if(sql.isNullOrEmpty()) category else sql
	}
}