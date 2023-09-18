package com.op.smss.view;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.op.smss.background.ItemDataBase;
import com.op.smss.model.Items;
import com.op.smss.R;

public class ItemListAdapter  extends ListAdapter<Items, ItemViewHolder> {

    public ItemListAdapter(@NonNull DiffUtil.ItemCallback<Items> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    ItemDataBase itemDataBase;
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Items current = getItem(position);

        holder.bind(current.getKey(),current.getValue());
        itemDataBase = ItemDataBase.getDatabase(holder.itemView.getContext());
        holder.itemView.findViewById(R.id.btnd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemDataBase.smswordDao().deleteItem(current.getKey());
                Log.i("delete",itemDataBase.smswordDao().getItems().toString());
            }
        });

        holder.itemView.findViewById(R.id.btne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), EditItem.class);
                intent.putExtra("k", current.getKey());
                intent.putExtra("v", current.getValue());
                startActivity(holder.itemView.getContext(),intent,intent.getExtras());

            }
        });


    }

    public static class WordDiff extends DiffUtil.ItemCallback<Items> {

        @Override
        public boolean areItemsTheSame(@NonNull Items oldItem, @NonNull Items newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Items oldItem, @NonNull Items newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }
}
