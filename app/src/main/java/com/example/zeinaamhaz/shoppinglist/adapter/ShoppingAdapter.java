package com.example.zeinaamhaz.shoppinglist.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import com.example.zeinaamhaz.shoppinglist.MainActivity;
import com.example.zeinaamhaz.shoppinglist.R;
import com.example.zeinaamhaz.shoppinglist.data.Item;


public class ShoppingAdapter extends
        RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivIcon;
        public TextView tvItem;
        public Button btnDelete;
        public Button btnEdit;
        public CheckBox cbItemBought;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvItem = (TextView) itemView.findViewById(R.id.tvItem);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            cbItemBought = (CheckBox) itemView.findViewById(R.id.cbItemBought);

        }
    }

    private List<Item> itemList;
    private Context context;
    private int lastPosition = -1;

    public ShoppingAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,
                                 final int position) {
        viewHolder.tvItem.setText(itemList.get(position).getItemName());
        viewHolder.ivIcon.setImageResource(
                itemList.get(position).getItemType().getIconId());

        viewHolder.cbItemBought.setChecked(itemList.get(position).isBought());

        viewHolder.cbItemBought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = itemList.get(position);
                item.setBought(viewHolder.cbItemBought.isChecked());

                item.save();

            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).showEditItemActivity(itemList.get(position), position);
            }
        });

        setAnimation(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(Item item) {
        item.save();
        itemList.add(item);
        notifyDataSetChanged();
    }

    public void deleteAll() {
        Item.deleteAll(Item.class);
        itemList.clear();
    }

    public void updateItem(int index, Item item) {
        itemList.set(index, item);
        item.save();
        notifyItemChanged(index);
    }

    public void removeItem(int index) {
        itemList.get(index).delete();
        itemList.remove(index);
        notifyDataSetChanged();
    }

    public void swapItems(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }

    public Item getItem(int i) {
        return itemList.get(i);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
