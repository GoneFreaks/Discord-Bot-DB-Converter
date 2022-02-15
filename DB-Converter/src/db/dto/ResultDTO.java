package db.dto;

import java.util.List;

public class ResultDTO {

	private final List<ChannelDTO> channels;
	private final List<TrackDTO> tracks;
	private final List<PlaylistDTO> playlists;
	
	public ResultDTO(List<ChannelDTO> channels, List<TrackDTO> tracks, List<PlaylistDTO> playlists) {
		this.channels = channels;
		this.tracks = tracks;
		this.playlists = playlists;
	}

	public List<ChannelDTO> getChannels() {
		return channels;
	}

	public List<TrackDTO> getTracks() {
		return tracks;
	}

	public List<PlaylistDTO> getPlaylists() {
		return playlists;
	}
}
