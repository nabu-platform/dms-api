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

import java.io.IOException;
import java.util.List;

import be.nabu.libs.vfs.api.File;

/**
 * A referencer implementation must be able to extract and manipulate references in a certain file
 */
public interface Referencer {
	/**
	 * The content types it supports
	 */
	public List<String> getContentTypes();
	/**
	 * The references for the given file
	 */
	public List<String> getReferences(File file) throws IOException;
	/**
	 * Update a reference within the given file
	 */
	public void updateReference(File file, String from, String to) throws IOException;
}
