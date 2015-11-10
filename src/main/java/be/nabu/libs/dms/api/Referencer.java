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
