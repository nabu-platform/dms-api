package be.nabu.libs.dms.api;

public interface ConverterResolver {
	public Converter getConverter(String fromContentType, String toContentType);
}
