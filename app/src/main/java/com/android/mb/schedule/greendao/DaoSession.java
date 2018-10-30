package com.android.mb.schedule.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.android.mb.schedule.db.Office;
import com.android.mb.schedule.db.Schedule;
import com.android.mb.schedule.db.User;

import com.android.mb.schedule.greendao.OfficeDao;
import com.android.mb.schedule.greendao.ScheduleDao;
import com.android.mb.schedule.greendao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig officeDaoConfig;
    private final DaoConfig scheduleDaoConfig;
    private final DaoConfig userDaoConfig;

    private final OfficeDao officeDao;
    private final ScheduleDao scheduleDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        officeDaoConfig = daoConfigMap.get(OfficeDao.class).clone();
        officeDaoConfig.initIdentityScope(type);

        scheduleDaoConfig = daoConfigMap.get(ScheduleDao.class).clone();
        scheduleDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        officeDao = new OfficeDao(officeDaoConfig, this);
        scheduleDao = new ScheduleDao(scheduleDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(Office.class, officeDao);
        registerDao(Schedule.class, scheduleDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        officeDaoConfig.clearIdentityScope();
        scheduleDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public OfficeDao getOfficeDao() {
        return officeDao;
    }

    public ScheduleDao getScheduleDao() {
        return scheduleDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
