package net.transolyfer.transolyfergo.data.storage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import net.transolyfer.transolyfergo.data.entity.raw.OrderRegister;

import java.sql.SQLException;

/**
 *
 * Clase que crea y modifica la base de datos
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "net.transolyfer.transolyfergo";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getSimpleName(),"Creando base de Datos");
            TableUtils.createTable(connectionSource, OrderRegister.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getSimpleName(),"Actualizando base de Datos");
            TableUtils.dropTable(connectionSource, OrderRegister.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearOrderRegisterDatabase(){
        try {
            TableUtils.clearTable(this.connectionSource, OrderRegister.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() {
        super.close();
    }
}
