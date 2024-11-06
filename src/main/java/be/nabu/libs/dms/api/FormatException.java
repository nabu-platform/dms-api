/*
* Copyright (C) 2015 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package be.nabu.libs.dms.api;

public class FormatException extends Exception {

	private static final long serialVersionUID = 2966156386002796085L;

	public FormatException() {
		super();
	}

	public FormatException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FormatException(String arg0) {
		super(arg0);
	}

	public FormatException(Throwable arg0) {
		super(arg0);
	}
}
