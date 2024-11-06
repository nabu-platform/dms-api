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
import java.util.List;
import java.util.Map;

import be.nabu.libs.vfs.api.File;

/**
 * A converter is initialized once and the convert function is called for each conversion
 */
public interface Converter {
	
	/**
	 * The scheme you can use in dxf to indicate an embedded object
	 */
	public static final String SCHEME_STREAM = "internal+stream";
	
	/**
	 * The scheme you can use in dxf to indicate a linked object
	 */
	public static final String SCHEME_LINK = "internal+link";
	
	/**
	 * The document exchange format is a standardized subset of html which can be interpreted by multiple parties
	 * This allows easy conversion between document formats
	 */
	public static final String DOCUMENT_EXCHANGE_FORMAT_CONTENT_TYPE = "application/vnd-nabu-dxf";
	
	public static final String TAB_SPACES = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	
	public void convert(DocumentManager documentManager, File file, OutputStream output, Map<String, String> properties) throws IOException, FormatException;
	
	/**
	 * You can convert a number of content types with the same code, e.g. png, gif, jpg,... to html
	 */
	public List<String> getContentTypes();
	
	/**
	 * The resulting converted document should be of a single type otherwise you can't know which type it is
	 */
	public String getOutputContentType();
	
	/**
	 * Whether or not the conversion is lossless, in other words it can be converted without losing any information contained in the original
	 */
	public boolean isLossless();
}
