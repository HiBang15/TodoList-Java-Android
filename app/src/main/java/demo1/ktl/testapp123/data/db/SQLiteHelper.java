package demo1.ktl.testapp123.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import demo1.ktl.testapp123.data.model.Item;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "th2.view";
    private static final int DB_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "date TEXT," +
                "status TEXT" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase statement = getReadableDatabase();
        Cursor cursor = statement.query("items", null, null, null, null, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1); //name
            String date = cursor.getString(2); //date 2
            String status = cursor.getString(3); //status 3

            list.add(new Item(id, title, date, status));
        }

        return list;
    }

    public void addItem(Item item) {
        String sql = "INSERT INTO items(name, date, status, isCoop) values (?,?,?,?)";
        String[] args = {item.getName(), item.getDate(), item.getStatus()};
        SQLiteDatabase statement = getWritableDatabase();
        statement.execSQL(sql, args);
    }

    public void updateItem(Item item) {
        String sql = "UPDATE items " +
                "SET name = ?, date = ?, status = ? " +
                "WHERE id = ?";
        String[] args = {item.getName(), item.getDate(), item.getStatus(), String.valueOf(item.getId())};
        SQLiteDatabase statement = getWritableDatabase();
        statement.execSQL(sql, args);
    }

    public void deleteItem(int id) {
        String sql = "DELETE FROM items " +
                "WHERE id = ?";
        String[] args = {String.valueOf(id)};
        SQLiteDatabase statement = getWritableDatabase();
        statement.execSQL(sql, args);
    }
}
