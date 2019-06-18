package com.xiaoshulin.vipbanlv.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.xiaoshulin.vipbanlv.database.entity.ChatMessageEntity;

import java.util.List;

/**
 * Created by jipeng on 2018/11/27.
 */
@Dao
public interface ChatMessageDao {
    @Query("SELECT * FROM ChatMessageEntity")
    List<ChatMessageEntity> getChatMessagelist();

    @Insert
    void insert(ChatMessageEntity chatMessageEntity);

    @Update
    void update(ChatMessageEntity chatMessageEntity);

    @Delete
    void delete(ChatMessageEntity chatMessageEntity);

    /**
     * 降序
     */
    @Query("SELECT * FROM ChatMessageEntity WHERE (messageSection =:messageSectionid)")
    List<ChatMessageEntity> getChatMessagelistDesc(String messageSectionid);
}
