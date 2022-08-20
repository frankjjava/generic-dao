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
package otc.framework.generic.dao.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class DaoException.
 */
public class DaoException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1871120411165458375L;
	
	/** The error code. */
	protected String errorCode;

	/**
	 * Instantiates a new dao exception.
	 *
	 * @param errorCode the error code
	 */
	public DaoException(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new dao exception.
	 *
	 * @param errorCode the error code
	 * @param msg the msg
	 */
	public DaoException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new dao exception.
	 *
	 * @param cause the cause
	 */
	public DaoException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new dao exception.
	 *
	 * @param errorCode the error code
	 * @param cause the cause
	 */
	public DaoException(String errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	/**
	 * Instantiates a new dao exception.
	 *
	 * @param errorCode the error code
	 * @param msg the msg
	 * @param cause the cause
	 */
	public DaoException(String errorCode, String msg, Throwable cause) {
		super(msg, cause);
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}
}
