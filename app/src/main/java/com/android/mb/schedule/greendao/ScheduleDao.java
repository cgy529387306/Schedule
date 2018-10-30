package com.android.mb.schedule.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.android.mb.schedule.db.Schedule;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SCHEDULE".
*/
public class ScheduleDao extends AbstractDao<Schedule, Long> {

    public static final String TABLENAME = "SCHEDULE";

    /**
     * Properties of entity Schedule.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Create_by = new Property(1, long.class, "create_by", false, "CREATE_BY");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Description = new Property(3, String.class, "description", false, "DESCRIPTION");
        public final static Property Date = new Property(4, String.class, "date", false, "DATE");
        public final static Property Time_s = new Property(5, long.class, "time_s", false, "TIME_S");
        public final static Property Time_e = new Property(6, long.class, "time_e", false, "TIME_E");
        public final static Property Address = new Property(7, String.class, "address", false, "ADDRESS");
        public final static Property StartTime = new Property(8, String.class, "startTime", false, "START_TIME");
        public final static Property EndTime = new Property(9, String.class, "endTime", false, "END_TIME");
        public final static Property AllDay = new Property(10, int.class, "allDay", false, "ALL_DAY");
        public final static Property Repeattype = new Property(11, int.class, "repeattype", false, "REPEATTYPE");
        public final static Property Remind = new Property(12, int.class, "remind", false, "REMIND");
        public final static Property Important = new Property(13, int.class, "important", false, "IMPORTANT");
        public final static Property Summary = new Property(14, String.class, "summary", false, "SUMMARY");
        public final static Property Not_remind_related = new Property(15, int.class, "not_remind_related", false, "NOT_REMIND_RELATED");
        public final static Property Close_time = new Property(16, long.class, "close_time", false, "CLOSE_TIME");
        public final static Property Createtime = new Property(17, long.class, "createtime", false, "CREATETIME");
        public final static Property Updatetime = new Property(18, long.class, "updatetime", false, "UPDATETIME");
        public final static Property St_del = new Property(19, int.class, "st_del", false, "ST_DEL");
        public final static Property Related = new Property(20, String.class, "related", false, "RELATED");
        public final static Property Share = new Property(21, String.class, "share", false, "SHARE");
        public final static Property File = new Property(22, String.class, "file", false, "FILE");
    }


    public ScheduleDao(DaoConfig config) {
        super(config);
    }
    
    public ScheduleDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SCHEDULE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CREATE_BY\" INTEGER NOT NULL ," + // 1: create_by
                "\"TITLE\" TEXT," + // 2: title
                "\"DESCRIPTION\" TEXT," + // 3: description
                "\"DATE\" TEXT," + // 4: date
                "\"TIME_S\" INTEGER NOT NULL ," + // 5: time_s
                "\"TIME_E\" INTEGER NOT NULL ," + // 6: time_e
                "\"ADDRESS\" TEXT," + // 7: address
                "\"START_TIME\" TEXT," + // 8: startTime
                "\"END_TIME\" TEXT," + // 9: endTime
                "\"ALL_DAY\" INTEGER NOT NULL ," + // 10: allDay
                "\"REPEATTYPE\" INTEGER NOT NULL ," + // 11: repeattype
                "\"REMIND\" INTEGER NOT NULL ," + // 12: remind
                "\"IMPORTANT\" INTEGER NOT NULL ," + // 13: important
                "\"SUMMARY\" TEXT," + // 14: summary
                "\"NOT_REMIND_RELATED\" INTEGER NOT NULL ," + // 15: not_remind_related
                "\"CLOSE_TIME\" INTEGER NOT NULL ," + // 16: close_time
                "\"CREATETIME\" INTEGER NOT NULL ," + // 17: createtime
                "\"UPDATETIME\" INTEGER NOT NULL ," + // 18: updatetime
                "\"ST_DEL\" INTEGER NOT NULL ," + // 19: st_del
                "\"RELATED\" TEXT," + // 20: related
                "\"SHARE\" TEXT," + // 21: share
                "\"FILE\" TEXT);"); // 22: file
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SCHEDULE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Schedule entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getCreate_by());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(4, description);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(5, date);
        }
        stmt.bindLong(6, entity.getTime_s());
        stmt.bindLong(7, entity.getTime_e());
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(8, address);
        }
 
        String startTime = entity.getStartTime();
        if (startTime != null) {
            stmt.bindString(9, startTime);
        }
 
        String endTime = entity.getEndTime();
        if (endTime != null) {
            stmt.bindString(10, endTime);
        }
        stmt.bindLong(11, entity.getAllDay());
        stmt.bindLong(12, entity.getRepeattype());
        stmt.bindLong(13, entity.getRemind());
        stmt.bindLong(14, entity.getImportant());
 
        String summary = entity.getSummary();
        if (summary != null) {
            stmt.bindString(15, summary);
        }
        stmt.bindLong(16, entity.getNot_remind_related());
        stmt.bindLong(17, entity.getClose_time());
        stmt.bindLong(18, entity.getCreatetime());
        stmt.bindLong(19, entity.getUpdatetime());
        stmt.bindLong(20, entity.getSt_del());
 
        String related = entity.getRelated();
        if (related != null) {
            stmt.bindString(21, related);
        }
 
        String share = entity.getShare();
        if (share != null) {
            stmt.bindString(22, share);
        }
 
        String file = entity.getFile();
        if (file != null) {
            stmt.bindString(23, file);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Schedule entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getCreate_by());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(4, description);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(5, date);
        }
        stmt.bindLong(6, entity.getTime_s());
        stmt.bindLong(7, entity.getTime_e());
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(8, address);
        }
 
        String startTime = entity.getStartTime();
        if (startTime != null) {
            stmt.bindString(9, startTime);
        }
 
        String endTime = entity.getEndTime();
        if (endTime != null) {
            stmt.bindString(10, endTime);
        }
        stmt.bindLong(11, entity.getAllDay());
        stmt.bindLong(12, entity.getRepeattype());
        stmt.bindLong(13, entity.getRemind());
        stmt.bindLong(14, entity.getImportant());
 
        String summary = entity.getSummary();
        if (summary != null) {
            stmt.bindString(15, summary);
        }
        stmt.bindLong(16, entity.getNot_remind_related());
        stmt.bindLong(17, entity.getClose_time());
        stmt.bindLong(18, entity.getCreatetime());
        stmt.bindLong(19, entity.getUpdatetime());
        stmt.bindLong(20, entity.getSt_del());
 
        String related = entity.getRelated();
        if (related != null) {
            stmt.bindString(21, related);
        }
 
        String share = entity.getShare();
        if (share != null) {
            stmt.bindString(22, share);
        }
 
        String file = entity.getFile();
        if (file != null) {
            stmt.bindString(23, file);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Schedule readEntity(Cursor cursor, int offset) {
        Schedule entity = new Schedule( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // create_by
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // description
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // date
            cursor.getLong(offset + 5), // time_s
            cursor.getLong(offset + 6), // time_e
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // address
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // startTime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // endTime
            cursor.getInt(offset + 10), // allDay
            cursor.getInt(offset + 11), // repeattype
            cursor.getInt(offset + 12), // remind
            cursor.getInt(offset + 13), // important
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // summary
            cursor.getInt(offset + 15), // not_remind_related
            cursor.getLong(offset + 16), // close_time
            cursor.getLong(offset + 17), // createtime
            cursor.getLong(offset + 18), // updatetime
            cursor.getInt(offset + 19), // st_del
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // related
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // share
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22) // file
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Schedule entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCreate_by(cursor.getLong(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDescription(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDate(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTime_s(cursor.getLong(offset + 5));
        entity.setTime_e(cursor.getLong(offset + 6));
        entity.setAddress(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setStartTime(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setEndTime(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setAllDay(cursor.getInt(offset + 10));
        entity.setRepeattype(cursor.getInt(offset + 11));
        entity.setRemind(cursor.getInt(offset + 12));
        entity.setImportant(cursor.getInt(offset + 13));
        entity.setSummary(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setNot_remind_related(cursor.getInt(offset + 15));
        entity.setClose_time(cursor.getLong(offset + 16));
        entity.setCreatetime(cursor.getLong(offset + 17));
        entity.setUpdatetime(cursor.getLong(offset + 18));
        entity.setSt_del(cursor.getInt(offset + 19));
        entity.setRelated(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setShare(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setFile(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Schedule entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Schedule entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Schedule entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}