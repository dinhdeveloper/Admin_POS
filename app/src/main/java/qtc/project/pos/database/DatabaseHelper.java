package qtc.project.pos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import b.laixuantam.myaarlibrary.helper.MyLog;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
    private static final String NAME = "database.db";
    private static final int VERSION = 1;
//    private AirPortDao airPortDao;

    public DatabaseHelper(Context context)
    {
        super(context.getApplicationContext(), DatabaseHelper.NAME, null, DatabaseHelper.VERSION);

        this.connectionSource = new AndroidConnectionSource(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource)
    {
        createAllTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        MyLog.d("DatabaseHelper", "Upgrade DB: " + oldVersion + " to " + newVersion);
        dropAllTable();
        createAllTable();
//        AppProvider.getPreferences().clear();
    }

    private void dropAllTable()
    {
        try
        {
//            TableUtils.dropTable(connectionSource, AirPortModel.class, true);
        }
        catch (Exception e)
        {
            MyLog.e("DatabaseHelper", e.getMessage());
        }
    }

    private void createAllTable()
    {
        try
        {
//            TableUtils.createTableIfNotExists(connectionSource, AirPortModel.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            MyLog.e("DatabaseHelper", e.getMessage());
        }
    }

    public void clear()
    {
        dropAllTable();
        createAllTable();
    }

//    public synchronized AirPortDao getAirPortDao() throws SQLException
//    {
//        if (airPortDao == null)
//        {
//            airPortDao = getDao(AirPortModel.class);
//        }
//
//        return airPortDao;
//    }


    @Override
    public void close()
    {
        super.close();

//        airPortDao = null;
    }



}
