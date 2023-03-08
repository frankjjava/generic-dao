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
public class GenericDaoValidationException extends RuntimeException {
	public GenericDaoValidationException() {
	}

	public GenericDaoValidationException(String message) {
		super(message);
	}

	public GenericDaoValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenericDaoValidationException(Throwable cause) {
		super(cause);
	}

	public GenericDaoValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
