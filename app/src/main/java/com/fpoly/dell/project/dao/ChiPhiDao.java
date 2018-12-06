package com.fpoly.dell.project.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fpoly.dell.project.database.Constants;
import com.fpoly.dell.project.database.DatabaseHelper;
import com.fpoly.dell.project.model.ChiPhi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChiPhiDao {
    private final SQLiteDatabase db;
    private final DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "ChiPhi";
    public static final String SQL_CHIPHI = "CREATE TABLE ChiPhi(machiphi " +
            "text primary key,tenthucan text, ngaynhap date, soluong text, giatien text);";
    private static final String TAG = "ChiPhiDao";

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public ChiPhiDao(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    public int insertChiPhi(ChiPhi cp) {
        ContentValues values = new ContentValues();
        values.put("machiphi", cp.getMachiphi());
        values.put("tenthucan", cp.getTenthucan());
        values.put("ngaynhap", sdf.format(cp.getNgaynhap()));
        values.put("soluong", cp.getSoluong());
        values.put("giatien", cp.getGiatien());


        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    //getall
    public List<ChiPhi> getAllChiPhi() throws ParseException {
        List<ChiPhi> dschiphi = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            ChiPhi ee = new ChiPhi();
            ee.setMachiphi(c.getString(0));
            ee.setTenthucan(c.getString(1));
            ee.setNgaynhap(sdf.parse(Constants.convertDate(c.getString(2))));
            ee.setSoluong(c.getString(3));
            ee.setGiatien(c.getString(4));

            dschiphi.add(ee);
            Log.d("//======", ee.toString());
            c.moveToNext();
        }
        c.close();
        return dschiphi;
    }

    //delete
    public int deleteChiPhi(String machiphi) {
        int result = db.delete(TABLE_NAME, "machiphi=?", new
                String[]{machiphi});
        if (result == 0)
            return -1;
        return 1;
    }

    public int updateChiphi(String machiphi, String tenthucan, Date ngaynhap, String soluong, String giatien) {
        ContentValues values = new ContentValues();
        values.put("machiphi", machiphi);
        values.put("tenthucan", tenthucan);
        values.put("ngaynhap", String.valueOf(ngaynhap));
        values.put("soluong", soluong);
        values.put("giatien", giatien);

        int result = db.update(TABLE_NAME, values, "machiphi=?", new
                String[]{machiphi});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public double getDoanhThuTheoNgay() {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(giatien)  as 'tongtien' FROM ChiPhi where ChiPhi.ngaynhap = strftime('%Y-%m-%d','now','localtime')";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }

        c.close();
        return doanhThu;
    }

    public double getDoanhThuTheoThang() {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(giatien) as 'tongtien' " + "FROM ChiPhi" + " where strftime('%m-%Y',ChiPhi.ngaynhap,'localtime') =  strftime('%m-%Y','now','localtime')";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public double getDoanhThuTheoNam() {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(giatien) as 'tongtien' " +
                "FROM ChiPhi " +
                " where strftime('%Y',ChiPhi.ngaynhap)=strftime('%Y','now')";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getDouble(0);
            c.moveToNext();

        }
        c.close();
        return doanhThu;
    }

}
