package com.xiaoshulin.vipbanlv.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.xiaoshulin.vipbanlv.database.dao.ChatMessageDao;
import com.xiaoshulin.vipbanlv.database.dao.MessageListDao;
import com.xiaoshulin.vipbanlv.database.entity.ChatMessageEntity;
import com.xiaoshulin.vipbanlv.database.entity.MessageListEntity;

import io.reactivex.annotations.NonNull;

/**
 * Created by jipeng on 2018/11/26.
 */
@Database(entities = {MessageListEntity.class, ChatMessageEntity.class}, version = 1, exportSchema = false)
public abstract class MessageDatabase extends RoomDatabase {

    private static MessageDatabase databaseCreator;

    public static MessageDatabase getInstance(Context context) {
        if (databaseCreator == null) {
            synchronized (MessageDatabase.class) {
                if (databaseCreator == null) {
                    databaseCreator = Room.databaseBuilder(context.getApplicationContext(), MessageDatabase.class,
                            "xiaoshulin.db").addMigrations(new Migration(1, 2) {
                        //版本升级.从1升到2
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            //升级处理
//                            database.execSQL("ALTER TABLE XXXX "
//                                    + " ADD COLUMN XXXXX INTEGER");
//                            database.execSQL("drop table if exists" +" XXXX ");
//                            database.execSQL("drop table if exists" +" XXXX ");
//                            database.execSQL("create table coach(id int,name varchar(30),age int,constraint pk_coach PRIMARY_KEY(id) )");
                        }
                        //版本升级并不方便，也不提倡频繁对数据库进行组织结构的变动
                    }).build();
                }
            }
        }
        return databaseCreator;
    }

    public static void onDestroy() {
        databaseCreator = null;
    }

    public abstract ChatMessageDao getChatMessageDao();

    public abstract MessageListDao getMessageListDao();
}
