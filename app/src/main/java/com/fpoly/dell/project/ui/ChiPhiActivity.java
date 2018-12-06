package com.fpoly.dell.project.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.fpoly.dell.project.adapter.ChiPhiAdapter;
import com.fpoly.dell.project.dao.ChiPhiDao;
import com.fpoly.dell.project.model.ChiPhi;
import com.fpoly.dell.project1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChiPhiActivity extends AppCompatActivity {
    private List<ChiPhi> dsChiPhi = new ArrayList<>();
    private ListView lvChiPhi;
    private ChiPhiAdapter adapter = null;
    private ChiPhiDao chiPhiDao;
    private FloatingActionButton fab;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_phi);
        setTitle("Chi phí");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        lvChiPhi = findViewById(R.id.lv_chiphi);

        fab = findViewById(R.id.fabAdd);


        chiPhiDao = new ChiPhiDao(ChiPhiActivity.this);
        try {
            dsChiPhi = chiPhiDao.getAllChiPhi();
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
        }
        adapter = new ChiPhiAdapter(this, dsChiPhi);
        lvChiPhi.setAdapter(adapter);

        lvChiPhi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChiPhiActivity.this, ChiTietChiPhiActivity.class);
                Bundle b = new Bundle();

                b.putString("MACHIPHI", dsChiPhi.get(position).getMachiphi());
                b.putString("TENTHUCAN", dsChiPhi.get(position).getTenthucan());
                b.putString("NGAYNHAP", sdf.format((dsChiPhi.get(position).getNgaynhap())));
                b.putString("SOLUONG", dsChiPhi.get(position).getSoluong());
                b.putString("GIATIEN", dsChiPhi.get(position).getGiatien());

                intent.putExtras(b);
                startActivity(intent);
            }
        });


        // TextFilter
        lvChiPhi.setTextFilterEnabled(true);
        EditText edSeach = findViewById(R.id.edSearch);
        edSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - " +
                        "Before [" + before + "] - Count [" + count + "]");
                if (count < before) {
                    adapter.resetData();
                }
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(ChiPhiActivity.this, ThemChiPhiActivity.class);
                startActivity(a);
            }
        });
    }
}
