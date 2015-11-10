package be.nabu.libs.dms.api;

import java.util.Date;

import be.nabu.libs.vfs.api.File;

public interface DocumentCacheManager {
	/**
	 * Returns null if no cached value exists
	 */
	public byte [] getCached(File file, String contentType);
	public Date getAge(File file, String contentType);
	public void setCached(File file, String contentType, byte [] content);
}
