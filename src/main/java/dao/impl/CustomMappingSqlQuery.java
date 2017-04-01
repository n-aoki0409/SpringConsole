package dao.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

public abstract class CustomMappingSqlQuery<T> extends MappingSqlQuery<T> {

	public CustomMappingSqlQuery<T> init(DataSource ds, String sql, SqlParameter... params) {
		super.setDataSource(ds);
		super.setSql(sql);
		for (SqlParameter param : params) {
			super.declareParameter(param);
		}
		compile();

		return this;
	}
}
