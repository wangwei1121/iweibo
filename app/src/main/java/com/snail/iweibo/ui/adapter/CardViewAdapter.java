package com.snail.iweibo.ui.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snail.iweibo.R;
import com.snail.iweibo.ui.adapter.CardViewAdapter.ViewHolder;

import java.util.List;

/**
 * Created by alexwan on 16/1/30.
 */
public class CardViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<String> titles;
    private Context context;
    public CardViewAdapter(Context context , List<String> titles) {
        this.context = context;
        this.titles = titles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_main_card , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(titles.get(position));
    }


    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title_text);
        }
    }

    public void setTitles(List<String> titles){
        this.titles = titles;
    }
}
