package br.unb.mobileMedia.core.db;

public interface DBConstants {
	public String DATABASE_NAME = "MMUnBDB";
	

	//String repetida em v��rias partes do c��digo
	public String DEFINICAO_PK = "PK INTEGER PRIMARY KEY AUTOINCREMENT";

	public int DATABASE_VERSION = 24;


	public String DROP_TABLE_STATEMENTS[] = { "DROP TABLE IF EXISTS AUTHOR",
			"DROP TABLE IF EXISTS AUDIO", "DROP TABLE IF EXISTS EXECUTION_HISTORY", "DROP TABLE IF EXISTS TB_PLAYLIST",
			"DROP TABLE IF EXISTS TB_MEDIA_FROM_PLAYLIST" };

	public String CREATE_TABLE_STATEMENTS[] = {
			"CREATE TABLE AUTHOR(" +  DEFINICAO_PK +", "
					+ "ID INTEGER, " + "NAME VARCHAR(50) NOT NULL);" + "NAMEUNIC VARCHAR(50) NOT NULL);",
			
			//TODO: here, we are only considering audio. we must generalize!
			"CREATE TABLE AUDIO ( " + DEFINICAO_PK +", "
					+ "ID INTEGER, " + "TITLE VARCHAR(50) NOT NULL,"
					+ "URL VARCHAR(255) NOT NULL, " + "ALBUM VARCHAR(50),"
					+ "FK_AUTHOR INTEGER);",

			"CREATE TABLE EXECUTION_HISTORY ( "
					+ DEFINICAO_PK+", "
					+ "DATE_TIME_EXECUTION CHAR(14), " + "FK_MEDIA INTEGER);",
			
			"CREATE TABLE TB_PLAYLIST ( " 
					+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, " 
					+ "NAME VARCHAR(255) UNIQUE NOT NULL );",
			
			"CREATE TABLE TB_MEDIA_FROM_PLAYLIST ("
					+ DEFINICAO_PK + ", "
					+ "FK_PLAYLIST INTEGER," 
					+ "FK_MEDIA INTEGER );",
					
			"CREATE TABLE PLAYLIST_LOCATION ( " 
					+ "FK_PLAYLIST INTEGER UNIQUE NOT NULL, " 
					+ "LATITUDE DOUBLE NOT NULL, "
					+ "LONGITUDE DOUBLE NOT NULL );"}; //yyyyMMddhhmmss

	public String SELECT_AUTHORS = "SELECT PK, ID, NAME FROM AUTHOR";
	public String SELECT_AUTHORS_BY_ID = "SELECT PK, ID, NAME FROM AUTHOR WHERE ID = ?";
	public String SELECT_AUTHORS_BY_NAME = "SELECT PK, ID, NAME FROM AUTHOR WHERE NAME = ?";
	public String SELECT_AUDIO_PRODUCTION_BY_AUTHOR_KEY = "SELECT PK, ID, TITLE, ALBUM, URL FROM AUDIO WHERE FK_AUTHOR = ?";
	public String SELECT_AUDIO_BY_AUTHOR_KEY_TITLE = "SELECT PK, ID FROM AUDIO WHERE FK_AUTHOR = ? AND TITLE = ?";
	public String SELECT_AUDIO_BY_ID = "SELECT PK, ID, TITLE, ALBUM, URL FROM AUDIO WHERE ID = ?";
	public String SELECT_SIMPLE_PLAYLIST = "SELECT ID, NAME FROM TB_PLAYLIST";
	public String SELECT_SIMPLE_ALL_AUDIOS = "SELECT * FROM AUDIO";
	public String SELECT_SIMPLE_PLAYLIST_BY_ID = "SELECT ID, NAME FROM TB_PLAYLIST WHERE ID = ?";
	public String SELECT_SIMPLE_PLAYLIST_BY_NAME = "SELECT ID, NAME FROM TB_PLAYLIST WHERE NAME = ?";
	public String LIST_AUDIO_PRODUCTION = "SELECT PK, ID, TITLE, ALBUM, URL FROM AUDIO";
	public String SELECT_MUSIC_FROM_A_PLAYLIST = "SELECT FK_MEDIA FROM TB_MEDIA_FROM_PLAYLIST WHERE FK_PLAYLIST = ?";
	
	public String SELECT_EXECUTION_HISTORY = 
			"SELECT AT.PK, AT.ID, AT.NAME, AU.PK, AU.ID, AU.TITLE, AU.ALBUM, AU.URL, EH.DATE_TIME_EXECUTION " +
			"FROM (AUTHOR AT INNER JOIN AUDIO AU ON AT.PK = AU.FK_AUTHOR) INNER JOIN EXECUTION_HISTORY EH " +
			" ON AU.PK = EH.FK_MEDIA " +
			"WHERE DATE_TIME_EXECUTION > ? AND DATE_TIME_EXECUTION < ?";
	
	
	public String AUTHOR_TABLE = "AUTHOR";
	public String AUTHOR_KEY_COLUMN = "PK";
	public String AUTHOR_ID_COLUMN = "ID";
	public String AUTHOR_NAME_COLUMN = "NAME";
	

	public String AUDIO_TABLE = "AUDIO";
	public String AUDIO_KEY_COLUMN = "PK";
	public String AUDIO_ID_COLUMN = "ID";
	public String AUDIO_TITLE_COLUMN = "TITLE";
	public String AUDIO_ALBUM_COLUMN = "ALBUM";
	public String AUDIO_URL_COLUMN = "URL";
	public String AUDIO_FK_AUTHOR_COLUMN = "FK_AUTHOR";
	
	public String EH_TABLE = "EXECUTION_HISTORY";
	public String EH_DATE_TIME_EXECUTION_COLUMN = "DATE_TIME_EXECUTION"; 
	public String EH_FK_AUDIO_COLUMN = "FK_MEDIA";
	
	public String PLAYLIST_TABLE = "TB_PLAYLIST";
	public String PLAYLIST_ID_COLUMN = "ID";
	public String PLAYLIST_NAME_COLUMN = "NAME";
	public String PLAYLIST_LOCATION_TABLE = "PLAYLIST_LOCATION";
	public String PLAYLIST_LOCATION_FK_PLAYLIST = "FK_PLAYLIST";
	public String PLAYLIST_LOCATION_LATITUDE = "LATITUDE";
	public String PLAYLIST_LOCATION_LONGITUDE = "LONGITUDE";

	public String MEDIA_FROM_PLAYLIST_TABLE = "TB_MEDIA_FROM_PLAYLIST"; 
	public String MEDIA_FROM_PLAYLIST_FK_PLAYLIST = "FK_PLAYLIST";
	public String MEDIA_FROM_PLAYLIST_FK_MEDIA = "FK_MEDIA";


}