package eu.darken.myolib.exampleapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuHurufAdapter extends RecyclerView.Adapter<MenuHurufAdapter.BelajarViewHolder> {
    private ArrayList<HurufItem> hurufItems;
    private final Context context;

    public static class BelajarViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;

        public BelajarViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_menu_belajar);
        }
    }

    public MenuHurufAdapter(ArrayList<HurufItem> hurufItems, Context context) {
        this.hurufItems = hurufItems;
        this.context = context;
    }

    @NonNull
    @Override
    public BelajarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        BelajarViewHolder bvh = new BelajarViewHolder(view);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BelajarViewHolder holder, int position) {
        final HurufItem currentItem =  hurufItems.get(position);

        holder.textView.setText("Huruf " + currentItem.getHuruf());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoViewActivity.class);
                intent.putExtra("RAW_FILE", currentItem.getRawFile());
                intent.putExtra("HURUF", currentItem.getHuruf());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (hurufItems != null) ? hurufItems.size() : 0;
    }

}
