package br.unb.mobileMedia.core.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import br.unb.mobileMedia.core.db.DBException;
import br.unb.mobileMedia.core.db.DBFactory;
import br.unb.mobileMedia.core.db.idao.IAlbumDao;
import br.unb.mobileMedia.core.db.idao.IAudioDao;
import br.unb.mobileMedia.core.db.idao.IAuthorDao;
import br.unb.mobileMedia.core.db.idao.IPlayListDao;
import br.unb.mobileMedia.core.db.idao.IPlaylistMediaDao;
import br.unb.mobileMedia.core.domain.Album;
import br.unb.mobileMedia.core.domain.Audio;
import br.unb.mobileMedia.core.domain.Author;
import br.unb.mobileMedia.core.domain.Playlist;
import br.unb.mobileMedia.core.domain.PlaylistMedia;
import br.unb.mobileMedia.core.domain.VideoFormats;
import br.unb.mobileMedia.core.extractor.DefaultAudioExtractor;
import br.unb.mobileMedia.core.extractor.MediaExtractor;
import br.unb.mobileMedia.util.FileUtility;
import br.unb.mobileMedia.util.ListAllFiles;

/**
 * A facade class for the MMUnB application.
 * 
 * @author rbonifacio
 */
public class Manager {

	/* singleton instance of the manager class */
	private static Manager instance;

	/*
	 * Private constructor according to the singleton design pattern
	 */
	private Manager(){
		
	}; 

	/**
	 * Single method to obtain an instance of the Manager class. 
	 * 
	 * @return the singleton instance of the Manager class. 
	 */
	public static final Manager instance() {
		if(instance == null) {
			instance = new Manager();
		}
		return instance;
	}

	/**
	 * Updates the MMUnB database with the existing MP3s files 
	 * in the SDCARD.
	 * 
	 * TODO: a lot must be generalized here. it only works for MP3 and M4A file.
	 * 
	 * @param context the application context.
	 */
	public void synchronizeMedia(Context context) throws DBException {
		
		sync_audio(context);
//		sync_video(context);
	}

	private void sync_video(Context context) throws DBException{
		final List<File> allVideos = new ArrayList<File>();

		for(VideoFormats format : VideoFormats.values()) {
			allVideos.addAll(FileUtility.listFiles(new File(Environment
					.getExternalStorageDirectory().getPath() + "/Movies/"),
					format.getFormatAsString()));
		}
//		MediaExtractor extractor = new DefaultVideoExtractor(context);

//		List<Author> authors = (List<Author>) extractor.processFiles(allVideos);

		
//		saveAuthor(context, authors);

	}

	private void sync_audio(Context context) throws DBException {
		
		MediaExtractor extractor = new DefaultAudioExtractor(context);
		
		//get all audio in device
		List<File> allMusics = new ListAllFiles().getAllMusic();
		
		//extract all authors of audio in device
		List<String> authors =  extractor.extractAllAuthors(allMusics);
		
		// save authors in database
		for(String author : authors)
			saveAuthors(context, author);
		
		
		//Get all authors put in database to get id of all author
		List<Author> list_authors = listAuthors(context);
		
		
		//extract all albums of audios in device
		List<Album> albums = extractor.extractAllAlbum(allMusics, list_authors);
		
		//save all album with respective authorId in db
		for(Album album : albums)
			saveAlbum(context, album);
		
		
		//Get all albums put in database to get id of all album
		albums = this.listAlbums(context);
		
		//prepare all audio to persist in database
		List<Audio> audios = extractor.processAudio(allMusics, albums);
		
		saveAudios(context, audios);
		
	}
	


	private void saveAudios(Context context, List<Audio> audios) throws DBException {

		IAudioDao daoAudio  = DBFactory.factory(context).createAudioDAO();

		for(Audio a : audios){
//			 Log.i("Audio Title:", ""+a.getTitle());
//			 Log.i("Audio Url:", ""+a.getUrl());
//			 Log.i("Audio Album:", ""+a.getAlbum());
//			 Log.i("Audio Author:", ""+a.getAuthorId());
			
			 daoAudio.saveAudio(a);
			 
		}
				
	}
	
	
	
	public void saveAuthors(Context context, String nameAuthor){
		IAuthorDao dao = DBFactory.factory(context).createAuthorDao();
		
		try {
			dao.saveAuthor(nameAuthor);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void saveAlbum(Context context, Album album){
		
		IAlbumDao dao = DBFactory.factory(context).createAlbumDao();
		
		try {
			dao.saveAlbum(album);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	


	/**
	 * List the production of a specific author
	 * @param authorPK the primary key of the author
	 * @return the author production.
	 */
	public List<Audio> listProductionByAuthorPK(Context context, Integer authorPK) throws DBException {
		IAuthorDao dao = DBFactory.factory(context).createAuthorDao();
		return dao.findAudioProductionByAuthorKey(authorPK);
	}

	/**
	 * List all authors in the MMUnB database 
	 * @param context the application context
	 * @return all authors in the database
	 * @throws DBException
	 */
	public List<Author> listAuthors(Context context) throws DBException {
		IAuthorDao dao = DBFactory.factory(context).createAuthorDao();
		return dao.listAuthors();
	}
	
	public List<Album> listAlbums(Context context) throws DBException{
		IAlbumDao dao = DBFactory.factory(context).createAlbumDao();
		
		return dao.listAlbums();
	}

	/**
	 * List all production within the database
	 * @param context the application context
	 * @return a list with all production synchronized at the database.
	 */
	public List<Audio> listAllProduction(Context context) throws DBException {
		IAuthorDao dao = DBFactory.factory(context).createAuthorDao();
		return dao.listAllProduction();
	}
	
	/**
	 * 
	 * @param context
	 * @param init
	 * @param limit
	 * @return
	 * @throws DBException
	 */
	public List<Audio> listAllAudioPaginated(Context context,  int init, int limit) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IAudioDao audioDao = factory.createAudioDAO();
		
		return audioDao.listAllAudioPaginated(init, limit);
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws DBException
	 */
	public List<Audio> listAllAudio(Context context) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IAudioDao audioDao = factory.createAudioDAO();
		return audioDao.listAllAudio();
//		DBFactory factory = DBFactory.factory(context);
//		final IPlayListDao playlistDao = factory.createPlaylistDao();
//		playlistDao.deletePlaylist(namePlaylist);
	}
	
	
	/**
	 * 
	 * @param context
	 * @return number of audio in device
	 */
	public Long countAllAudio(Context context){
		DBFactory factory = DBFactory.factory(context);
		final IAudioDao audioDao = factory.createAudioDAO();
		return audioDao.countAllAudio();
	}
	
	
	public Long countAllAlbum(Context context){
		DBFactory factory = DBFactory.factory(context);
		final IAlbumDao albumDao = factory.createAlbumDao();
		return albumDao.countAlbum();
	}
	
	

	/**
	 * List the recently played musics, considering the period 
	 * from <i>start</i> until today, gouped by Author and Music. 
	 * 
	 * It is a map of maps, where the first map key is the 
	 * author, and the second map key is an audio. The values entry 
	 * of the second map is a list of dates, representing the number 
	 * in which the audio was played in a given period. 
	 * 
	 * @param context the application context
	 * @param start the start date of the period
	 * 
	 * @return a list with the most recently played musics. 
	 */
	public Map<Author, Map<Audio, List<Date>>> recently(Context context, Date start) throws DBException {
		Date today = Calendar.getInstance().getTime();
		if(start.before(today)) {
			Map<Author, Map<Audio, List<Date>>> executionHistory = DBFactory.factory(context).createAuthorDao().executionHistory(start, today);


			return executionHistory;
		}
		return null;
	}

	/**
	 * Registers the execution of a specific audio. 
	 * 
	 * @param context the application context.
	 * @param audio the audio that has just been executed
	 */
	public void registerExecution(Context context, Audio audio) throws DBException {
		Log.v(Manager.class.getCanonicalName(), "PK: "  + audio.getId());
		DBFactory.factory(context).createAuthorDao().saveExecutionHistory(audio, Calendar.getInstance().getTime());
	}

	/**
	 * Returns the <i>size</i>most frequently played artists
	 * 
	 * @param context the application context
	 * @param start the start date considered
	 * @param end the end date considered
	 * @param size the size of the returned list is constrained to size 
	 * @return the list of the top <i>size</i> artists
	 * @throws DBException
	 */
	public List<Author> topArtistsFromPeriod(Context context, Date start, Date end, int size) throws DBException {
		Map<Author, Map<Audio, List<Date>>> executionHistory = DBFactory.factory(context).createAuthorDao().executionHistory(start, end);

		Map<Author, Integer> summary = summarize(executionHistory);

		Map<Author, Integer> sortedMap = new TreeMap<Author, Integer>(new SummaryOfExecutionHistoryComparator(summary));

		sortedMap.putAll(summary);

		Iterator<Author> it = sortedMap.keySet().iterator();

		List<Author> result = new ArrayList<Author>();
		for(int i = 0; i < sortedMap.keySet().size() && i < 5 ; i++) {
			result.add(it.next());
		}
		return result;
	}

	/**
	 * Create a new playlist
	 * 
	 * @param context the application context
	 * @param playlist to be created
	 * @throws DBException
	 */
	public void newPlaylist(Context context, Playlist playlist) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlayListDao playlistDao = factory.createPlaylistDao();
		playlistDao.newPlaylist(playlist);
//		final PlaylistDAOOld playlistDAO = factory.createPlaylistDAO();
//		playlistDAO.newPlaylist(playlist);
	}

	/**
	 * Edit an existing playlist. The edited playlist will 
	 * be found by the editedPlaylist id parameter. All fields
	 * from editedPlaylist (except id) will overwrite all 
	 * fields from the found playlist currently persisted.
	 * 
	 * @param context the application context
	 * @param playlist to be edited
	 * @throws DBException
	 */
	public void editPlaylist(Context context, Playlist editedPlaylist) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlayListDao playlistDao = factory.createPlaylistDao();
		playlistDao.editPlaylist(editedPlaylist);
	}

	/**
	 * Remove a playlist identified by id and Automatic remove all media from this playlist
	 * 
	 * @param context the application context
	 * @param name from the playlist to be removed
	 * @throws DBException
	 */
	public void removePlaylist(Context context, Long idPlaylist) throws DBException {
		
		DBFactory factory = DBFactory.factory(context);
		
		final IPlayListDao playlistDao = factory.createPlaylistDao();
		final IPlaylistMediaDao playlistMediaDao = factory.createPlaylistMediaDao();
		
		try{
			//Remove all medias from playlist
			removeAllMediaFromPlaylist(context, idPlaylist);
			
			//delete playlist
			playlistDao.deletePlaylist(idPlaylist);
			
		}catch(DBException e){
			Log.e("Erro", e.getCause().toString());
		}
		
		
	}
	
	/**
	 * Add a geographical position to playlist
	 * 
	 * @param context the application context
	 * @param playlist
	 * @param latitude
	 * @param longitude
	 * @throws DBException
	 */
	public void addPositionPlaylist(Context context, Playlist playlist, double latitude, double longitude) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlayListDao playlistDao = factory.createPlaylistDao();
		playlistDao.addPositionPlaylist(playlist,latitude,longitude);
//		final PlaylistDAOOld playlistDAO = factory.createPlaylistDAO();
//		playlistDAO.addPositionPlaylist(playlist,latitude,longitude);
		
	}
	
	/**
	 * Return a playlists identified by its id. The playlist returned do not have any media
	 * 
	 * @param context the application context
	 * @param id from the playlist to be found
	 * @return a playlist object
	 * @throws DBException
	 */
	public Playlist getPlaylistById(Context context, int id) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlayListDao playlistDao = factory.createPlaylistDao();
		return playlistDao.getPlaylistById(id);
	}
	
	/**
	 * Return a playlists identified by its name. The playlist returned do not have any media
	 * 
	 * @param context the application context
	 * @param name from the playlist to be found
	 * @return a playlist object
	 * @throws DBException
	 */
	public Playlist getPlaylistByName(Context context, String name) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlayListDao playlistDao = factory.createPlaylistDao();
		return playlistDao.getPlaylistByName(name);
//		final PlaylistDAOOld playlistDAO = factory.createPlaylistDAO();
//		return playlistDAO.getSimplePlaylist(name);
	}
	
	/**
	 * Return a playlists identified by its id 
	 * 
	 * @param context the application context
	 * @param id from the playlist to be found
	 * @return a playlist object
	 * @throws DBException
	 */
	public Playlist getPlaylist(Context context, int id) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlayListDao playlistDao = factory.createPlaylistDao();
		return playlistDao.getPlaylist(id);
//		final PlaylistDAOOld playlistDAO = factory.createPlaylistDAO();
//		return playlistDAO.getPlaylist(id);
	}
	
	/**
	 * Return a playlists identified by its name 
	 * 
	 * @param context the application context
	 * @param name from the playlist to be found
	 * @return a playlist object
	 * @throws DBException
	 */
	public Playlist getPlaylist(Context context, String name) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlayListDao playlistDao = factory.createPlaylistDao();
		return playlistDao.getPlaylistByName(name);
//		final PlaylistDAOOld playlistDAO = factory.createPlaylistDAO();
//		return playlistDAO.getPlaylist(name);
	}
	
	/**
	 * Return all playlists persisted. The playlist returned do not have any media
	 * 
	 * @param context the application context
	 * @return the list of all playlists
	 * @throws DBException
	 */
	public List<Playlist> listSimplePlaylists(Context context) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlayListDao playlistDao = factory.createPlaylistDao();
		return playlistDao.listSimplePlaylists();
		
//		final PlaylistDAOOld playlistDAO = factory.createPlaylistDAO();
//		return playlistDAO.listSimplePlaylists();
	}
	
	/**
	 * Return all playlists persisted
	 * 
	 * @param context the application context
	 * @return the list of all playlists
	 * @throws DBException
	 */
	public List<Playlist> listPlaylists(Context context) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlayListDao playlistDao = factory.createPlaylistDao();
		return playlistDao.listPlaylists();
	}
	
	/**
	 * Add a media to a playlist
	 * 
	 * @param context the application context
	 * @param id from the playlist
	 * @param list of ids of medias 
	 * @throws DBException
	 */
	public void addMediaToPlaylist(Context context, int idPlaylist, List<Long> mediaList) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlaylistMediaDao playlistMediaDao = factory.createPlaylistMediaDao();
		
		Playlist platlist = new Playlist((long)idPlaylist);
		
		for(Long idAudio : mediaList){
			playlistMediaDao.addAudioToPlaylist(idAudio, platlist);
		}
	}

	
	
	/**
	 * Remove all medias from a playlist
	 * 
	 * @param context the application context
	 * @param id from the playlist
	 * @param list of ids of medias 
	 * @throws DBException
	 */
	public void removeAllMediaFromPlaylist(Context context, Long idPlaylist) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlaylistMediaDao playlistMediaDao = factory.createPlaylistMediaDao();
		
		List<PlaylistMedia> pl;
		
		try{
			pl = playlistMediaDao.getMusicFromPlaylist(idPlaylist);
			
			if(pl != null){
				for(int i=0;i<pl.size();i++){
					playlistMediaDao.removeMediaFromPlaylist(pl.get(i).getId());
				}
				
			}
			
		}catch(DBException e){
			e.getStackTrace();
			Log.i("PLMedia", "null");
		}
			
		
	}
	
	
	/**
	 * Remove a list of medias from a playlist
	 * 
	 * @param context the application context
	 * @param id from the playlist
	 * @param list of ids of medias 
	 * @throws DBException
	 */
	public void removeMediaFromPlaylist(Context context, int idPlaylist, List<Audio> mediaList) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlaylistMediaDao playlistMediaDao = factory.createPlaylistMediaDao();
		PlaylistMedia pl;
		
		for(Audio audio : mediaList){
			
			try{
				pl = playlistMediaDao.getPlaylistByMediaInPlaylist(audio, new Playlist((long)idPlaylist));
				
				if(pl != null)
					playlistMediaDao.removeMediaFromPlaylist(pl.getId());
				
			}catch(DBException e){
				e.getStackTrace();
				Log.i("PLMedia", "null");
			}
			
			
			
		}
		
	}
	
	/**
	 *  List all the musics from a a playlist
	 * 
	 * @param context the application context
	 * @param id from the playlist
	 * @throws DBException
	 */
	public List<Audio> getMusicFromPlaylist(Context context, int idPlaylist) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlaylistMediaDao playlistMediaDao = factory.createPlaylistMediaDao();
		final IAudioDao audioDao = factory.createAudioDAO();

		List<PlaylistMedia> playlist = playlistMediaDao.getMusicFromPlaylist((long)idPlaylist);
		
		List<Audio> audiosInPlaylist = new ArrayList<Audio>();
		
		for(PlaylistMedia media : playlist){
			audiosInPlaylist.add(audioDao.listAudioById(new Audio(media.getAudioPlaylistMediaId())));
		}
		
		 return audiosInPlaylist;
	}
	

	
	
	
	public List<Audio> getMusicFromPlaylistPaginate(Context context, int idPlaylist, int init, int limit) throws DBException {
		DBFactory factory = DBFactory.factory(context);
		final IPlaylistMediaDao playlistMediaDao = factory.createPlaylistMediaDao();
		final IAudioDao audioDao = factory.createAudioDAO();

		List<PlaylistMedia> playlist = playlistMediaDao.getMusicFromPlaylistPaginate(new Playlist((long)idPlaylist), init, limit);
//		
		List<Audio> audiosInPlaylist = new ArrayList<Audio>();
//		
		for(PlaylistMedia media : playlist){
			audiosInPlaylist.add(audioDao.listAudioById(new Audio(media.getAudioPlaylistMediaId())));
		}
//		
		Log.i("init "+ init, "limit " + limit);
		
//		return null;
		 return audiosInPlaylist;
	}
	
	
	private Map<Author, Integer> summarize(Map<Author, Map<Audio, List<Date>>> executionHistory) {
		Map<Author, Integer> result = new HashMap<Author, Integer>();
		for(Author author : executionHistory.keySet()) {
			int totalOfExecution = 0;
			for(Audio audio : executionHistory.get(author).keySet()) {
				totalOfExecution += executionHistory.get(author).get(audio).size();
			}
			result.put(author, totalOfExecution);
		}
		return result;
	}

	/*
	 * A comparator for ordering the execution history of an author 
	 * (a map<Author, Integer).
	 */
	class SummaryOfExecutionHistoryComparator implements Comparator<Author> {
		private Map<Author, Integer> map;

		public SummaryOfExecutionHistoryComparator(Map<Author, Integer> map) {
			this.map = map;
		}

		public int compare(Author key1, Author key2) {
			if(map.containsKey(key1) && map.containsKey(key2)) {
				return map.get(key1).compareTo(map.get(key2));
			}
			return 0;
		}
	}
} 
