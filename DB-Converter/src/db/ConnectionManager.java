package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class ConnectionManager {

	private SQLiteDataSource source;
	private final File db;
	
	public ConnectionManager(String db_url, boolean src) throws Exception {
		this.db = new File(db_url);
		if(src && !db.exists()) throw new FileNotFoundException(); 
		if(!src && db.exists()) {
			if(db.delete()) db.createNewFile();
		}
		connect(src);
	}
	
	private void connect(boolean src) throws SQLException {
		this.source = new SQLiteDataSource();
		source.setUrl("jdbc:sqlite:" + db.getPath());
		if(source.getConnection() == null) throw new SQLException();
		if(!src) init();
	}
	
	private static final String[] TABLES = {"CREATE TABLE output_channel (guildId int(64) primary key, channelId int(64) unique not null)",
											"CREATE TABLE track (iD integer primary key, url varchar unique not null)",
											"CREATE TABLE playlist (iD int(64) not null, isUser boolean not null, playlist_name varchar not null, trackid int not null, unique(iD, isUser, playlist_name, trackid))"};
	private void init() throws SQLException {
		try(Connection cn = getConnection()) {
			for (int i = 0; i < TABLES.length; i++) {
				Statement stmt = cn.createStatement();
				stmt.execute(TABLES[i]);
			}
		} 
	}
	
	public Connection getConnection() throws SQLException {
		return source.getConnection();
	}
	
}
