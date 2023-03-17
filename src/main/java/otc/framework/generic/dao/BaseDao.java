/**
* Copyright (c) OTC Framework
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2020-06-08 
*
* This file is part of the "OTC Framework's generic-dao" library.
* 
*  The "generic-dao" is free library: you can redistribute it and/or modify
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

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import otc.framework.generic.dao.dto.TableMetaDataDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Interface BaseDao.
 */
public interface BaseDao {
	
	/** The nanos in one milli. */
	long NANOS_IN_ONE_MILLI = 1000000;

	String INSERT_INTO = "INSERT INTO ";
	String SELECT = "SELECT ";
	String FROM = " FROM ";
	String UPDATE = "UPDATE ";
	String DELETE = "DELETE ";
	String SET = " SET ";
	String VALUES = " VALUES (";
	String ON_CONFLICT = " ON CONFLICT (";
	String DO_UPDATE_SET = " DO UPDATE SET ";

	/** The column status. */
	String COLUMN_NAME_STATUS = "STATUS";
	
	/** The column created timestamp. */
	String COLUMN_CREATED_TIMESTAMP = "created_timestamp";
	
	/** The column created by. */
	String COLUMN_CREATED_BY = "created_by";
	
	/** The column updated timestamp. */
	String COLUMN_UPDATED_TIMESTAMP = "updated_timestamp";
	
	/** The column updated by. */
	String COLUMN_UPDATED_BY = "updated_by";

	/** The Constant SPACE. */
	String SPACE = " ";

	/** The Constant APOSTROPHE. */
	String QUESTION = "?";
	String APOSTROPHE = "'";

	/** The Constant EQUALS. */

	/** The Constant COMMA. */
	String COMMA = ", ";

	/** The Constant COLON. */
	String COLON = ":";

	/** The Constant SEMI_COLON. */
	String SEMI_COLON = ";";

	/** The Constant ASTERISK. */
	String ASTERISK = "*";

	/** The Constant OPEN_PARANTHESIS. */
	String OPEN_PARANTHESIS = " (";

	/** The Constant CLOSE_PARANTHESIS. */
	String CLOSE_PARANTHESIS = ") ";

	/** The tinyint. */
	String TINYINT = "TINYINT";

	/** The smallint. */
	String SMALLINT = "SMALLINT";

	/** The integer. */
	String INTEGER = "INTEGER";

	/** The bigint. */
	String BIGINT = "BIGINT";

	/** The real. */
	String REAL = "REAL";

	/** The double. */
	String DOUBLE = "DOUBLE";

	/** The date. */
	String DATE = "DATE";

	/** The timestamp. */
	String TIMESTAMP = "TIMESTAMP";

	/** The Constant WHERE. */
	String VARCHAR = "varchar";

	/** The Constant CREATE_TABLE. */
	String CREATE_TABLE = "CREATE TABLE";

	/** The Constant PRIMARY_KEY. */
	String PRIMARY_KEY = "PRIMARY KEY";

	/** The Constant SELECT. */

	/** The Constant NEXTVAL_FROM_DUAL. */
	String NEXTVAL_FROM_DUAL = ".NEXTVAL FROM DUAL";

	/**
	 * Checks if is table exists.
	 *
	 * @param tableName the table name
	 * @return true, if is table exists
	 */
	public boolean isTableExists(String tableName);

	/**
	 * Creates the table.
	 *
	 * @param tableMetaDataDto the table meta data dto
	 * @return true, if successful
	 */
	public boolean createTable(TableMetaDataDto tableMetaDataDto);

	/**
	 * Fetch next seq value.
	 *
	 * @param seqName the seq name
	 * @return the long
	 */
	public abstract long fetchNextSeqValue(String seqName);

	/**
	 * Execute insert.
	 *
	 * @param table the table
	 * @param params the params
	 * @return the int
	 */
	public abstract int executeInsert(String table, Map<String, Object> params);

    /**
	 * Execute insert and return key holder.
	 *
	 * @param table the table
	 * @param params the params
	 * @param generatedKeyName the generated key
	 * @return the key holder
	 */
	public KeyHolder executeInsertAndReturnKeyHolder(String table, Map<String, Object> params, String generatedKeyName);

	/**
	 * Execute insert and return key holder.
	 *
	 * @param table the table
	 * @param params the params
	 * @param generatedKeyNames the generated keys
	 * @return the key holder
	 */
	public KeyHolder executeInsertAndReturnKeyHolder(String table, Map<String, Object> params,
			Set<String> generatedKeyNames);

	/**
	 * Execute insert and return key.
	 *
	 * @param table the table
	 * @param params the params
	 * @param generatedKeyName the generated key
	 * @return the object
	 */
	public <T> T executeInsertAndReturnKey(String table, Map<String, Object> params, String generatedKeyName);

	/**
	 * Execute insert and return key.
	 *
	 * @param table the table
	 * @param params the params
	 * @param generatedKeyNames the generated keys
	 * @return the object
	 */
	public <T> T executeInsertAndReturnKey(String table, Map<String, Object> params, Set<String> generatedKeyNames);

	/**
	 * Execute batch insert.
	 *
	 * @param table the table
	 * @param params the params
	 * @return the int[]
	 */
	public abstract int[] executeBatchInsert(String table, Map<String, Object>[] params);

	/**
	 * Execute batch insert.
	 *
	 * @param schema the schema
	 * @param table the table
	 * @param params the params
	 * @return the int[]
	 */
	public abstract int[] executeBatchInsert(String schema, String table, Map<String, Object>[] params);

	/**
	 * Execute batch upsert.
	 *
	 * @param lstBatchSql the lst batch sql
	 * @return the int[]
	 */
	public abstract int[] executeBatchUpsert(List<String> lstBatchSql);

	/**
	 * Execute query.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param cls the cls
	 * @return the t
	 */
	public abstract <T> T executeQuery(String query, Class<T> cls);

	/**
	 * Execute query.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param rowMapper the row mapper
	 * @return the list
	 */
	public abstract <T> List<T> executeQuery(String query, RowMapper<T> rowMapper);

	/**
	 * Execute query.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param params the params
	 * @param rowMapper the row mapper
	 * @return the list
	 */
	public abstract <T> List<T> executeQuery(String query, Object[] params, RowMapper<T> rowMapper);

	/**
	 * Execute for object.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param params the params
	 * @param rowMapper the row mapper
	 * @return the t
	 */
	public abstract <T> T executeQueryForObject(String query, Object[] params, RowMapper<T> rowMapper);

	/**
	 * Execute query.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param rsExtractor the rs extractor
	 * @return the t
	 */
	public abstract <T> T executeQuery(String query, ResultSetExtractor<T> rsExtractor);

	/**
	 * Execute update.
	 *
	 * @param sql the sql
	 * @return the int
	 */
	public abstract int executeUpdate(String sql);

	/**
	 * Execute update.
	 *
	 * @param query the query
	 * @param params the params
	 * @return the int
	 */
	public abstract int executeUpdate(String query, Object[] params);

	/**
	 * Execute update.
	 *
	 * @param query the query
	 * @param pss the pss
	 * @return the int
	 */
	public abstract int executeUpdate(String query, PreparedStatementSetter pss);

	/**
	 * Execute update for named sql.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the int
	 */
	public abstract int executeUpdateForNamedSql(String sql, SqlParameterSource params);

	/**
	 * Execute batch update.
	 *
	 * @param query the query
	 * @param objArr the obj arr
	 * @return the int[]
	 */
	public abstract int[] executeBatchUpdate(String query, List<Object[]> objArr);

	/**
	 * Execute named batch update.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the int[]
	 */
	public abstract int[] executeNamedBatchUpdate(String sql, SqlParameterSource[] params);

    int[] executeNamedBatchUpdate(String sql, Map<String, Object>[] params);

    /**
	 * Execute named update.
	 *
	 * @param sql the sql
	 * @param paramMap the param map
	 * @return the int
	 */
	public abstract int executeNamedUpdate(String sql, Map<String, ?> paramMap);

    int executeDelete(String sql);

    int executeDelete(String query, Object[] params);

    int executeDelete(String query, PreparedStatementSetter pss);

    int executeDeleteForNamedSql(String sql, SqlParameterSource params);

    int executeNamedDelete(String sql, Map<String, ?> paramMap);

    /**
	 * Execute named query.
	 *
	 * @param <T> the generic type
	 * @param sql the sql
	 * @param paramMap the param map
	 * @param rse the rse
	 * @return the t
	 */
	public abstract <T> T executeNamedQuery(String sql, Map<String, Object> paramMap, ResultSetExtractor<T> rse);

	/**
	 * Execute named query.
	 *
	 * @param <T> the generic type
	 * @param sql the sql
	 * @param paramMap the param map
	 * @param rowMapper the row mapper
	 * @return the t
	 */
	public abstract <T> T executeNamedQuery(String sql, Map<String, Object> paramMap, RowMapper<T> rowMapper);

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
	public abstract <T> Object executeQueryForObject(String query, Object[] params, int[] types, Class<T> type);

	/**
	 * Execute named query for obj.
	 *
	 * @param <T> the generic type
	 * @param sql the sql
	 * @param paramMap the param map
	 * @param requiredType the required type
	 * @return the object
	 */
	public abstract <T> Object executeNamedQueryForObj(String sql, Map<String, ?> paramMap, Class<T> requiredType);

}
