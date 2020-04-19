package com.example.android.grocerylist.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.grocerylist.Database.RoomModel.Grocery;
import com.example.android.grocerylist.R;
import com.example.android.grocerylist.ViewModel.GroceryViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

public class GroceryActivity extends AppCompatActivity {
    // public static final String NOTES_ID  = NoteListActivity.NOTES_ID;

    public static final String NOTES_ID = "com.example.android.notekeeper.NOTES_ID";
    public static final String ORIGINAL_GROCERY_PRICE = "com.example.android.notekeeper.ORIGINAL_GROCERY_PRICE";
    public static final String ORIGINAL_NOTE_ID = "com.example.android.notekeeper.ORIGINAL_NOTE_ID";
    public static final String ORIGINAL_GROCERY_TITLE = "com.example.android.notekeeper.ORIGINAL_GROCERY_TITLE";
    public static final String ORIGINAL_GROCERY_QUANTITY = "com.example.android.notekeeper.ORIGINAL_GROCERY_QUANTITY";
    public static final int ID_NOT_SET = -1;
    private static final String TAG = "SaveNote";
    private int quantity;

    private List<Grocery> notesList = new ArrayList<>();
    private Spinner groceryCategory;
    private EditText textGroceryTitle;
    private EditText textGroceryPrice;
    private TextView textGroceryQuantity;
    private GroceryViewModel groceryViewModel;

    protected void onDestroy() {
        super.onDestroy();
        /*groceryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(GroceryViewModel.class);
        groceryViewModel.deleteAllNotes();
        finish();
        System.exit(0);*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        groceryCategory = findViewById(R.id.grocery_category);
        textGroceryTitle = findViewById(R.id.text_grocery_title);
        textGroceryPrice = findViewById(R.id.text_grocery_price);
        textGroceryQuantity = findViewById(R.id.text_grocery_quantity);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("FOOD");
        arrayList.add("BABY PRODUCTS");
        arrayList.add("Cleaning/Cosmetics");
        arrayList.add("Hygiene");
        arrayList.add("Beer, Wine and Spirits");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groceryCategory.setAdapter(arrayAdapter);

        //
        groceryCategory.setEnabled(false);
        textGroceryTitle.setEnabled(false);
        textGroceryPrice.setEnabled(false);
        textGroceryQuantity.setEnabled(false);

        final Button buttonPlus = findViewById(R.id.button_plus);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (textGroceryQuantity.getText().toString().isEmpty()) {
                    quantity = 0;
                } else {
                    quantity = Integer.parseInt(textGroceryQuantity.getText().toString());
                }
                if (quantity == 100) {
                    Context context = getApplicationContext();
                    CharSequence text = "You cannot have more than 100 coffees";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return;
                }
                quantity += 1;
                displayQuantity(quantity);// Code here executes on main thread after user presses button
            }
        });

        final Button buttonMinus = findViewById(R.id.button_minus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (textGroceryQuantity.getText().toString().isEmpty()) {
                    quantity = 0;
                } else {
                    quantity = Integer.parseInt(textGroceryQuantity.getText().toString());
                }
                if (quantity == 0) {
                    Context context = getApplicationContext();
                    CharSequence text = "You cannot have less than 1 coffee";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return;
                }
                quantity -= 1;
                displayQuantity(quantity);

            }
        });
    }

    private void displayQuantity(int number) {
        textGroceryQuantity.setText(String.valueOf(number));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grocery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int myId = item.getItemId();
        if (myId == R.id.action_save) {
            saveNote();
            Log.e(TAG, "it didn't work");
            return true;
        } else if (myId == R.id.action_send_mail) {
            share();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = textGroceryTitle.getText().toString();
        String priceText = textGroceryPrice.getText().toString();
        String quantityText = textGroceryQuantity.getText().toString();


        if (title.trim().isEmpty() || priceText.trim().isEmpty() || quantityText.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a non-empty title and note", Toast.LENGTH_SHORT).show();
            return;
        }

        int price = Integer.parseInt(textGroceryPrice.getText().toString());
        int quantity = Integer.parseInt(textGroceryQuantity.getText().toString());
        groceryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(GroceryViewModel.class);
        Grocery grocery = new Grocery(title, price, quantity);
        groceryViewModel.insert(grocery);
        Toast.makeText(this, "Grocery Saved", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(GroceryActivity.this, GroceryActivity.class);
//
//        startActivity(intent);
        finish();
        startActivity(getIntent());

    }

    private void share() {
        String item = textGroceryTitle.getText().toString();
        String subject = "Grocery to  purchase: ";
        String price = textGroceryPrice.getText().toString();
        String quantity = textGroceryQuantity.getText().toString();
        String totalPrice = String.valueOf((Integer.parseInt(price)) * (Integer.parseInt(quantity)));
        String finalText = "You want to purchase " + quantity + " " + item + " at " + price + " each "
                + "which cost #" + totalPrice;
        Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.setType("*/*");
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, finalText);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void activateViews(View view) {
        groceryCategory.setEnabled(true);
        textGroceryTitle.setEnabled(true);
        textGroceryPrice.setEnabled(true);
        textGroceryQuantity.setEnabled(true);
    }

    public void activateDataFormDialog(View view) {
        DataRequestFormClass cdd = new DataRequestFormClass(this);
        cdd.show();
    }

    public void saveEachGrocery(View view) {
        saveNote();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
