package br.unb.mobileMedia.core.domain;

import br.unb.mobileMedia.core.db.AudioDao;
import br.unb.mobileMedia.core.db.DaoSession;
import br.unb.mobileMedia.core.db.PlaylistDao;
import br.unb.mobileMedia.core.db.PlaylistMediaDao;
import br.unb.mobileMedia.core.db.VideoDao;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PLAYLIST_MEDIA.
 */
public class PlaylistMedia {

    private Long id;
    private Long videoId;
    private Long audioId;
    private long playlistId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient PlaylistMediaDao myDao;

    private Video playlistMediaVideo;
    private Long playlistMediaVideo__resolvedKey;

    private Audio playlistMediaAudio;
    private Long playlistMediaAudio__resolvedKey;

    private Playlist playlistMediaPlaylist;
    private Long playlistMediaPlaylist__resolvedKey;


    public PlaylistMedia() {
    }

    public PlaylistMedia(Long id) {
        this.id = id;
    }

    public PlaylistMedia(Long id, Long videoId, Long audioId, long playlistId) {
        this.id = id;
        this.videoId = videoId;
        this.audioId = audioId;
        this.playlistId = playlistId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlaylistMediaDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getAudioId() {
        return audioId;
    }

    public void setAudioId(Long audioId) {
        this.audioId = audioId;
    }

    public long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(long playlistId) {
        this.playlistId = playlistId;
    }

    /** To-one relationship, resolved on first access. */
    public Video getPlaylistMediaVideo() {
        Long __key = this.videoId;
        if (playlistMediaVideo__resolvedKey == null || !playlistMediaVideo__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            VideoDao targetDao = daoSession.getVideoDao();
            Video playlistMediaVideoNew = targetDao.load(__key);
            synchronized (this) {
                playlistMediaVideo = playlistMediaVideoNew;
            	playlistMediaVideo__resolvedKey = __key;
            }
        }
        return playlistMediaVideo;
    }

    public void setPlaylistMediaVideo(Video playlistMediaVideo) {
        synchronized (this) {
            this.playlistMediaVideo = playlistMediaVideo;
            videoId = playlistMediaVideo == null ? null : playlistMediaVideo.getId();
            playlistMediaVideo__resolvedKey = videoId;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Audio getPlaylistMediaAudio() {
        Long __key = this.audioId;
        if (playlistMediaAudio__resolvedKey == null || !playlistMediaAudio__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AudioDao targetDao = daoSession.getAudioDao();
            Audio playlistMediaAudioNew = targetDao.load(__key);
            synchronized (this) {
                playlistMediaAudio = playlistMediaAudioNew;
            	playlistMediaAudio__resolvedKey = __key;
            }
        }
        return playlistMediaAudio;
    }

    public void setPlaylistMediaAudio(Audio playlistMediaAudio) {
        synchronized (this) {
            this.playlistMediaAudio = playlistMediaAudio;
            audioId = playlistMediaAudio == null ? null : playlistMediaAudio.getId();
            playlistMediaAudio__resolvedKey = audioId;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Playlist getPlaylistMediaPlaylist() {
        long __key = this.playlistId;
        if (playlistMediaPlaylist__resolvedKey == null || !playlistMediaPlaylist__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlaylistDao targetDao = daoSession.getPlaylistDao();
            Playlist playlistMediaPlaylistNew = targetDao.load(__key);
            synchronized (this) {
                playlistMediaPlaylist = playlistMediaPlaylistNew;
            	playlistMediaPlaylist__resolvedKey = __key;
            }
        }
        return playlistMediaPlaylist;
    }

    public void setPlaylistMediaPlaylist(Playlist playlistMediaPlaylist) {
        if (playlistMediaPlaylist == null) {
            throw new DaoException("To-one property 'playlistId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.playlistMediaPlaylist = playlistMediaPlaylist;
            playlistId = playlistMediaPlaylist.getId();
            playlistMediaPlaylist__resolvedKey = playlistId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}