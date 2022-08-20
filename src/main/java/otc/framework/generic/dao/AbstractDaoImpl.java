/**
* Copyright (c) OTC Framework
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2020-06-08 
*
* This file is part of the "OTC Framework's generic-dao" library.
* 
*  The generic-dao is free library: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, version 3 of the License.
*
*  The generic-dao is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
*
*  A copy of the GNU General Public License is made available as 'License.md' file, 
*  along with generic-dao project.  If not, see <https://www.gnu.org/licenses/>.
*
*/
package otc.framework.generic.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;

import otc.framework.generic.dao.dto.TableMetaDataDto;
import otc.framework.generic.dao.dto.TableMetaDataDto.ColumnMetaDataDto;
import otc.framework.generic.dao.dto.TableMetaDataDto.ColumnMetaDataDto.CONSTRAINTS;
import otc.framework.generic.dao.exception.DaoException;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDaoImpl.
 */
public abstract class AbstractDaoImpl implements BaseDao {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDaoImpl.class);
	
	/**
	 * The Enum AND_OR.
	 */
	protected enum AND_OR {
		
		/** The and. */
		AND, 
		 /** The or. */
		 OR
	};

	/** The Constant simpleTypes. */
	protected static final List<Class<?>> simpleTypes = new ArrayList<>();

	/** The data source. */
	@Autowired
	private DataSource dataSource;
	
	/** The jdbc template. */
	protected JdbcTemplate jdbcTemplate;
	
	/** The named jdbc template. */
	protected NamedParameterJdbcTemplate namedJdbcTemplate;

	static {
		simpleTypes.add(String.class);
		simpleTypes.add(short.class);
		simpleTypes.add(Short.class);
		simpleTypes.add(int.class);
		simpleTypes.add(Integer.class);
		simpleTypes.add(long.class);
		simpleTypes.add(Long.class);
		simpleTypes.add(double.class);
		simpleTypes.add(Double.class);
		simpleTypes.add(java.sql.Date.class);
		simpleTypes.add(java.sql.Timestamp.class);
	}

	/**
	 * Instantiates a new abstract dao impl.
	 */
	protected AbstractDaoImpl() {
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		setJdbcTemplate(jdbcTemplate);
	}

	/**
	 * Sets the jdbc template.
	 *
	 * @param jdbcTemplate the new jdbc template
	 */
	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	/**
	 * Checks if is table exists.
	 *
	 * @param tableName the table name
	 * @return true, if is table exists
	 */
	@Override
	public boolean isTableExists(String tableName) {
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, null);
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	/**
	 * Creates the table.
	 *
	 * @param tableMetaDataDto the table meta data dto
	 * @return true, if successful
	 */
	@Override
	public boolean createTable(TableMetaDataDto tableMetaDataDto) {
		StringBuilder sql = null;
		for (ColumnMetaDataDto columnMetaDataDto : tableMetaDataDto.getColumns()) {
			if (sql == null) {
				sql = new StringBuilder(GenericDaoConstants.CREATE_TABLE).append(tableMetaDataDto.getTableName());
			} else {
				sql.append(GenericDaoConstants.COMMA);
			}
			sql.append(columnMetaDataDto.getColumnName())
				.append(GenericDaoConstants.SPACE)
				.append(columnMetaDataDto.getTypeName());
			Set<CONSTRAINTS> constraints = columnMetaDataDto.getConstraints();
			if (constraints != null && !constraints.isEmpty()) {
				for (CONSTRAINTS constraint : constraints) {
					if (constraint == CONSTRAINTS.PRIMARY_KEY) {
						sql.append(GenericDaoConstants.SPACE + GenericDaoConstants.PRIMARY_KEY);
					}
				}
			}
		}
		sql.append(GenericDaoConstants.SEMI_COLON);
		jdbcTemplate.execute(sql.toString());
		return true;
	}

	/**
	 * Fetch next seq value.
	 *
	 * @param seqName the seq name
	 * @return the long
	 */
	@Override
	public long fetchNextSeqValue(String seqName) {
		StringBuilder sql = new StringBuilder(GenericDaoConstants.SELECT)
					.append(seqName)
					.append(GenericDaoConstants.NEXTVAL_FROM_DUAL);
		return jdbcTemplate.queryForObject(sql.toString(), Long.class);
	}

	/**
	 * Execute insert.
	 *
	 * @param table the table
	 * @param params the params
	 * @return the int
	 */
	@Override
	public int executeInsert(String table, Map<String, Object> params) {
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params.keySet(), null);
		int count = jdbcInsert.execute(params);
		return count;
	}

	/**
	 * Execute insert and return key holder.
	 *
	 * @param table the table
	 * @param params the params
	 * @param generatedKey the generated key
	 * @return the key holder
	 */
	@Override
	public KeyHolder executeInsertAndReturnKeyHolder(String table, Map<String, Object> params, String generatedKey) {
		Set<String> generatedKeys = new HashSet<>();
		generatedKeys.add(generatedKey);
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params.keySet(), generatedKeys);
		KeyHolder keyHolder = jdbcInsert.executeAndReturnKeyHolder(params);
		return keyHolder;
	}

	/**
	 * Execute insert and return key holder.
	 *
	 * @param table the table
	 * @param params the params
	 * @param generatedKeys the generated keys
	 * @return the key holder
	 */
	@Override
	public KeyHolder executeInsertAndReturnKeyHolder(String table, Map<String, Object> params,
			Set<String> generatedKeys) {
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params.keySet(), generatedKeys);
		KeyHolder keyHolder = jdbcInsert.executeAndReturnKeyHolder(params);
		return keyHolder;
	}

	/**
	 * Execute insert and return key.
	 *
	 * @param table the table
	 * @param params the params
	 * @param generatedKey the generated key
	 * @return the object
	 */
	@Override
	public Object executeInsertAndReturnKey(String table, Map<String, Object> params, String generatedKey) {
		Set<String> generatedKeys = new HashSet<>();
		generatedKeys.add(generatedKey);
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params.keySet(), generatedKeys);
		Object object = jdbcInsert.executeAndReturnKey(params);
		return object;
	}

	/**
	 * Execute insert and return key.
	 *
	 * @param table the table
	 * @param params the params
	 * @param generatedKeys the generated keys
	 * @return the object
	 */
	@Override
	public Object executeInsertAndReturnKey(String table, Map<String, Object> params, Set<String> generatedKeys) {
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params.keySet(), generatedKeys);
		Object object = jdbcInsert.executeAndReturnKey(params);
		return object;
	}

	/**
	 * Execute batch insert.
	 *
	 * @param table the table
	 * @param params the params
	 * @return the int[]
	 */
	@Override
	public int[] executeBatchInsert(String table, Map<String, Object>[] params) {
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params, null);
		int[] count = jdbcInsert.executeBatch(params);
		return count;
	}

	/**
	 * Execute batch insert.
	 *
	 * @param schema the schema
	 * @param table the table
	 * @param params the params
	 * @return the int[]
	 */
	@Override
	public int[] executeBatchInsert(String schema, String table, Map<String, Object>[] params) {
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params, null);
		int[] count = jdbcInsert.executeBatch(params);
		return count;
	}

	/**
	 * Execute batch upsert.
	 *
	 * @param lstBatchSql the lst batch sql
	 * @return the int[]
	 */
	@Override
	public int[] executeBatchUpsert(List<String> lstBatchSql) {
		LOGGER.debug(lstBatchSql.toString());
		long startTime = System.nanoTime();
		String[] arrSql = lstBatchSql.toArray(new String[lstBatchSql.size()]);
		int[] arrCount = jdbcTemplate.batchUpdate(arrSql);
		LOGGER.debug("Executed 'executeBatchInsertOrUpdate(..)' in (millis) = "
				+ (System.nanoTime() - startTime) / NANOS_IN_ONE_MILLI);
		return arrCount;
	}

	/**
	 * Execute query.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param cls the cls
	 * @return the t
	 */
	@Override
	public <T> T executeQuery(String query, Class<T> cls) {
		return jdbcTemplate.queryForObject(query, cls);
	}

	/**
	 * Execute query.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param rowMapper the row mapper
	 * @return the list
	 */
	@Override
	public <T> List<T> executeQuery(String query, RowMapper<T> rowMapper) {
		return jdbcTemplate.query(query, rowMapper);
	}

	/**
	 * Execute query.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param params the params
	 * @param rowMapper the row mapper
	 * @return the list
	 */
	@Override
	public <T> List<T> executeQuery(String query, Object[] params, RowMapper<T> rowMapper) {
		return jdbcTemplate.query(query, params, rowMapper);
	}

	/**
	 * Execute for object.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param params the params
	 * @param rowMapper the row mapper
	 * @return the t
	 */
	@Override
	public <T> T executeForObject(String query, Object[] params, RowMapper<T> rowMapper) {
		return jdbcTemplate.queryForObject(query, params, rowMapper);
	}

	/**
	 * Execute query.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param rsExtractor the rs extractor
	 * @return the t
	 */
	@Override
	public <T> T executeQuery(String query, ResultSetExtractor<T> rsExtractor) {
		return jdbcTemplate.query(query, rsExtractor);
	}

	/**
	 * Execute update.
	 *
	 * @param sql the sql
	 * @return the int
	 */
	@Override
	public int executeUpdate(String sql) {
		return jdbcTemplate.update(sql);
	}

	/**
	 * Execute update.
	 *
	 * @param query the query
	 * @param params the params
	 * @return the int
	 */
	@Override
	public int executeUpdate(String query, Object[] params) {
		return jdbcTemplate.update(query, params);
	}

	/**
	 * Execute update.
	 *
	 * @param query the query
	 * @param pss the pss
	 * @return the int
	 */
	@Override
	public int executeUpdate(String query, PreparedStatementSetter pss) {
		return jdbcTemplate.update(query, pss);
	}

	/**
	 * Execute update for named sql.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the int
	 */
	/*
	 * @Override public int executeUpdate(String query, Object[] params, int[]
	 * types) { return jdbcTemplate.update(query, params, types); }
	 */
	@Override
	public int executeUpdateForNamedSql(String sql, SqlParameterSource params) {
		return jdbcTemplate.update(sql, params);
	}

	/**
	 * Execute batch update.
	 *
	 * @param query the query
	 * @param objArr the obj arr
	 * @return the int[]
	 */
	@Override
	public int[] executeBatchUpdate(String query, List<Object[]> objArr) {
		return jdbcTemplate.batchUpdate(query, objArr);
	}

	/**
	 * Execute named batch update.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the int[]
	 */
	@Override
	public int[] executeNamedBatchUpdate(String sql, SqlParameterSource[] params) {
		return namedJdbcTemplate.batchUpdate(sql, params);
	}

	/**
	 * Execute named update.
	 *
	 * @param sql the sql
	 * @param paramMap the param map
	 * @return the int
	 */
	@Override
	public int executeNamedUpdate(String sql, Map<String, ?> paramMap) {
		return namedJdbcTemplate.update(sql, paramMap);
	}

	/**
	 * Execute named query.
	 *
	 * @param <T> the generic type
	 * @param sql the sql
	 * @param paramMap the param map
	 * @param rse the rse
	 * @return the t
	 */
	@Override
	public <T> T executeNamedQuery(String sql, Map<String, Object> paramMap, ResultSetExtractor<T> rse) {
		sql = sanitizeWhereClauseForNullCriteria(sql, paramMap);
		return namedJdbcTemplate.query(sql, paramMap, rse);
	}

	/**
	 * Execute named query.
	 *
	 * @param <T> the generic type
	 * @param sql the sql
	 * @param paramMap the param map
	 * @param rowMapper the row mapper
	 * @return the t
	 */
	@Override
	public <T> T executeNamedQuery(String sql, Map<String, Object> paramMap, RowMapper<T> rowMapper) {
		return namedJdbcTemplate.queryForObject(sql, paramMap, rowMapper);
	}

	/**
	 * Execute for object.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param params the params
	 * @param types the types
	 * @param type the type
	 * @return the object
	 */
	@Override
	public <T> Object executeForObject(String query, Object[] params, int[] types, Class<T> type) {
		return jdbcTemplate.queryForObject(query, params, types, type);
	}

	/**
	 * Execute named query for obj.
	 *
	 * @param <T> the generic type
	 * @param sql the sql
	 * @param paramMap the param map
	 * @param requiredType the required type
	 * @return the object
	 */
	@Override
	public <T> Object executeNamedQueryForObj(String sql, Map<String, ?> paramMap, Class<T> requiredType) {
		return namedJdbcTemplate.queryForObject(sql, paramMap, requiredType);
	}

	/**
	 * Builds the where clause.
	 *
	 * @param whereClause the where clause
	 * @param colName the col name
	 * @param value the value
	 * @param logicalOperator the logical operator
	 * @param isUseNamedCriteria the is use named criteria
	 * @return the string builder
	 */
	protected StringBuilder buildWhereClause(StringBuilder whereClause, String colName, String value,
			AND_OR logicalOperator, boolean isUseNamedCriteria) {
		return buildWhereClause(whereClause, colName, value, logicalOperator, GenericDaoConstants.EQUALS, 
				isUseNamedCriteria);
	}

	/**
	 * Builds the where clause.
	 *
	 * @param whereClause the where clause
	 * @param colName the col name
	 * @param value the value
	 * @param logicalOperator the logical operator
	 * @param relationalOperator the relational operator
	 * @param isUseNamedCriteria the is use named criteria
	 * @return the string builder
	 */
	protected StringBuilder buildWhereClause(StringBuilder whereClause, String colName, String value,
			AND_OR logicalOperator, String relationalOperator, boolean isUseNamedCriteria) {
		return buildWhereClause(whereClause, colName, value, logicalOperator, relationalOperator, isUseNamedCriteria,
				null);
	}

	/**
	 * Builds the where clause.
	 *
	 * @param whereClause the where clause
	 * @param colName the col name
	 * @param value the value
	 * @param logicalOperator the logical operator
	 * @param relationalOperator the relational operator
	 * @param isUseNamedCriteria the is use named criteria
	 * @param ignoreCriteriaFields the ignore criteria fields
	 * @return the string builder
	 */
	protected StringBuilder buildWhereClause(StringBuilder whereClause, String colName, String value,
			AND_OR logicalOperator, String relationalOperator, boolean isUseNamedCriteria,
			List<String> ignoreCriteriaFields) {
		boolean isIgnoreFieldsContainsIgnoreCase = containsIgnoreCase(ignoreCriteriaFields, colName);
		if (whereClause == null) {
			whereClause = new StringBuilder(GenericDaoConstants.WHERE);
		} else if (!whereClause.toString().equals(GenericDaoConstants.WHERE)) {
			if (logicalOperator != null && (ignoreCriteriaFields == null || !isIgnoreFieldsContainsIgnoreCase)) {
				whereClause.append(GenericDaoConstants.SPACE)
					.append(logicalOperator.name())
					.append(GenericDaoConstants.SPACE);
			}
		}
		if (value != null && !isIgnoreFieldsContainsIgnoreCase) {
			value = value.trim();
			if (value.equalsIgnoreCase(GenericDaoConstants.IS_NULL.trim())) {
				whereClause.append(colName).append(GenericDaoConstants.IS_NULL);
			} else if (value.equalsIgnoreCase(GenericDaoConstants.ASTERISK) || 
					GenericDaoConstants.IS_NOT_NULL.trim().equals(value)) {
				whereClause.append(colName).append(GenericDaoConstants.IS_NOT_NULL);
			} else {
				whereClause.append(colName)
					.append(relationalOperator)
					.append(GenericDaoConstants.APOSTROPHE)
					.append(value)
					.append(GenericDaoConstants.APOSTROPHE);
			}
		} else if (isUseNamedCriteria) {
			whereClause.append(colName)
				.append(relationalOperator)
				.append(GenericDaoConstants.COLON)
				.append(colName);
		} else if (ignoreCriteriaFields == null || !isIgnoreFieldsContainsIgnoreCase) {
			whereClause.append(colName).append(GenericDaoConstants.IS_NULL);
		}
		return whereClause;
	}

	/**
	 * Builds the where between clause.
	 *
	 * @param whereClause the where clause
	 * @param logicalOperator the logical operator
	 * @param columnName the column name
	 * @param beginValue the begin value
	 * @param endValue the end value
	 * @return the string builder
	 */
	protected StringBuilder buildWhereBetweenClause(StringBuilder whereClause, AND_OR logicalOperator,
			String columnName, String beginValue, String endValue) {
		if (beginValue == null || endValue == null) {
			throw new DaoException("", "Invalid Begin-value and / or End-value!");
		}
		if (whereClause == null) {
			whereClause = new StringBuilder(GenericDaoConstants.WHERE);
		} else if (!whereClause.toString().equals(GenericDaoConstants.WHERE)) {
			if (logicalOperator != null) {
				whereClause.append(GenericDaoConstants.SPACE)
					.append(logicalOperator.name())
					.append(GenericDaoConstants.SPACE);
			}
		}
		whereClause.append(columnName)
			.append(GenericDaoConstants.BETWEEN)
			.append(beginValue)
			.append(GenericDaoConstants.APOSTROPHE + GenericDaoConstants.SPACE)
			.append(AND_OR.AND)
			.append(GenericDaoConstants.SPACE + GenericDaoConstants.APOSTROPHE)
			.append(endValue).append(GenericDaoConstants.APOSTROPHE);
		return whereClause;
	}

	/**
	 * Builds the set clause.
	 *
	 * @param setClause the set clause
	 * @param colName the col name
	 * @return the string builder
	 */
	protected StringBuilder buildSetClause(StringBuilder setClause, String colName) {
		return buildSetClause(setClause, colName, null);
	}

	/**
	 * Builds the set clause.
	 *
	 * @param setClause the set clause
	 * @param colName the col name
	 * @param value the value
	 * @return the string builder
	 */
	protected StringBuilder buildSetClause(StringBuilder setClause, String colName, String value) {
		if (colName != null) {
			if (setClause == null) {
				setClause = new StringBuilder(GenericDaoConstants.SET);
			} else {
				setClause.append(GenericDaoConstants.COMMA + GenericDaoConstants.SPACE);
			}
			if (value != null) {
				setClause.append(colName)
					.append(GenericDaoConstants.EQUALS + GenericDaoConstants.APOSTROPHE)
					.append(value)
					.append(GenericDaoConstants.APOSTROPHE);
			} else {
				setClause.append(colName)
					.append(GenericDaoConstants.EQUALS + GenericDaoConstants.COLON)
					.append(colName);
			}
		}
		return setClause;
	}

	/**
	 * Builds the in clause.
	 *
	 * @param lstValues the lst values
	 * @return the string
	 */
	protected String buildInClause(List<String> lstValues) {
		String csvSqlString = buildCsvSqlString(lstValues);
		if (csvSqlString == null || csvSqlString.isEmpty()) {
			return null;
		}
		csvSqlString = new StringBuilder(GenericDaoConstants.IN + GenericDaoConstants.OPEN_PARANTHESIS)
				.append(csvSqlString)
				.append(GenericDaoConstants.CLOSE_PARANTHESIS)
				.toString();
		return csvSqlString;
	}

	/**
	 * Builds the csv sql string.
	 *
	 * @param lstValues the lst values
	 * @return the string
	 */
	protected String buildCsvSqlString(List<String> lstValues) {
		StringBuilder values = null;
		for (Object value : lstValues) {
			if (values == null) {
				values = new StringBuilder();
			} else {
				values.append(GenericDaoConstants.COMMA);
			}
			values.append(GenericDaoConstants.APOSTROPHE)
				.append(value)
				.append(GenericDaoConstants.APOSTROPHE);
		}
		return values.toString();
	}

	/**
	 * Creates the simple types params map.
	 *
	 * @param obj the obj
	 * @return the map
	 */
	protected Map<String, Object> createSimpleTypesParamsMap(Object obj) {
		Map<String, Object> paramsMap = null;
		Class<? extends Object> cls = obj.getClass();
		for (Field fld : cls.getDeclaredFields()) {
			Object val = retrieveValue(fld, obj);
			if (paramsMap == null) {
				paramsMap = new HashMap<>();
			}
			paramsMap.put(fld.getName(), val);
		}
		return paramsMap;
	}

	/**
	 * Gets the current date.
	 *
	 * @return the current date
	 */
	protected static java.sql.Date getCurrentDate() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	/**
	 * Gets the current timestamp.
	 *
	 * @return the current timestamp
	 */
	protected static java.sql.Timestamp getCurrentTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	/**
	 * Creates the is exists result set extractor.
	 *
	 * @return the result set extractor
	 */
	protected ResultSetExtractor<Boolean> createIsExistsResultSetExtractor() {
		return new ResultSetExtractor<Boolean>() {
			@Override
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
				try {
					return rs.next();
				} catch (SQLException e) {
					LOGGER.warn(e.toString());
					return false;
				}
			}
		};
	}

	/**
	 * Creates the result set extractor for object.
	 *
	 * @return the result set extractor
	 */
	protected ResultSetExtractor<Object> createResultSetExtractorForObject() {
		return new ResultSetExtractor<Object>() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Object object = null;
				try {
					while (rs.next()) {
						object = rs.getObject(1);
					}
					return object;
				} catch (DataAccessException e) {
					LOGGER.warn(e.toString());
					return null;
				}
			}
		};
	}

	/**
	 * Retrieve value.
	 *
	 * @param fld the fld
	 * @param obj the obj
	 * @return the object
	 */
	private Object retrieveValue(Field fld, Object obj) {
		Class<?> type = fld.getType();
		if (!simpleTypes.contains(type)) {
			return null;
		}
		Object value;
		try {
			value = fld.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new DaoException(e);
		}
		return value;
	}

	/**
	 * Sanitize where clause for null criteria.
	 *
	 * @param sql the sql
	 * @param paramasMap the paramas map
	 * @return the string
	 */
	private String sanitizeWhereClauseForNullCriteria(String sql, Map<String, Object> paramasMap) {
		String tempSql = sql.toLowerCase();
		if (!tempSql.contains(GenericDaoConstants.WHERE)) {
			return sql;
		}
		int idx = tempSql.indexOf(GenericDaoConstants.WHERE) + 7;
		String selectClause = sql.substring(0, idx);
		String whereClause = sql.substring(idx).trim();
		if (!whereClause.contains(GenericDaoConstants.COLON)) {
			return sql;
		}
		idx = whereClause.indexOf(GenericDaoConstants.COLON);
		while (idx >= 0) {
			String namedParam = null;
			if (whereClause.indexOf(GenericDaoConstants.SPACE, idx + 2) > 0) {
				namedParam = whereClause.substring(idx + 1, whereClause.indexOf(GenericDaoConstants.SPACE, idx + 2));
			} else {
				namedParam = whereClause.substring(idx + 1);
			}
			String leadSql = whereClause.substring(0, idx).trim();
			if (paramasMap.get(namedParam) == null) {
				if (leadSql.endsWith(GenericDaoConstants.EQUALS)) {
					leadSql = leadSql.substring(0, leadSql.length() - 2).concat(GenericDaoConstants.IS);
				}
			}
			whereClause = leadSql.concat(whereClause.substring(idx));
			idx = whereClause.indexOf(GenericDaoConstants.COLON, idx + namedParam.length());
		}
		return selectClause.concat(whereClause);
	}

	/**
	 * Creates the simple jdbc insert.
	 *
	 * @param schema the schema
	 * @param table the table
	 * @param params the params
	 * @param generatedKeys the generated keys
	 * @return the simple jdbc insert
	 */
	private SimpleJdbcInsert createSimpleJdbcInsert(String schema, String table, Set<String> params,
			Set<String> generatedKeys) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(table);
		if (schema != null) {
			jdbcInsert = jdbcInsert.withSchemaName(schema);
		}
		if (generatedKeys != null && !generatedKeys.isEmpty()) {
			jdbcInsert = jdbcInsert.usingGeneratedKeyColumns(generatedKeys.toArray(new String[generatedKeys.size()]));
		}
		String[] columnNames = params.toArray(new String[params.size()]);
		jdbcInsert = jdbcInsert.usingColumns(columnNames);
		return jdbcInsert;
	}

	/**
	 * Creates the simple jdbc insert.
	 *
	 * @param schema the schema
	 * @param table the table
	 * @param arrParams the arr params
	 * @param generatedKeys the generated keys
	 * @return the simple jdbc insert
	 */
	private SimpleJdbcInsert createSimpleJdbcInsert(String schema, String table, Map<String, Object>[] arrParams,
			Set<String> generatedKeys) {
		Set<String> columnNames = new HashSet<>();
		for (Map<String, Object> params : arrParams) {
			for (String columnName : params.keySet()) {
				if (!columnNames.contains(columnName)) {
					columnNames.add(columnName);
				}
			}
		}
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(schema, table, columnNames, generatedKeys);
		return jdbcInsert;
	}

	/**
	 * Contains ignore case.
	 *
	 * @param values the values
	 * @param searchStr the search str
	 * @return true, if successful
	 */
	private boolean containsIgnoreCase(List<String> values, String searchStr) {
		if (values == null && searchStr == null) {
			return true;
		}
		if (values == null || values.isEmpty() || searchStr == null) {
			return false;
		}
		for (String value : values) {
			if (value.equalsIgnoreCase(searchStr)) {
				return true;
			}
		}
		return false;
	}
}