package com.example.zeinaamhaz.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.zeinaamhaz.shoppinglist.data.Item;


public class CreateItemActivity extends AppCompatActivity {
    public static final String KEY_ITEM = "KEY_ITEM";
    private Spinner spinnerItemType;
    private EditText etItem;
    private EditText etItemDesc;
    private EditText etItemPrice;
    private CheckBox cbItemBought;
    private Item itemToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        if (getIntent().getSerializableExtra(MainActivity.KEY_EDIT) != null) {
            itemToEdit = (Item) getIntent().getSerializableExtra(MainActivity.KEY_EDIT);
        }

        spinnerItemType = (Spinner) findViewById(R.id.spinnerItemType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.itemtypes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItemType.setAdapter(adapter);

        etItem = (EditText) findViewById(R.id.etItemName);
        etItemDesc = (EditText) findViewById(R.id.etItemDesc);
        etItemPrice = (EditText) findViewById(R.id.etItemPrice);
        cbItemBought = (CheckBox) findViewById(R.id.cbItemBought);


        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });

        if (itemToEdit != null) {
            etItem.setText(itemToEdit.getItemName());
            etItemDesc.setText(itemToEdit.getDescription());
            etItemPrice.setText(itemToEdit.getPrice());
            cbItemBought.setChecked(itemToEdit.isBought());

            spinnerItemType.setSelection(itemToEdit.getItemType().getValue());
        }
    }

    private void saveItem() {
        Intent intentResult = new Intent();
        Item itemResult = null;
        if (itemToEdit != null) {
            itemResult = itemToEdit;
        } else {
            itemResult = new Item();

        }

        itemResult.setItemName(etItem.getText().toString());
        itemResult.setDescription(etItemDesc.getText().toString());
        itemResult.setPrice(etItemPrice.getText().toString());
        itemResult.setBought(cbItemBought.isChecked());

        itemResult.setItemType(
                Item.ItemType.fromInt(spinnerItemType.getSelectedItemPosition()));

        intentResult.putExtra(KEY_ITEM, itemResult);
        setResult(RESULT_OK, intentResult);
        finish();
    }
}
