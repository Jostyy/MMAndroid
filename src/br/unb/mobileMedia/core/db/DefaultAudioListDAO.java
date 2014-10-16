package br.unb.mobileMedia.core.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import br.unb.mobileMedia.core.domain.Audio;
import br.unb.mobileMedia.core.domain.Playlist;

/**
 * Default implementation of PlaylistDAO.
 * 
 * @author willian
 */

public class DefaultAudioListDAO {
	
	private Context context;
	private SQLiteDatabase db;
	private DBHelper dbHelper;

	/**
	 * Constructor of DefaultPlaylistDAO.
	 * 
	 * @param c the application context.
	 */
	
	public DefaultAudioListDAO(Context c) {
		context = c;
		dbHelper = new DBHelper(context, DBConstants.DATABASE_NAME, null,
				DBConstants.DATABASE_VERSION);
	}
	

	
	private void fechaConexao(){
		if (db.inTransaction()) {
			db.endTransaction();
		}
		db.close();
		dbHelper.close();
	}
	
	private void trataException(SQLiteException e) throws DBException{
		e.printStackTrace();
		Log.e(DefaultAudioListDAO.class.getCanonicalName(),
				e.getLocalizedMessage());
		throw new DBException();
	}
	
	/**
	 * 
	 * @return the quantity of audio have in bank
	 * @throws DBException
	 */
	public int countListAudioBanco() throws DBException {
		
		int count = 0;
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(DBConstants.SELECT_SIMPLE_ALL_AUDIOS, null);

			if (cursor.moveToFirst()) {
				do {
					count++;
					
				} while (cursor.moveToNext());
			}
			cursor.close();
			return count;
		} catch (SQLiteException e) {
			trataException(e);
		} finally {
			fechaConexao();
		}
		return 0;
	}


    /**
     * 
     * @param audioId
     * @param title
     * @param url
     * @param album
     * @return
     * @throws DBException
     * include in the bank the recieve audio informations
     */
	public int adicionaAudio(Integer audioId,String title, String url, String album, Integer fkAuthor) throws DBException {

		try {
			db = dbHelper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			
			values.put(DBConstants.AUDIO_TITLE_COLUMN, title);
			values.put(DBConstants.AUDIO_URL_COLUMN, url);
			values.put(DBConstants.AUDIO_ALBUM_COLUMN, album);
			values.put(DBConstants.AUDIO_ID_COLUMN, audioId);
			values.put(DBConstants.AUDIO_FK_AUTHOR_COLUMN, fkAuthor);
			
			db.beginTransaction();
			db.insert(DBConstants.AUDIO_TABLE, null, values);
			db.setTransactionSuccessful();
				
		} catch (SQLiteException e) {
			
			e.printStackTrace();
			Log.e(DefaultAuthorDAO.class.getCanonicalName(),
					e.getLocalizedMessage());
			throw new DBException();
			
		} finally {
		
			if (db.inTransaction()) {
				db.endTransaction();
			}
			db.close();
			dbHelper.close();
			
		}
		
		return 1;
	}
	
	
	



}