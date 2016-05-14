package com.example.zeinaamhaz.shoppinglist.data;

import com.orm.SugarRecord;

import java.io.Serializable;

import com.example.zeinaamhaz.shoppinglist.R;


public class Item extends SugarRecord<Item> implements Serializable {
    public enum ItemType {
        FOOD(0, R.drawable.food),
        CLOTHES(1, R.drawable.clothes), BOOK(2, R.drawable.book);

        private int value;
        private int iconId;

        private ItemType(int value, int iconId) {
            this.value = value;
            this.iconId = iconId;
        }

        public int getValue() {
            return value;
        }

        public int getIconId() {
            return iconId;
        }

        public static ItemType fromInt(int value) {
            for (ItemType i : ItemType.values()) {
                if (i.value == value) {
                    return i;
                }
            }
            return FOOD;
        }
    }

    private String itemName;
    private String description;
    private String price;
    private boolean bought;
    private ItemType itemType;

    public Item() {

    }

    public Item(String itemName, String description, String price, boolean bought, ItemType itemType) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.itemType = itemType;
        this.bought = bought;

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
