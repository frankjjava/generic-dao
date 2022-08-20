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

// TODO: Auto-generated Javadoc
/**
 * The Interface DbCommonDao.
 */
public interface DbCommonDao {
	
	/** The nanos in one milli. */
	long NANOS_IN_ONE_MILLI = 1000000;
	
	/** The column status. */
	String COLUMN_STATUS = "STATUS";
	
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
}
