package db.dto;

public class TrackDTO {

	private final int id;
	private final String url;
	
	public TrackDTO(int id, String url) {
		this.id = id;
		this.url = url;
	}
	
	public int getId() {
		return id;
	}
	public String getUrl() {
		return url;
	}
}
