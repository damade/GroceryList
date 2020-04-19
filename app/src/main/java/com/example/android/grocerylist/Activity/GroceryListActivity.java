package com.example.android.grocerylist.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.grocerylist.Adapter.GroceryRecylerAdapter;
import com.example.android.grocerylist.Database.RoomModel.Grocery;
import com.example.android.grocerylist.R;
import com.example.android.grocerylist.ViewModel.GroceryViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.android.grocerylist.R;


public class GroceryListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private GroceryViewModel groceryViewModel;
    //private List<Grocery> notesList = new ArrayList<>();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final GroceryRecylerAdapter groceryRecylerAdapter = new GroceryRecylerAdapter();
        recyclerView.setAdapter(groceryRecylerAdapter);

        groceryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(GroceryViewModel.class);
        //groceryViewModel = new ViewModelProvider(this, GroceryViewModelFactory).get(GroceryViewModel.class);
        groceryViewModel.getAllGroceries().observe(this, new Observer<List<Grocery>>() {
            @Override
            public void onChanged(List<Grocery> groceries) {
                //Update RecyclerView here
                groceryRecylerAdapter.submitList(groceries);
            }
        });



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        selectNavigationMenuItem(R.id.nav_notes);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                groceryViewModel.delete(groceryRecylerAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(GroceryListActivity.this, "Grocery deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        //initializeDisplayContent();
        groceryRecylerAdapter.setOnItemClickListener(new GroceryRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Grocery grocery) {
                Intent intent = new Intent(GroceryListActivity.this, GroceryActivity.class);
                intent.putExtra(GroceryActivity.ORIGINAL_NOTE_ID, grocery.getNid());
                intent.putExtra(GroceryActivity.ORIGINAL_NOTE_TIME, grocery.getGrocery_Quantity());
                intent.putExtra(GroceryActivity.ORIGINAL_NOTE_TITLE, grocery.getGrocery_Title());
                intent.putExtra(GroceryActivity.ORIGINAL_NOTE_TEXT, grocery.getGrocery_Price());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(GroceryActivity.ORIGINAL_NOTE_TITLE);
            String text = data.getStringExtra(GroceryActivity.ORIGINAL_NOTE_TEXT);
            String time = data.getStringExtra(GroceryActivity.ORIGINAL_NOTE_TIME);

            Grocery grocery = new Grocery(title, text, time);
            groceryViewModel.insert(grocery);
            Toast.makeText(this, "Grocery Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(GroceryActivity.ORIGINAL_NOTE_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Grocery can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(GroceryActivity.ORIGINAL_NOTE_TITLE);
            String text = data.getStringExtra(GroceryActivity.ORIGINAL_NOTE_TEXT);
            String time = data.getStringExtra(GroceryActivity.ORIGINAL_NOTE_TIME);

            Grocery grocery = new Grocery(title, text, time);
            grocery.setNid(id);
            groceryViewModel.update(grocery);
            Toast.makeText(this, "Grocery Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Grocery not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all_notes) {
            groceryViewModel.deleteAllNotes();
            Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final GroceryRecylerAdapter groceryRecylerAdapter = new GroceryRecylerAdapter();
        groceryViewModel.getAllGroceries().observe(this, new Observer<List<Grocery>>() {
            @Override
            public void onChanged(List<Grocery> groceries) {
                //Update RecyclerView here
                groceryRecylerAdapter.submitList(groceries);
            }
        });
    }
//        madapterNotes.notifyDataSetChanged();
//        loadNotes();
//        //mNoteRecylerAdapter.notifyDataSetChanged();



    /*private void loadNotes() {
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
        final String[] notesColumns = {
                NoteKeeperDatabaseContract.NoteInfoEntry.COLUMN_NOTE_TITLE,
                NoteKeeperDatabaseContract.NoteInfoEntry._ID};
        String noteOrderBy = NoteKeeperDatabaseContract.NoteInfoEntry.COLUMN_NOTE_TITLE;
        final Cursor noteQuery =
                db.query(NoteKeeperDatabaseContract.NoteInfoEntry.TABLE_NAME, notesColumns,
                        null, null, null, null, noteOrderBy);
        mNoteRecylerAdapter.changeCursor(noteQuery);
    }

    private void initializeDisplayContent() {
        //DataManager.loadFromDatabase(mDbOpenHelper);
        notesList.addAll(mDbOpenHelper.getAllGroceries());

        mNoteRecylerAdapter = new GroceryRecylerAdapter(this, notesList);
        mNotesLayoutManager = new LinearLayoutManager(this);

        //List<NoteInfo> notes = DataManager.getInstance().getNotes();



        displayNotes();
    }

    private void displayNotes() {
        mRecyclerItems.setLayoutManager(mNotesLayoutManager);
        mRecyclerItems.setAdapter(mNoteRecylerAdapter);


    }*/

    private void selectNavigationMenuItem(int id) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(id).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            //displayNotes();
        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
