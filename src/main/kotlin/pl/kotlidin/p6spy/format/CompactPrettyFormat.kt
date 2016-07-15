package pl.kotlidin.p6spy.format

import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl

class CompactPrettyFormat : MessageFormattingStrategy {
	private val formatter = BasicFormatterImpl()
	
	override fun formatMessage(connectionId: Int, now: String?, elapsed: Long, category: String?, prepared: String?, sql: String?): String? {
		return if(sql.isNullOrEmpty()) category else formatter.format(sql)
	}
}