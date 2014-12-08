package co.woofy.upload;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import co.woofy.upload.Tribunal;

import co.woofy.upload.TribunalDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig tribunalDaoConfig;

    private final TribunalDao tribunalDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        tribunalDaoConfig = daoConfigMap.get(TribunalDao.class).clone();
        tribunalDaoConfig.initIdentityScope(type);

        tribunalDao = new TribunalDao(tribunalDaoConfig, this);

        registerDao(Tribunal.class, tribunalDao);
    }
    
    public void clear() {
        tribunalDaoConfig.getIdentityScope().clear();
    }

    public TribunalDao getTribunalDao() {
        return tribunalDao;
    }

}
