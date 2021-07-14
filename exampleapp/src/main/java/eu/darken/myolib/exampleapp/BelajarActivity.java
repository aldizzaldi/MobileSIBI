package eu.darken.myolib.exampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class BelajarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuHurufAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belajar);
        getSupportActionBar().setTitle("Menu Belajar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayList<HurufItem> hurufItems = new ArrayList<>();

        hurufItems.add(new HurufItem(1,"A", R.raw.a));
        hurufItems.add(new HurufItem(2,"B", R.raw.b));
        hurufItems.add(new HurufItem(3,"C", R.raw.c));
        hurufItems.add(new HurufItem(4,"D", R.raw.d));
        hurufItems.add(new HurufItem(5,"E", R.raw.e));
        hurufItems.add(new HurufItem(6,"F", R.raw.f));
        hurufItems.add(new HurufItem(7,"G", R.raw.g));
        hurufItems.add(new HurufItem(8,"H", R.raw.h));
        hurufItems.add(new HurufItem(9,"I", R.raw.i));
        hurufItems.add(new HurufItem(10,"J", R.raw.j));
        hurufItems.add(new HurufItem(11,"K", R.raw.k));
        hurufItems.add(new HurufItem(12,"L", R.raw.l));
        hurufItems.add(new HurufItem(13,"M", R.raw.m));
        hurufItems.add(new HurufItem(14,"N", R.raw.n));
        hurufItems.add(new HurufItem(15,"O", R.raw.o));
        hurufItems.add(new HurufItem(16,"P", R.raw.p));
        hurufItems.add(new HurufItem(17,"Q", R.raw.q));
        hurufItems.add(new HurufItem(18,"R", R.raw.r));
        hurufItems.add(new HurufItem(19,"S", R.raw.s));
        hurufItems.add(new HurufItem(20,"T", R.raw.t));
        hurufItems.add(new HurufItem(21,"U", R.raw.u));
        hurufItems.add(new HurufItem(22,"V", R.raw.v));
        hurufItems.add(new HurufItem(23,"W", R.raw.w));
        hurufItems.add(new HurufItem(24,"X", R.raw.x));
        hurufItems.add(new HurufItem(25,"Y", R.raw.y));
        hurufItems.add(new HurufItem(26,"Z", R.raw.z));

        recyclerView = findViewById(R.id.rv_belajar);
        adapter = new MenuHurufAdapter(hurufItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
