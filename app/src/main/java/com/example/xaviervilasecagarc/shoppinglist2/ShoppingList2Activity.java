package com.example.xaviervilasecagarc.shoppinglist2;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingList2Activity extends AppCompatActivity {

    private ArrayList<ShoppingItem> itemlist;
    private ShoppingListAdapter adapter;
    private ListView list;
    private Button addButton;
    private EditText editItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list2);

        list = (ListView)findViewById(R.id.list);
        addButton = (Button)findViewById(R.id.button_add);
        editItem = (EditText)findViewById(R.id.editItem);

        itemlist= new ArrayList<>();
        int recursos[] = {
            R.string.pizza, R.string.potatoes, R.string.chestnust,
        };
        for (int i = 0; i < recursos.length; i++) {
            itemlist.add(new ShoppingItem(getString(recursos[i])));
        }

        adapter = new ShoppingListAdapter(this,android.R.layout.simple_list_item_1, itemlist);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        editItem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                addItem();
                return false;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                itemlist.get(pos).toggleCheck();
                adapter.notifyDataSetChanged();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> list, View item, int pos, long id) {
                maybeRemobeItem(pos);
                return false;
            }
        });

        list.setAdapter(adapter);

    }

    private void maybeRemobeItem(final int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        String fmt = getResources().getString(R.string.confirm_message);
        builder.setMessage(String.format(fmt,itemlist.get(pos).getText()));
        builder.setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemlist.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();


    }

    private void addItem() {
        String item_text = editItem.getText().toString();
        if (!item_text.isEmpty()) {
            itemlist.add(new ShoppingItem(item_text));
            adapter.notifyDataSetChanged();
            editItem.setText("");
        }
        list.smoothScrollToPosition(itemlist.size()-1);
    }
}
