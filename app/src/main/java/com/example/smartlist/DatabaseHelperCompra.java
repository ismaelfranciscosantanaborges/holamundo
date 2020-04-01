package com.example.smartlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelperCompra extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "DBProductosComprados";
    private static String TABLET_NAME = "Productos";

    private static DatabaseHelperCompra databaseHelper;

    private DatabaseHelperCompra(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    //Aqui esta el patron Sigleton
    public static DatabaseHelperCompra obtenerConexion(Context context){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelperCompra( context );
            return databaseHelper;
        }

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLET_NAME + "(" +
                "id INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "json VARCHAR(1000)" +
                ")";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLET_NAME);

        onCreate(db);
    }

    public boolean addEntidad(ProductosComprados entidad){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("json", entidad.getJSONAllCampos());

        sqLiteDatabase.insert(TABLET_NAME, null, contentValues);

        return true;

    }

    public ArrayList<ProductosComprados> getAllEntidad(){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<ProductosComprados> arrayList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLET_NAME, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int idDataBase = cursor.getInt(cursor.getColumnIndex("id"));
            String json = cursor.getString(cursor.getColumnIndex("json"));

            String jsonn = json.replace("{","")
                    .replace("}", "").replace(":", ",").replace("\"", "");
            String[] array = jsonn.split(",");

            Log.e("klklk", jsonn);

            String id = array[1];
            String nombre = array[3];
            String marca = array[5];
            String descripcion = array[7];
            double precioTotal = Double.parseDouble(array[9]);
            int cantidad = Integer.parseInt(array[11]);
            double precio = Double.parseDouble(array[13]);


            ProductosComprados entidad = new ProductosComprados(id,nombre, descripcion, marca,precio);
            entidad.setIdDataBase(idDataBase);
            entidad.setCantidad(cantidad);
            entidad.setPrecioTotal(precioTotal);

            arrayList.add( entidad );

            cursor.moveToNext();
        }

        return arrayList;
    }


    public boolean updateEntidad(ProductosComprados item){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("json", item.getJSONAllCampos());

        database.update(TABLET_NAME, values, "id=" + item.getIdDataBase() , null);

        return true;
    }

    public Integer deleteEntidad(ProductosComprados item){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(TABLET_NAME, "id="+item.getIdDataBase(), null);
    }

    public boolean buscarSiExiste(String id){
        SQLiteDatabase database = this.getWritableDatabase();


        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLET_NAME + " WHERE json LIKE '%" + id + "%'", null);
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


}
