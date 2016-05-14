package com.example.zeinaamhaz.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import java.util.List;

import com.example.zeinaamhaz.shoppinglist.adapter.ShoppingAdapter;
import com.example.zeinaamhaz.shoppinglist.data.Item;
import com.example.zeinaamhaz.shoppinglist.touch.ItemListTouchHelperCallback;


public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_NEW_ITEM = 101;
    public static final int REQUEST_EDIT_ITEM = 102;

    public static final String KEY_EDIT = "KEY_EDIT";
    private ShoppingAdapter shoppingAdapter;
    private CoordinatorLayout layoutContent;
    private Item itemToEditHolder;
    private int itemToEditPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Item> itemList = Item.listAll(Item.class);

        shoppingAdapter = new ShoppingAdapter(itemList, this);
        RecyclerView recyclerViewItems = (RecyclerView) findViewById(
                R.id.recyclerViewItems);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItems.setAdapter(shoppingAdapter);

        ItemListTouchHelperCallback touchHelperCallback = new ItemListTouchHelperCallback(
                shoppingAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerViewItems);

        layoutContent = (CoordinatorLayout) findViewById(
                R.id.layoutContent);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateItemActivity();
            }
        });

        setUpToolBar();
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showCreateItemActivity() {
        Intent intentStart = new Intent(MainActivity.this,
                CreateItemActivity.class);
        startActivityForResult(intentStart, REQUEST_NEW_ITEM);
    }


    public void showEditItemActivity(Item itemToEdit, int position) {
        Intent intentStart = new Intent(MainActivity.this,
                CreateItemActivity.class);
        itemToEditHolder = itemToEdit;
        itemToEditPosition = position;

        intentStart.putExtra(KEY_EDIT, itemToEdit);
        startActivityForResult(intentStart, REQUEST_EDIT_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_NEW_ITEM) {
                    Item item = (Item) data.getSerializableExtra(
                            CreateItemActivity.KEY_ITEM);

                    shoppingAdapter.addItem(item);
                    showSnackBarMessage(getString(R.string.txt_item_added));
                } else if (requestCode == REQUEST_EDIT_ITEM) {
                    Item itemTemp = (Item) data.getSerializableExtra(
                            CreateItemActivity.KEY_ITEM);

                    itemToEditHolder.setItemName(itemTemp.getItemName());
                    itemToEditHolder.setDescription(itemTemp.getDescription());
                    itemToEditHolder.setItemType(itemTemp.getItemType());
                    itemToEditHolder.setBought(itemTemp.isBought());


                    if (itemToEditPosition != -1) {
                        shoppingAdapter.updateItem(itemToEditPosition, itemToEditHolder);
                        itemToEditPosition = -1;
                    } else {
                        shoppingAdapter.notifyDataSetChanged();
                    }
                    showSnackBarMessage(getString(R.string.txt_item_edited));
                }
                break;
            case RESULT_CANCELED:
                showSnackBarMessage(getString(R.string.txt_add_cancel));
                break;
        }
    }


    private void showSnackBarMessage(String message) {
        Snackbar.make(layoutContent,
                message,
                Snackbar.LENGTH_LONG
        ).setAction(R.string.action_hide, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_from_toolbar:
                showCreateItemActivity();
                return true;
            case R.id.action_delete_all_from_toolbar:
                shoppingAdapter.deleteAll();
                return true;

        }
        return true;
    }

}

