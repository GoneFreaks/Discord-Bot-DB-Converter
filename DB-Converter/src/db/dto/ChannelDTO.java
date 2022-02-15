package db.dto;

public class ChannelDTO {

	private final long guild_id;
	private final long channel_id;
	
	public ChannelDTO(long guild_id, long channel_id) {
		this.guild_id = guild_id;
		this.channel_id = channel_id;
	}

	public long getGuild_id() {
		return guild_id;
	}

	public long getChannel_id() {
		return channel_id;
	}
}
