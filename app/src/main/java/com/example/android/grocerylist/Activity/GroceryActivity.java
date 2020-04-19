package com.example.android.grocerylist.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.grocerylist.Database.RoomModel.Grocery;
import com.example.android.grocerylist.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GroceryActivity extends AppCompatActivity {
    // public static final String NOTES_ID  = NoteListActivity.NOTES_ID;

    public static final String NOTES_ID = "com.example.android.notekeeper.NOTES_ID";
    public static final String ORIGINAL_NOTE_TEXT = "com.example.android.notekeeper.ORIGINAL_NOTE_TEXT";
    public static final String ORIGINAL_NOTE_ID = "com.example.android.notekeeper.ORIGINAL_NOTE_ID";
    public static final String ORIGINAL_NOTE_TITLE = "com.example.android.notekeeper.ORIGINAL_NOTE_TITLE";
    public static final String ORIGINAL_NOTE_TIME = "com.example.android.notekeeper.ORIGINAL_NOTE_TIME";
    public static final int ID_NOT_SET = -1;
    private static final String TAG = "SaveNote";

    private List<Grocery> notesList = new ArrayList<>();
    private Spinner groceryCategory;
    private EditText textGroceryTitle;
    private EditText textGroceryPrice;
    private TextView textGroceryQuantity;



    protected void onDestroy() {
        super.onDestroy();
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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groceryCategory.setAdapter(arrayAdapter);


        Intent intent = getIntent();
        if (intent.hasExtra(ORIGINAL_NOTE_ID)) {
            setTitle(R.string.edit_activity_note);
            textGroceryTitle.setText(intent.getStringExtra(ORIGINAL_NOTE_TITLE));
            textGroceryPrice.setText(intent.getStringExtra(ORIGINAL_NOTE_TEXT));
            textGroceryQuantity.setText(intent.getStringExtra(ORIGINAL_NOTE_TIME));
        } else {
            setTitle(R.string.add_activity_note);
            groceryCategory.setEnabled(false);
            textGroceryTitle.setEnabled(false);
            textGroceryTitle.setFocusable(false);
            textGroceryPrice.setEnabled(false);
            textGroceryPrice.setFocusable(false);
            textGroceryQuantity.setEnabled(false);
            textGroceryQuantity.setFocusable(false);

        }
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
            //saveNote();
            Log.e(TAG, "it didn't work");
            return true;
        } else if (myId == R.id.action_send_mail) {
            share();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /*private void saveNote() {
        String title = textGroceryTitle.getText().toString();
        String text = textGroceryPrice.getText().toString();
        String date;



        if (title.trim().isEmpty() || text.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a non-empty title and note", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(ORIGINAL_NOTE_TITLE, title);
        data.putExtra(ORIGINAL_NOTE_TEXT, text);
        data.putExtra(ORIGINAL_NOTE_TIME, date);

        int id = getIntent().getIntExtra(ORIGINAL_NOTE_ID, -1);
        if (id != -1) {
            data.putExtra(ORIGINAL_NOTE_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }*/

    private void share() {
        String subject = textGroceryTitle.getText().toString();
        String text = "\"" + textGroceryPrice.getText().toString() + "\"\n";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);
    }
    public void activateViews(View view) {
        groceryCategory.setEnabled(true);
        textGroceryTitle.setEnabled(true);
        textGroceryTitle.setFocusable(true);
        textGroceryPrice.setEnabled(true);
        textGroceryPrice.setFocusable(true);
        textGroceryQuantity.setEnabled(true);
        textGroceryQuantity.setFocusable(true);
    }

    public void activateDataFormDialog(View view) {
    }


    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_next);
        int mNotePostion = getIntent().getIntExtra(ORIGINAL_NOTE_ID, -1);
        int lastNoteIndex = notesList.size();
        if (mNotePostion >= lastNoteIndex) {
            item.setEnabled(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void moveNext() {
        saveNote();

        int mNotePostion = getIntent().getIntExtra(ORIGINAL_NOTE_ID, -1);
        mNotePostion += 1;
        Grocery mGrocery = notesList.get(mNotePostion);
        textGroceryTitle.setText(mGrocery.getGrocery_Title());
        textGroceryPrice.setText(mGrocery.getNote_Text());
        textGroceryQuantity.setText(formatDate(mGrocery.getGrocery_Quantity()));
        invalidateOptionsMenu();
    }





    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ORIGINAL_NOTE_COURSE_ID, mOriginalNoteCourseId);
        outState.putString(ORIGINAL_NOTE_TEXT, mOriginalNoteText);
        outState.putString(ORIGINAL_NOTE_TITLE, mOriginalNoteTitle);
    }*/

   /* private void storePreviousValues() {
        mNote.setText(mOriginalNoteText);
        mNote.setTitle(mOriginalNoteTitle);
    }



    private void displayNotes() {
        String noteTitle = noteCursor.getString(noteTitlePos);
        String noteText = noteCursor.getString(noteTextPos);
        String courseId = noteCursor.getString(noteIdPos);

        textGroceryPrice.setText(noteText);
        textGroceryTitle.setText(noteTitle);
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        mNoteId = intent.getIntExtra(NOTES_ID, ID_NOT_SET);
        mIsNewNote = mNoteId == ID_NOT_SET;
        if(mIsNewNote) {
            //createNewNote();
        }
        /*else {
            mNote = DataManager.getInstance().getNotes().get(mNoteId);
        }*/


    /*private void createNewNote() {
        DataManager dm = DataManager.getInstance();
        mNotePostion = dm.createNewNote();
        mNote = dm.getNotes().get(mNotePostion);
    }*/
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grocery, menu);
        return true;
    }  */


    /**/
}
