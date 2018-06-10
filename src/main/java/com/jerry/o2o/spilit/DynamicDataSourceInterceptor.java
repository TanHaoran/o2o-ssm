package com.jerry.o2o.spilit;

import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.extern.slf4j.Slf4j;

// 增删改的操作都会封装到update中
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }) })
@Slf4j
public class DynamicDataSourceInterceptor implements Interceptor {

	private static final String REGET = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 记录是否是事务的
		boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
		Object[] objects = invocation.getArgs();
		MappedStatement statement = (MappedStatement) objects[0];
		String lookupKey = DynamicDataSourceHolder.DB_MASTER;
		if (!synchronizationActive) {
			// 读方法
			if (statement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
				// 如果selectKey为自增id查询主键(SELECT LAST_INSERT_ID()方法)，就使用主库
				if (statement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
					lookupKey = DynamicDataSourceHolder.DB_MASTER;
				} else {
					BoundSql boundSql = statement.getSqlSource().getBoundSql(objects[1]);
					String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("\\t\\n\\r", " ");
					if (sql.matches(REGET)) {
						lookupKey = DynamicDataSourceHolder.DB_MASTER;
					} else {
						lookupKey = DynamicDataSourceHolder.DB_SLAVE;
					}
				}
			}
		} else {
			// 事务的操作一般都是写的，使用主库
			lookupKey = DynamicDataSourceHolder.DB_MASTER;
		}
		log.debug("设置方法[{}] use [{}] Strategy, SqlCommandType [{}]", statement.getId(), lookupKey,
				statement.getSqlCommandType().name());
		DynamicDataSourceHolder.setDbType(lookupKey);
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		// Executor是用来支持数据库增删改查操作的
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
