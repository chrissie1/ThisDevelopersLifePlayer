package be.baes.thisDevelopersLifePlayer.dal.sqliteandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context) {
        super(context, be.baes.thisDevelopersLifePlayer.Constants.DATABASE_NAME, null, be.baes.thisDevelopersLifePlayer.Constants.DATABASE_VERSION);
    }

    /* (non-Javadoc)
      * @see be.baes.notes.Dal.DatabaseHelper#onCreate(android.database.sqlite.SQLiteDatabase)
      */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "Creating database");
        db.execSQL(be.baes.thisDevelopersLifePlayer.Constants.DATABASE_CREATE);
    }

    /* (non-Javadoc)
      * @see be.baes.notes.Dal.DatabaseHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
      */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID,"upgrading database");
        db.execSQL(be.baes.thisDevelopersLifePlayer.Constants.DROP_TABLE);
        onCreate(db);
    }

    public SQLiteDatabase getWritableDatabase()
    {
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "getWritableDatabase");
        try
        {
            return super.getWritableDatabase();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public void close()
    {
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "close");
        super.close();
    }
}
