package be.nabu.libs.dms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import be.nabu.libs.datastore.api.DataProperties;
import be.nabu.libs.datastore.api.WritableDatastore;
import be.nabu.libs.resources.URIUtils;
import be.nabu.libs.vfs.api.File;
import be.nabu.utils.io.IOUtils;

public class FileDatastore implements WritableDatastore {

	private File file;

	public FileDatastore(File file) {
		this.file = file;
	}
	
	@Override
	public InputStream retrieve(URI uri) throws IOException {
		File target = file.getParent().resolve(".resources");
		if (target.exists()) {
			final File resolve = target.resolve(uri.getPath());
			if (resolve.exists()) {
				return resolve.getInputStream();
			}
		}
		return null;
	}

	@Override
	public DataProperties getProperties(URI uri) throws IOException {
		File target = file.getParent().resolve(".resources");
		if (target.exists()) {
			final File resolve = target.resolve(uri.getPath());
			if (resolve.exists()) {
				return new DataProperties() {
					@Override
					public long getSize() {
						try {
							return resolve.getSize();
						}
						catch (IOException e) {
							throw new RuntimeException();
						}
					}
					@Override
					public String getName() {
						return resolve.getName();
					}
					@Override
					public String getContentType() {
						try {
							return resolve.getContentType();
						}
						catch (IOException e) {
							throw new RuntimeException();
						}
					}
					@Override
					public Date getLastModified() {
						try {
							return resolve.getLastModified();
						}
						catch (IOException e) {
							throw new RuntimeException();
						}
					}
					
				};
			}
		}
		return null;
	}

	@Override
	public URI store(InputStream input, String name, String contentType) throws IOException {
		File target = file.getParent().resolve(".resources");
		if (!target.exists()) {
			target.mkdir();
		}
		File resolve = target.resolve(name);
		OutputStream output = resolve.getOutputStream();
		try {
			IOUtils.copyBytes(IOUtils.wrap(input), IOUtils.wrap(output));
		}
		finally {
			output.close();
		}
		try {
			return new URI(URIUtils.encodeURI(name));
		}
		catch (URISyntaxException e) {
			throw new IOException("Could not create uri", e);
		}
	}

}
