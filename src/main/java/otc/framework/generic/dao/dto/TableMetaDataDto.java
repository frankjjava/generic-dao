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

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import otc.framework.generic.dao.GenericDaoConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class TableMetaDataDto.
 */
@Data
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
	 * The Class ColumnMetaDataDto.
	 */
	@Data
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

			VARCHAR(Types.VARCHAR), 
			BYTE(Types.TINYINT), 
			SHORT(Types.SMALLINT), 
			INT(Types.INTEGER), 
			LONG(Types.BIGINT),
			FLOAT(Types.REAL), 
			DOUBLE(Types.DOUBLE), 
			DATE(Types.DATE), 
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
		@Getter(AccessLevel.NONE)
		private TYPE type;
		
		/** The length. */
		private int length;
		
		/** The constraints. */
		private Set<CONSTRAINTS> constraints;

		/**
		 * Gets the type name.
		 *
		 * @return the type name
		 */
		public String getTypeName() {
			if (type == TYPE.VARCHAR) {
				return new StringBuilder(GenericDaoConstants.VARCHAR + GenericDaoConstants.CLOSE_PARANTHESIS)
						.append(length)
						.append(GenericDaoConstants.CLOSE_PARANTHESIS)
						.toString();
			} else if (type.equals(TYPE.BYTE)) {
				return GenericDaoConstants.TINYINT;
			} else if (type.equals(TYPE.SHORT)) {
				return GenericDaoConstants.SMALLINT;
			} else if (type.equals(TYPE.INT)) {
				return GenericDaoConstants.INTEGER;
			} else if (type.equals(TYPE.LONG)) {
				return GenericDaoConstants.BIGINT;
			} else if (type.equals(TYPE.FLOAT)) {
				return GenericDaoConstants.REAL;
			} else if (type.equals(TYPE.DOUBLE)) {
				return GenericDaoConstants.DOUBLE;
			} else if (type.equals(TYPE.DATE)) {
				return GenericDaoConstants.DATE;
			} else if (type.equals(TYPE.TIMESTAMP)) {
				return GenericDaoConstants.TIMESTAMP;
			}
			return null;
		}
	}
}
