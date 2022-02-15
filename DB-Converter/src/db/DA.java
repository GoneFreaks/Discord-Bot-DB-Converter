package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import db.dto.ChannelDTO;
import db.dto.PlaylistDTO;
import db.dto.ResultDTO;
import db.dto.TrackDTO;

public class DA {

	public static ResultDTO read (ConnectionManager cMan) throws SQLException {
		List<ChannelDTO> channels = readChannels(cMan);
		List<TrackDTO> tracks = readTracks(cMan);
		List<PlaylistDTO> playlists = readPlaylists(cMan);
		return new ResultDTO(channels, tracks, playlists);
	}
	
	private static List<ChannelDTO> readChannels (ConnectionManager cMan) throws SQLException {
		List<ChannelDTO> result = new LinkedList<>();
		try(Connection cn = cMan.getConnection()) {
			Statement stmt = cn.createStatement();
			try(ResultSet rs = stmt.executeQuery("SELECT * FROM output_channel")) {
				while(rs.next()) result.add(new ChannelDTO(rs.getLong(1), rs.getLong(2)));
			}
		}
		return result;
	}
	
	private static List<TrackDTO> readTracks (ConnectionManager cMan) throws SQLException {
		List<TrackDTO> result = new LinkedList<>();
		try(Connection cn = cMan.getConnection()) {
			Statement stmt = cn.createStatement();
			try(ResultSet rs = stmt.executeQuery("SELECT * FROM track")) {
				while(rs.next()) result.add(new TrackDTO(rs.getInt(1), rs.getString(2)));
			}
		}
		return result;
	}

	private static List<PlaylistDTO> readPlaylists (ConnectionManager cMan) throws SQLException {
		List<PlaylistDTO> result = new LinkedList<>();
		try(Connection cn = cMan.getConnection()) {
			Statement stmt = cn.createStatement();
			try(ResultSet rs = stmt.executeQuery("SELECT * FROM playlist")) {
				while(rs.next()) result.add(new PlaylistDTO(rs.getLong(1), rs.getBoolean(2), rs.getString(3), rs.getInt(4)));
			}
		}
		return result;
	}
	
	public static ResultDTO write (ConnectionManager cMan, ResultDTO input) throws SQLException {
		writeChannels(cMan, input.getChannels());
		writeTracks(cMan, input.getTracks());
		writePlaylists(cMan, input.getPlaylists());
		return read(cMan);
	}
	
	private static int[] writeChannels (ConnectionManager cMan, List<ChannelDTO> channels) throws SQLException {
		try(Connection cn = cMan.getConnection()) {
			cn.setAutoCommit(false);
			try(PreparedStatement pstmt = cn.prepareStatement("INSERT INTO output_channel (guildId, channelId) VALUES (?,?)")) {
				for (ChannelDTO i : channels) {
					pstmt.setLong(1, i.getGuild_id());
					pstmt.setLong(2, i.getChannel_id());
					pstmt.addBatch();
				}
				int[] result = pstmt.executeBatch();
				cn.commit();
				return result;
			} catch (Exception e) {
				cn.rollback();
				return null;
			}
		}
	}
	
	private static int[] writeTracks (ConnectionManager cMan, List<TrackDTO> tracks) throws SQLException {
		try(Connection cn = cMan.getConnection()) {
			cn.setAutoCommit(false);
			try(PreparedStatement pstmt = cn.prepareStatement("INSERT INTO track (iD, url) VALUES (?,?)")) {
				for (TrackDTO i : tracks) {
					pstmt.setInt(1, i.getId());
					pstmt.setString(2, i.getUrl());
					pstmt.addBatch();
				}
				int[] result = pstmt.executeBatch();
				cn.commit();
				return result;
			} catch (Exception e) {
				cn.rollback();
				return null;
			}
		}
	}

	private static int[] writePlaylists (ConnectionManager cMan, List<PlaylistDTO> playlists) throws SQLException {
		try(Connection cn = cMan.getConnection()) {
			cn.setAutoCommit(false);
			try(PreparedStatement pstmt = cn.prepareStatement("INSERT INTO playlist (iD, isUser, playlist_name, trackid) VALUES (?,?,?,?)")) {
				for (PlaylistDTO i : playlists) {
					pstmt.setLong(1, i.getId());
					pstmt.setBoolean(2, i.isUser());
					pstmt.setString(3, i.getName());
					pstmt.setInt(4, i.getTrack_id());
					pstmt.addBatch();
				}
				int[] result = pstmt.executeBatch();
				cn.commit();
				return result;
			} catch (Exception e) {
				cn.rollback();
				return null;
			}
		}
	}
	
}
