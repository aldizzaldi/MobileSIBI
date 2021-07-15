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

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.BelajarViewHolder> {
    private ArrayList<BelajarItem> belajarItems;
    private final Context context;

    public static class BelajarViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;

        public BelajarViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_menu_belajar);
        }
    }

    public MenuItemAdapter(ArrayList<BelajarItem> belajarItems, Context context) {
        this.belajarItems = belajarItems;
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
        final BelajarItem currentItem =  belajarItems.get(position);
        if (currentItem.getTanda() == 0)
            holder.textView.setText("Huruf " + currentItem.getItem());
        else
            holder.textView.setText("Kata " + currentItem.getItem());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoViewActivity.class);
                intent.putExtra("RAW_FILE", currentItem.getRawFile());
                intent.putExtra("ITEM", currentItem.getItem());
                intent.putExtra("TANDA", currentItem.getTanda());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (belajarItems != null) ? belajarItems.size() : 0;
    }

}
