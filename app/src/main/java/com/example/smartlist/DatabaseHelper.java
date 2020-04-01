package com.example.smartlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "database_name";
    private static String TABLET_NAME = "table_name";

    private static DatabaseHelper databaseHelper;

    private DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    //Aqui esta el patron Sigleton
    public static DatabaseHelper obtenerConexion(Context context){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper( context );
            return databaseHelper;
        }

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLET_NAME + "(" +
                "id INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "nombre VARCHAR(30)," +
                "cantidad INTEGER," +
                "nombreLista VARCHAR(30)" +
                ")";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLET_NAME);

        onCreate(db);
    }

    public boolean addEntidad(Lista1 entidad){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", entidad.getNombre().toUpperCase());
        contentValues.put("cantidad", entidad.getCantidad());
        contentValues.put("nombreLista", entidad.getNombreLista());

        sqLiteDatabase.insert(TABLET_NAME, null, contentValues);

        return true;

    }

    public ArrayList<Lista1> getAllEntidad(boolean buscarTodosLosNull){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Lista1> arrayList = new ArrayList<>();
        Cursor cursor;
        if(buscarTodosLosNull)
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLET_NAME + " WHERE nombreLista is NULL", null);
        else
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLET_NAME, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
            String nombreLista = cursor.getString(cursor.getColumnIndex("nombreLista"));

            Lista1 entidad = new Lista1(nombre, cantidad, false);
            entidad.setNombreLista(nombreLista);
            entidad.setId(id+"");

            arrayList.add( entidad );

            cursor.moveToNext();
        }

        return arrayList;
    }

    public ArrayList<Lista1> getAllNombreLista(String mostrarLista){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Lista1> arrayList = new ArrayList<>();
        Cursor cursor;

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLET_NAME + " WHERE nombreLista LIKE '" + mostrarLista + "%'", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
            String nombreLista = cursor.getString(cursor.getColumnIndex("nombreLista"));

            Lista1 entidad = new Lista1(nombre, cantidad, false);
            entidad.setNombreLista(nombreLista);
            entidad.setId(id+"");

            arrayList.add( entidad );

            cursor.moveToNext();
        }

        return arrayList;
    }

    public ArrayList<String> getTodosLosNombreLista(){
        ArrayList<String> listaDeNombre = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor;

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLET_NAME + " GROUP BY nombreLista", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            String nombreLista = cursor.getString(cursor.getColumnIndex("nombreLista"));

            listaDeNombre.add( nombreLista );

            cursor.moveToNext();
        }

        return listaDeNombre;
    }

    public void unicaVez(){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombreLista", "MI LISTA, 24-Mar.-2020");

        database.update(TABLET_NAME, values, "1=1", null);
    }


    public boolean updateEntidad(Lista1 item, boolean desdeModificar){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", item.getNombre().toUpperCase());
        values.put("cantidad", item.getCantidad());
        values.put("nombreLista", item.getNombreLista());

        if(desdeModificar)
            database.update(TABLET_NAME, values, "id=" + item.getId() , null);
        else
            database.update(TABLET_NAME, values, "nombre='"+ item.getNombre() +"'", null);

        return true;
    }

    public Integer deleteEntidad(Lista1 item){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(TABLET_NAME, "id="+item.getId(), null);
    }


    public boolean buscarSiExiste(String nombre){
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLET_NAME + " WHERE nombre='" + nombre.toUpperCase() + "'", null);
        int cont = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            cont++;
            cursor.moveToNext();
        }
        if (cont == 0)
            return false;
        else
            return true;
    }


    public boolean buscarNombreLista(String nombreLista){
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLET_NAME + " WHERE nombreLista='" + nombreLista.toUpperCase() + "'", null);
        int cont = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            cont++;
            cursor.moveToNext();
        }
        if (cont == 0)
            return false;
        else
            return true;
    }

    public boolean updateNombreLista(String nombreLista){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombreLista", nombreLista.toUpperCase());

        database.update(TABLET_NAME, values, "nombreLista is NULL", null);

        return true;
    }

}
