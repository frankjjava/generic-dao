/**
* Copyright (c) OTC Framework
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2020-06-08 
*
* This file is part of the "OTC Framework's generic-dao".
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
package otc.framework.generic.dao.dto;

import java.sql.Types;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class TableMetaDataDto.
 */
public class TableMetaDataDto {
	
	/**
	 * The Enum DIALECT.
	 */
	public enum DIALECT {
		
		/** The oracle. */
		ORACLE, 
 /** The ms sql. */
 MS_SQL, 
 /** The mysql. */
 MYSQL, 
 /** The postgres. */
 POSTGRES
	};

	/** The database name. */
	private String databaseName;
	
	/** The table name. */
	private String tableName;
	
	/** The columns. */
	private Set<ColumnMetaDataDto> columns;
	
	/** The dialect. */
	private DIALECT dialect;

	/**
	 * Gets the database name.
	 *
	 * @return the database name
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * Sets the database name.
	 *
	 * @param databaseName the new database name
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * Gets the table name.
	 *
	 * @return the table name
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Sets the table name.
	 *
	 * @param tableName the new table name
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public Set<ColumnMetaDataDto> getColumns() {
		return columns;
	}

	/**
	 * Sets the columns.
	 *
	 * @param columns the new columns
	 */
	public void setColumns(Set<ColumnMetaDataDto> columns) {
		this.columns = columns;
	}

	/**
	 * Gets the dialect.
	 *
	 * @return the dialect
	 */
	public DIALECT getDialect() {
		return dialect;
	}

	/**
	 * Sets the dialect.
	 *
	 * @param dialect the new dialect
	 */
	public void setDialect(DIALECT dialect) {
		this.dialect = dialect;
	}

	/**
	 * The Class ColumnMetaDataDto.
	 */
	public static final class ColumnMetaDataDto {
		
		/**
		 * The Enum CONSTRAINTS.
		 */
		public enum CONSTRAINTS {
			
			/** The primary key. */
			PRIMARY_KEY
		};

		/**
		 * The Enum TYPE.
		 */
		public enum TYPE {
			
			/** The varchar. */
			VARCHAR(Types.VARCHAR), 
 /** The byte. */
 BYTE(Types.TINYINT), 
 /** The short. */
 SHORT(Types.SMALLINT), 
 /** The int. */
 INT(Types.INTEGER), 
 /** The long. */
 LONG(Types.BIGINT),
			
			/** The float. */
			FLOAT(Types.REAL), 
 /** The double. */
 DOUBLE(Types.DOUBLE), 
 /** The date. */
 DATE(Types.DATE), 
 /** The timestamp. */
 TIMESTAMP(Types.TIMESTAMP);

			/** The value. */
			int value;

			/**
			 * Instantiates a new type.
			 *
			 * @param value the value
			 */
			private TYPE(int value) {
				this.value = value;
			}
		};

		/** The column name. */
		private String columnName;
		
		/** The type. */
		private TYPE type;
		
		/** The length. */
		private int length;
		
		/** The constraints. */
		private Set<CONSTRAINTS> constraints;

		/**
		 * Gets the column name.
		 *
		 * @return the column name
		 */
		public String getColumnName() {
			return columnName;
		}

		/**
		 * Sets the column name.
		 *
		 * @param columnName the new column name
		 */
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		/**
		 * Sets the type.
		 *
		 * @param type the new type
		 */
		public void setType(TYPE type) {
			this.type = type;
		}

		/**
		 * Gets the type name.
		 *
		 * @return the type name
		 */
		public String getTypeName() {
			if (type == TYPE.VARCHAR) {
				return new StringBuilder("varchar(").append(length).append(")").toString();
			} else if (type.equals(TYPE.BYTE)) {
				return "TINYINT";
			} else if (type.equals(TYPE.SHORT)) {
				return "SMALLINT";
			} else if (type.equals(TYPE.INT)) {
				return "INTEGER";
			} else if (type.equals(TYPE.LONG)) {
				return "BIGINT";
			} else if (type.equals(TYPE.FLOAT)) {
				return "REAL";
			} else if (type.equals(TYPE.DOUBLE)) {
				return "DOUBLE";
			} else if (type.equals(TYPE.DATE)) {
				return "DATE";
			} else if (type.equals(TYPE.TIMESTAMP)) {
				return "TIMESTAMP";
			}
			return null;
		}

		/**
		 * Gets the length.
		 *
		 * @return the length
		 */
		public int getLength() {
			return length;
		}

		/**
		 * Sets the length.
		 *
		 * @param length the new length
		 */
		public void setLength(int length) {
			this.length = length;
		}

		/**
		 * Gets the constraints.
		 *
		 * @return the constraints
		 */
		public Set<CONSTRAINTS> getConstraints() {
			return constraints;
		}

		/**
		 * Sets the constraints.
		 *
		 * @param constraints the new constraints
		 */
		public void setConstraints(Set<CONSTRAINTS> constraints) {
			this.constraints = constraints;
		}
	}
}
