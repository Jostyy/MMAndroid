package br.unb.mobileMedia.core.db;

import java.util.List;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import br.unb.mobileMedia.core.domain.Audio;
import br.unb.mobileMedia.core.domain.Playlist;
import br.unb.mobileMedia.core.domain.PlaylistMedia;
import br.unb.mobileMedia.core.domain.Video;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PLAYLIST_MEDIA.
*/
public class PlaylistMediaDao extends AbstractDao<PlaylistMedia, Long> {

    public static final String TABLENAME = "PLAYLIST_MEDIA";

    /**
     * Properties of entity PlaylistMedia.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property VideoId = new Property(1, Long.class, "videoId", false, "VIDEO_ID");
        public final static Property AudioId = new Property(2, Long.class, "audioId", false, "AUDIO_ID");
        public final static Property PlaylistId = new Property(3, long.class, "playlistId", false, "PLAYLIST_ID");
    };

    private DaoSession daoSession;

    private Query<PlaylistMedia> video_ListPlaylistMediaVideoQuery;
    private Query<PlaylistMedia> audio_ListPlaylistMediaAudioQuery;
    private Query<PlaylistMedia> playlist_ListPlaylistMediaPlaylistMQuery;

    public PlaylistMediaDao(DaoConfig config) {
        super(config);
    }
    
    public PlaylistMediaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PLAYLIST_MEDIA' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'VIDEO_ID' INTEGER," + // 1: videoId
                "'AUDIO_ID' INTEGER," + // 2: audioId
                "'PLAYLIST_ID' INTEGER NOT NULL );"); // 3: playlistId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PLAYLIST_MEDIA'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, PlaylistMedia entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long videoId = entity.getVideoId();
        if (videoId != null) {
            stmt.bindLong(2, videoId);
        }
 
        Long audioId = entity.getAudioId();
        if (audioId != null) {
            stmt.bindLong(3, audioId);
        }
        stmt.bindLong(4, entity.getPlaylistId());
    }

    @Override
    protected void attachEntity(PlaylistMedia entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public PlaylistMedia readEntity(Cursor cursor, int offset) {
        PlaylistMedia entity = new PlaylistMedia( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // videoId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // audioId
            cursor.getLong(offset + 3) // playlistId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, PlaylistMedia entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setVideoId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setAudioId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setPlaylistId(cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(PlaylistMedia entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(PlaylistMedia entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "listPlaylistMediaVideo" to-many relationship of Video. */
    public List<PlaylistMedia> _queryVideo_ListPlaylistMediaVideo(Long videoId) {
        synchronized (this) {
            if (video_ListPlaylistMediaVideoQuery == null) {
                QueryBuilder<PlaylistMedia> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.VideoId.eq(null));
                video_ListPlaylistMediaVideoQuery = queryBuilder.build();
            }
        }
        Query<PlaylistMedia> query = video_ListPlaylistMediaVideoQuery.forCurrentThread();
        query.setParameter(0, videoId);
        return query.list();
    }

    /** Internal query to resolve the "listPlaylistMediaAudio" to-many relationship of Audio. */
    public List<PlaylistMedia> _queryAudio_ListPlaylistMediaAudio(Long audioId) {
        synchronized (this) {
            if (audio_ListPlaylistMediaAudioQuery == null) {
                QueryBuilder<PlaylistMedia> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.AudioId.eq(null));
                audio_ListPlaylistMediaAudioQuery = queryBuilder.build();
            }
        }
        Query<PlaylistMedia> query = audio_ListPlaylistMediaAudioQuery.forCurrentThread();
        query.setParameter(0, audioId);
        return query.list();
    }

    /** Internal query to resolve the "listPlaylistMediaPlaylistM" to-many relationship of Playlist. */
    public List<PlaylistMedia> _queryPlaylist_ListPlaylistMediaPlaylistM(long playlistId) {
        synchronized (this) {
            if (playlist_ListPlaylistMediaPlaylistMQuery == null) {
                QueryBuilder<PlaylistMedia> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.PlaylistId.eq(null));
                playlist_ListPlaylistMediaPlaylistMQuery = queryBuilder.build();
            }
        }
        Query<PlaylistMedia> query = playlist_ListPlaylistMediaPlaylistMQuery.forCurrentThread();
        query.setParameter(0, playlistId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getVideoDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getAudioDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T2", daoSession.getPlaylistDao().getAllColumns());
            builder.append(" FROM PLAYLIST_MEDIA T");
            builder.append(" LEFT JOIN VIDEO T0 ON T.'VIDEO_ID'=T0.'_id'");
            builder.append(" LEFT JOIN AUDIO T1 ON T.'AUDIO_ID'=T1.'_id'");
            builder.append(" LEFT JOIN PLAYLIST T2 ON T.'PLAYLIST_ID'=T2.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected PlaylistMedia loadCurrentDeep(Cursor cursor, boolean lock) {
        PlaylistMedia entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Video playlistMediaVideo = loadCurrentOther(daoSession.getVideoDao(), cursor, offset);
        entity.setPlaylistMediaVideo(playlistMediaVideo);
        offset += daoSession.getVideoDao().getAllColumns().length;

        Audio playlistMediaAudio = loadCurrentOther(daoSession.getAudioDao(), cursor, offset);
        entity.setPlaylistMediaAudio(playlistMediaAudio);
        offset += daoSession.getAudioDao().getAllColumns().length;

        Playlist playlistMediaPlaylist = loadCurrentOther(daoSession.getPlaylistDao(), cursor, offset);
         if(playlistMediaPlaylist != null) {
            entity.setPlaylistMediaPlaylist(playlistMediaPlaylist);
        }

        return entity;    
    }

    public PlaylistMedia loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<PlaylistMedia> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<PlaylistMedia> list = new ArrayList<PlaylistMedia>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<PlaylistMedia> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<PlaylistMedia> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}