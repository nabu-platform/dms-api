package be.nabu.libs.dms.api;

import java.io.IOException;
import java.io.OutputStream;
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
}
