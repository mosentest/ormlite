package org.mo.ormlite.service;

import android.content.Context;
import android.widget.Toast;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.support.DatabaseConnection;
import org.mo.ormlite.R;
import org.mo.ormlite.dao.DatabaseHelper;
import org.mo.ormlite.dao.UserDao;
import org.mo.ormlite.entity.User;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

/**
 * Created by moziqi on 2015/1/18 0018.
 */
public class UserService {
    // 添加事物
    public DatabaseConnection connection;
    private UserDao mUserDao;
    private Context mContext;

    public UserService(Context mContext) {
        this.mContext = mContext;
        mUserDao = new UserDao(mContext);
        connection = new AndroidDatabaseConnection(
                DatabaseHelper.getHelper(mContext).getWritableDatabase(), true);
    }

    /**
     * 参考这个,其他加事务吧
     *
     * @param user
     * @return
     */
    public int saveUser(User user) {
        int flag = -1;//保存失败
        Savepoint savepoint = null;
        try {
            boolean byUsername = mUserDao.findByUsername(user.getUsername());
            if (byUsername) {
                flag = 0;//用户名存在
            } else {
                savepoint = connection.setSavePoint("save");
                if (mUserDao.save(user)) {
                    flag = 1;//保存成功
                    connection.commit(savepoint);
                }
            }
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return flag;
    }

    public boolean updateUser(User user) {
        return mUserDao.update(user);
    }

    public boolean deleteUser(User user) {
        return mUserDao.delete(user);
    }

    public User getUserById(long id) {
        return mUserDao.findOneById(id);
    }

    public List<User> getAll() {
        List<User> users = mUserDao.findAll();
        return users;
    }

    public long countAll() {
        return mUserDao.countAll();
    }

    public List<User> getAllByLimit(long currentPage, long size) {
        return mUserDao.findAllByLimit(currentPage - 1, size);
    }

    public List<User> getAllUserOnlyUsernamePassword() {
        return mUserDao.findUNP();
    }
}
