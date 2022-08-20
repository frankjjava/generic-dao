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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;

import otc.framework.generic.dao.dto.TableMetaDataDto;

// TODO: Auto-generated Javadoc
/**
 * The Interface BaseDao.
 */
public interface BaseDao {
	
	/** The nanos in one milli. */
	long NANOS_IN_ONE_MILLI = 1000000;
	
	/** The column status. */
	String COLUMN_NAME_STATUS = "STATUS";
	
	/** The column remote address. */
	String COLUMN_REMOTE_ADDRESS = "remote_address";
	
	/** The column remote host. */
	String COLUMN_REMOTE_HOST = "remote_host";
	
	/** The column created timestamp. */
	String COLUMN_CREATED_TIMESTAMP = "created_timestamp";
	
	/** The column created by. */
	String COLUMN_CREATED_BY = "CREATED_BY";
	
	/** The column updated timestamp. */
	String COLUMN_UPDATED_TIMESTAMP = "updated_timestamp";
	
	/** The column updated by. */
	String COLUMN_UPDATED_BY = "UPDATED_BY";
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
	 * @param generatedKey the generated key
	 * @return the key holder
	 */
	public KeyHolder executeInsertAndReturnKeyHolder(String table, Map<String, Object> params, String generatedKey);

	/**
	 * Execute insert and return key holder.
	 *
	 * @param table the table
	 * @param params the params
	 * @param generatedKeys the generated keys
	 * @return the key holder
	 */
	public KeyHolder executeInsertAndReturnKeyHolder(String table, Map<String, Object> params,
			Set<String> generatedKeys);

	/**
	 * Execute insert and return key.
	 *
	 * @param table the table
	 * @param params the params
	 * @param generatedKey the generated key
	 * @return the object
	 */
	public Object executeInsertAndReturnKey(String table, Map<String, Object> params, String generatedKey);

	/**
	 * Execute insert and return key.
	 *
	 * @param table the table
	 * @param params the params
	 * @param generatedKeys the generated keys
	 * @return the object
	 */
	public Object executeInsertAndReturnKey(String table, Map<String, Object> params, Set<String> generatedKeys);

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
	public abstract <T> T executeForObject(String query, Object[] params, RowMapper<T> rowMapper);

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

	/**
	 * Execute named update.
	 *
	 * @param sql the sql
	 * @param paramMap the param map
	 * @return the int
	 */
	public abstract int executeNamedUpdate(String sql, Map<String, ?> paramMap);

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
	public abstract <T> Object executeForObject(String query, Object[] params, int[] types, Class<T> type);

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

/**
 * Sets the jdbc template.
 *
 * @param jdbcTemplate the new jdbc template
 */
//	public abstract int executeUpdate(String query, Object[] params, int[] types);
	void setJdbcTemplate(JdbcTemplate jdbcTemplate);
}
