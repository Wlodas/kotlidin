package pl.kotlidin.p6spy.logger

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.appender.StdoutLogger

class StatementOnlyStdoutLogger : StdoutLogger() {
	override fun logSQL(connectionId: Int, now: String?, elapsed: Long, category: Category?, prepared: String?, sql: String?) {
		if(!sql.isNullOrEmpty()) {
			super.logSQL(connectionId, now, elapsed, category, prepared, sql)
		}
	}
}