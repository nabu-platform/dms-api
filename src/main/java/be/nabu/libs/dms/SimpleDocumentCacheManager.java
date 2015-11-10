package be.nabu.libs.dms;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import be.nabu.libs.dms.api.DocumentCacheManager;
import be.nabu.libs.vfs.api.File;

/**
 * Thread-safe
 *
 */
public class SimpleDocumentCacheManager implements DocumentCacheManager {

	private static class CacheEntry {
		private byte [] content;
		private Date created;
		
		public CacheEntry(byte [] content) {
			this.content = content;
			this.created = new Date();
		}
		
		public byte[] getContent() {
			return content;
		}
		public Date getCreated() {
			return created;
		}
		
	}
	
	private Map<File, Map<String, CacheEntry>> cache = new HashMap<File, Map<String, CacheEntry>>();
	
	private Date lastChecked;
	
	/**
	 * Defaults to 30 minutes, note that a prune locks the cache!
	 * Set to 0 for no pruning
	 */
	private long checkInterval = 1000 * 60 * 30;
	/**
	 * Defaults to 60 minutes
	 */
	private long maxAge = 1000 * 60 * 60;
	
	@Override
	public byte[] getCached(File file, String contentType) {
		CacheEntry cacheEntry = getCacheEntry(file, contentType);
		return cacheEntry == null ? null : cacheEntry.getContent();
	}
	
	private CacheEntry getCacheEntry(File file, String contentType) {
		// defensive coding for concurrent access, removed ternary containsKey()
		Map<String, CacheEntry> fileEntry = cache.get(file);
		if (fileEntry == null)
			return null;
		// the file no longer exists, remove it from the cache
		// don't synchronize because conflicts should only result in non-existing cache which is acceptable
		if (!file.exists()) {
			cache.remove(file);
			return null;
		}
		CacheEntry cacheEntry = fileEntry.get(contentType);
		if (cacheEntry == null)
			return null;
		// if the cache is stale, remove it
		if (new Date().getTime() - cacheEntry.getCreated().getTime() > maxAge) {
			fileEntry.remove(contentType);
			return null;
		}
		// check that the file has not be modified since it was cached
		Date lastModified;
		try {
			lastModified = file.getLastModified();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		// the file has been modified since this entry was created
		if (cacheEntry.getCreated().before(lastModified)) {
			// once again conflicts should only result in non-existing cache
			fileEntry.remove(contentType);
			return null;
		}
		// perform pruning from time to time
		prune();		
		return cacheEntry;
	}

	@Override
	public void setCached(File file, String contentType, byte[] content) {
		// lock entirely if necessary
		if (!cache.containsKey(file)) {
			synchronized(cache) {
				if (!cache.containsKey(file))
					cache.put(file, new HashMap<String, CacheEntry>());
				cache.get(file).put(contentType, new CacheEntry(content));
			}
		}
		else {
			// Important: I don't want to lock the entire cache unless absolutely necessary however under certain conditions, the following synchronized() statement can throw a NullPointerException
			// If the cache[file] is deleted in between the first check and here, synchronized(null) will throw a npe
			try {
				synchronized(cache.get(file)) {
					try {
						cache.get(file).put(contentType, new CacheEntry(content));
					}
					catch (NullPointerException e) {
						// need to bypass the suppression...
						throw new RuntimeException(e);
					}
				}
			}
			catch (NullPointerException e) {
				// suppress, this is acceptable as it will be added to the cache on the next try
			}
		}
	}

	/**
	 * Only deletes the cache of files that no longer exist
	 * If the file is still being used, it is presumed that if the cache is outdated, it will be updated when you request it
	 */
	private void prune() {
		Date now = new Date();
		if (checkInterval > 0 && (lastChecked == null || now.getTime() - lastChecked.getTime() > checkInterval)) {
			synchronized(cache) {
				Iterator<File> iterator = cache.keySet().iterator();
				while (iterator.hasNext()) {
					File file = iterator.next();
					if (!file.exists())
						iterator.remove();
				}
			}
			lastChecked = now;
		}
	}

	@Override
	public Date getAge(File file, String contentType) {
		CacheEntry cacheEntry = getCacheEntry(file, contentType);
		return cacheEntry == null ? null : cacheEntry.getCreated();
	}
	
}
