package com.jerry.o2o.spilit;

import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DynamicDataSourceInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 记录是否是事务的
		boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
		if (!synchronizationActive) {
			Object[] objects = invocation.getArgs();
			MappedStatement statement = (MappedStatement) objects[0];
			String lookupKey;
			// 读方法
			if (statement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
				// 如果selectKey为自增id查询主键(SELECT LAST_INSERT_ID()方法)，就使用主库
				if (statement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
					lookupKey = DynamicDataSourceHolder.DB_MASTER;
				} else {
					BoundSql boundSql = statement.getSqlSource().getBoundSql(objects[1]);
					String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("\\t\\n\\r", " ");
				}
			}
		}
		return null;
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
