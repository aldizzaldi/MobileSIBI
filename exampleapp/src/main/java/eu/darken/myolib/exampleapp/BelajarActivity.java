package eu.darken.myolib.exampleapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class BelajarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belajar);
        getSupportActionBar().setTitle("Menu Belajar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayList<BelajarItem> belajarItems = new ArrayList<>();

        belajarItems.add(new BelajarItem(1,"A", R.raw.a,0));
        belajarItems.add(new BelajarItem(2,"B", R.raw.b,0));
        belajarItems.add(new BelajarItem(3,"C", R.raw.c,0));
        belajarItems.add(new BelajarItem(4,"D", R.raw.d,0));
        belajarItems.add(new BelajarItem(5,"E", R.raw.e,0));
        belajarItems.add(new BelajarItem(6,"F", R.raw.f,0));
        belajarItems.add(new BelajarItem(7,"G", R.raw.g,0));
        belajarItems.add(new BelajarItem(8,"H", R.raw.h,0));
        belajarItems.add(new BelajarItem(9,"I", R.raw.i,0));
        belajarItems.add(new BelajarItem(10,"J", R.raw.j,0));
        belajarItems.add(new BelajarItem(11,"K", R.raw.k,0));
        belajarItems.add(new BelajarItem(12,"L", R.raw.l,0));
        belajarItems.add(new BelajarItem(13,"M", R.raw.m,0));
        belajarItems.add(new BelajarItem(14,"N", R.raw.n,0));
        belajarItems.add(new BelajarItem(15,"O", R.raw.o,0));
        belajarItems.add(new BelajarItem(16,"P", R.raw.p,0));
        belajarItems.add(new BelajarItem(17,"Q", R.raw.q,0));
        belajarItems.add(new BelajarItem(18,"R", R.raw.r,0));
        belajarItems.add(new BelajarItem(19,"S", R.raw.s,0));
        belajarItems.add(new BelajarItem(20,"T", R.raw.t,0));
        belajarItems.add(new BelajarItem(21,"U", R.raw.u,0));
        belajarItems.add(new BelajarItem(22,"V", R.raw.v,0));
        belajarItems.add(new BelajarItem(23,"W", R.raw.w,0));
        belajarItems.add(new BelajarItem(24,"X", R.raw.x,0));
        belajarItems.add(new BelajarItem(25,"Y", R.raw.y,0));
        belajarItems.add(new BelajarItem(26,"Z", R.raw.z,0));

        recyclerView = findViewById(R.id.rv_belajar);
        adapter = new MenuItemAdapter(belajarItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
