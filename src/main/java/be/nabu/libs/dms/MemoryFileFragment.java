package be.nabu.libs.dms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Date;
import java.util.Iterator;

import be.nabu.libs.vfs.api.File;
import be.nabu.libs.vfs.api.FileSystem;

public class MemoryFileFragment implements File {
	
	private File original;
	private byte [] fragment;
	private String name;
	private String contentType;
	
	public MemoryFileFragment(File original, byte [] fragment, String name, String contentType) {
		this.original = original;
		this.fragment = fragment;
		this.name = name;
		this.contentType = contentType;
	}

	@Override
	public Iterator<File> iterator() {
		return original.iterator();
	}

	@Override
	public boolean isDirectory() {
		return original.isDirectory();
	}

	@Override
	public boolean isFile() {
		return original.isFile();
	}

	@Override
	public boolean isReadable() {
		return original.isReadable();
	}

	@Override
	public boolean isWritable() {
		return original.isWritable();
	}

	@Override
	public File resolve(String path) throws IOException {
		return original.resolve(path);
	}

	@Override
	public FileSystem getFileSystem() {
		return original.getFileSystem();
	}

	@Override
	public File getParent() throws IOException {
		return original.getParent();
	}

	@Override
	public String getPath() {
		return original.getPath() + "#" + name;
	}

	@Override
	public String getName() {
		return name + "$" + original.getName();
	}

	@Override
	public long getSize() throws IOException {
		return fragment.length;
	}

	@Override
	public String getContentType() throws IOException {
		return contentType;
	}

	@Override
	public Date getLastModified() throws IOException {
		return original.getLastModified();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(fragment);
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		throw new IOException("Can not write to a fragment");
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public void delete() throws IOException {
		// do nothing
	}

	@Override
	public void move(File target) throws IOException {
		InputStream input = getInputStream();
		try {
			OutputStream output = target.getOutputStream();
			try {
				copy(input, output);
			}
			finally {
				output.close();
			}
		}
		finally {
			input.close();
		}
	}
	
	private void copy(InputStream input, OutputStream output) throws IOException {
		int read = 0;
		byte [] buffer = new byte[102400];
		while ((read = input.read(buffer)) != -1)
			output.write(buffer, 0, read);
	}

	@Override
	public void mkdir() throws IOException {
		throw new IOException("Not implemented");
	}

	@Override
	public File cloneFor(Principal principal) {
		return this;
	}
}
