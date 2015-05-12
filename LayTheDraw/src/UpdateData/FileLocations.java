package UpdateData;

public class FileLocations {
	private String filename;
	private String URL;
	public FileLocations(String filename, String URL){
		this.filename=filename;
		this.URL=URL;
	}
	public String getFilename() {
		return filename;
	}
	public String getURL() {
		return URL;
	}
}
