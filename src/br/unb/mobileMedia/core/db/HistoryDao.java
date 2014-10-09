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
import br.unb.mobileMedia.core.domain.History;
import br.unb.mobileMedia.core.domain.Video;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table HISTORY.
*/
public class HistoryDao extends AbstractDao<History, Long> {

    public static final String TABLENAME = "HISTORY";

    /**
     * Properties of entity History.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property AudioHistoryId = new Property(1, long.class, "audioHistoryId", false, "AUDIO_HISTORY_ID");
        public final static Property VideoHistoryId = new Property(2, long.class, "videoHistoryId", false, "VIDEO_HISTORY_ID");
    };

    private DaoSession daoSession;

    private Query<History> audio_AudiosHistoryQuery;
    private Query<History> video_VideosHistoryQuery;

    public HistoryDao(DaoConfig config) {
        super(config);
    }
    
    public HistoryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'HISTORY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'AUDIO_HISTORY_ID' INTEGER NOT NULL ," + // 1: audioHistoryId
                "'VIDEO_HISTORY_ID' INTEGER NOT NULL );"); // 2: videoHistoryId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'HISTORY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, History entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getAudioHistoryId());
        stmt.bindLong(3, entity.getVideoHistoryId());
    }

    @Override
    protected void attachEntity(History entity) {
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
    public History readEntity(Cursor cursor, int offset) {
        History entity = new History( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // audioHistoryId
            cursor.getLong(offset + 2) // videoHistoryId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, History entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAudioHistoryId(cursor.getLong(offset + 1));
        entity.setVideoHistoryId(cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(History entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(History entity) {
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
    
    /** Internal query to resolve the "audiosHistory" to-many relationship of Audio. */
    public List<History> _queryAudio_AudiosHistory(long audioHistoryId) {
        synchronized (this) {
            if (audio_AudiosHistoryQuery == null) {
                QueryBuilder<History> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.AudioHistoryId.eq(null));
                audio_AudiosHistoryQuery = queryBuilder.build();
            }
        }
        Query<History> query = audio_AudiosHistoryQuery.forCurrentThread();
        query.setParameter(0, audioHistoryId);
        return query.list();
    }

    /** Internal query to resolve the "videosHistory" to-many relationship of Video. */
    public List<History> _queryVideo_VideosHistory(long videoHistoryId) {
        synchronized (this) {
            if (video_VideosHistoryQuery == null) {
                QueryBuilder<History> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.VideoHistoryId.eq(null));
                video_VideosHistoryQuery = queryBuilder.build();
            }
        }
        Query<History> query = video_VideosHistoryQuery.forCurrentThread();
        query.setParameter(0, videoHistoryId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getAudioDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getVideoDao().getAllColumns());
            builder.append(" FROM HISTORY T");
            builder.append(" LEFT JOIN AUDIO T0 ON T.'AUDIO_HISTORY_ID'=T0.'_id'");
            builder.append(" LEFT JOIN VIDEO T1 ON T.'VIDEO_HISTORY_ID'=T1.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected History loadCurrentDeep(Cursor cursor, boolean lock) {
        History entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Audio audio = loadCurrentOther(daoSession.getAudioDao(), cursor, offset);
         if(audio != null) {
            entity.setAudio(audio);
        }
        offset += daoSession.getAudioDao().getAllColumns().length;

        Video video = loadCurrentOther(daoSession.getVideoDao(), cursor, offset);
         if(video != null) {
            entity.setVideo(video);
        }

        return entity;    
    }

    public History loadDeep(Long key) {
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
    public List<History> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<History> list = new ArrayList<History>(count);
        
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
    
    protected List<History> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<History> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
