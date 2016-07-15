package pl.kotlidin.p6spy.format

import com.p6spy.engine.spy.appender.MessageFormattingStrategy

class MultiLineFormat : MessageFormattingStrategy {
	override fun formatMessage(connectionId: Int, now: String?, elapsed: Long, category: String?, prepared: String?, sql: String?): String {
		return when(sql.isNullOrEmpty()) {
			false -> "took $elapsed ms | $category | connection $connectionId\n$sql"
			else -> "took $elapsed ms | $category | connection $connectionId"
		}
	}
}