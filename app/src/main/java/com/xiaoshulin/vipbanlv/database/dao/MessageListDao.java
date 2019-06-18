package com.xiaoshulin.vipbanlv.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.xiaoshulin.vipbanlv.database.entity.MessageListEntity;

import java.util.List;

/**
 * Created by jipeng on 2018/11/27.
 */
@Dao
public interface MessageListDao {

    @Query("SELECT * FROM MessageListEntity")
    List<MessageListEntity> getMessagelist();


    @Insert
    void insert(MessageListEntity messageListEntity);

    @Update
    void update(MessageListEntity messageListEntity);

    @Delete
    void delete(MessageListEntity messageListEntity);

    /**降序*/
    @Query("SELECT * FROM MessageListEntity ORDER BY newestTime DESC")
    List<MessageListEntity> getMessagelistDesc();

}
