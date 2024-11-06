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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.nabu.libs.datastore.api.WritableDatastore;
import be.nabu.libs.vfs.api.File;

/**
 * The document manager is responsible for keeping track of the converters and actually performing the conversions.
 * It can also introduce optimizations like caching.
 */
public interface DocumentManager {
	public Converter getConverter(String fromContentType, String toContentType);
	public boolean canConvert(String fromContentType, String toContentType);
	public void convert(File file, String toContentType, OutputStream output, Map<String, String> properties) throws IOException, FormatException;
	public WritableDatastore getDatastore(File file);
	public default List<Templater> getTemplaters() {
		return new ArrayList<Templater>();
	}
}
