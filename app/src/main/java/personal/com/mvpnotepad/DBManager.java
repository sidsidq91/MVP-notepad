package personal.com.mvpnotepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private final String DBName = "SQLiteDataBase";
    private final String TableName = "NoteTable";
    private final String NoteNameColumn = "NoteName";
    private final String NoteDataColumn = "NoteDate";
    private final String NoteContentColumn = "NoteContent";
    private final int dbVersion = 1;
    private DataBaseHelper dbhelper;
    private final String CreateTable = "create table IF NOT EXISTS "+
            TableName+"(ID integer PRIMARY KEY AUTOINCREMENT,"+
            NoteNameColumn+" TEXT,"+
            NoteDataColumn+" TEXT,"+
            NoteContentColumn+" TEXT);";

    public DBManager(Context context){
        this.context = context;
        dbhelper = new DataBaseHelper(context);
        sqLiteDatabase = dbhelper.getWritableDatabase();
    }

    public Note insertNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(NoteNameColumn,note.getName());
        values.put(NoteContentColumn,note.getContent());
        values.put(NoteDataColumn,note.getDate());
        sqLiteDatabase.insert(TableName,"",values);
        return note;
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        String sql = "";
        sql += "SELECT DISTINCT "+"ID"+","+NoteNameColumn+","+NoteContentColumn+","+NoteDataColumn+" FROM " + TableName;
        sql += " ORDER BY " + NoteNameColumn + " ASC";
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql , null);
        if (cursor.moveToFirst()) {
            do {
                notes.add(new Note(cursor.getString(cursor.getColumnIndex(NoteContentColumn)),
                        cursor.getString(cursor.getColumnIndex(NoteDataColumn)),
                        cursor.getString(cursor.getColumnIndex(NoteNameColumn)),
                        cursor.getInt(cursor.getColumnIndex("ID"))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    class DataBaseHelper extends SQLiteOpenHelper {
        Context context;
        public DataBaseHelper(Context context) {
            super(context, DBName,null,dbVersion);
            this.context = context;
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CreateTable);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop table IF EXISTS "+TableName);
            onCreate(db);
        }
    }
}
