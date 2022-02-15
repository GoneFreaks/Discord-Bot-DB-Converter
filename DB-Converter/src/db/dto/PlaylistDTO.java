package db.dto;

public class PlaylistDTO {

	private final long id;
	private final boolean isUser;
	private final String name;
	private final int track_id;
	
	public PlaylistDTO(long id, boolean isUser, String name, int track_id) {
		this.id = id;
		this.isUser = isUser;
		this.name = name;
		this.track_id = track_id;
	}

	public long getId() {
		return id;
	}

	public boolean isUser() {
		return isUser;
	}

	public String getName() {
		return name;
	}

	public int getTrack_id() {
		return track_id;
	}
}
