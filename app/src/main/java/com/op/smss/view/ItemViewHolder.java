package com.op.smss.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.op.smss.background.ItemDataBase;
import com.op.smss.R;

class ItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView key;
    private final TextView val;
    private final Button btn;
    ItemDataBase itemDataBase;

    private ItemViewHolder(View itemView) {
        super(itemView);
        key = itemView.findViewById(R.id.textView);
        val = itemView.findViewById(R.id.val);
        btn = itemView.findViewById(R.id.btnd);
        itemDataBase = ItemDataBase.getDatabase(itemView.getContext());
    }

    public void bind(String k,String value) {
        key.setText(k);
        val.setText(value);
    }

    static ItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(view);
    }
}
