package com.example.android.grocerylist.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
    public static final String ORIGINAL_CONTACT_NAME = "com.example.android.notekeeper.ORIGINAL_CONTACT_NAME";
    public static final String ORIGINAL_CONTACT_EMAIL = "com.example.android.notekeeper.ORIGINAL_CONTACT_EMAIL";
    public static final String ORIGINAL_CONTACT_PHONENUMBER = "com.example.android.notekeeper.ORIGINAL_CONTACT_PHONENUMBER";

    private TextView profileName;
    private TextView profileEmail;
    private TextView profilePhoneNumber;
    private TextView textNameNavBar;
    private TextView textEmailNavBar;
    private TextView textPhoneNumberNavBar;
    private TextView textTotalAmount;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //System.exit(0);
        //finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profileName = findViewById(R.id.text_name);
        profileEmail = findViewById(R.id.text_email);
        profilePhoneNumber = findViewById(R.id.text_phone_number);
        textTotalAmount = findViewById(R.id.total_amount);

        Intent intent = getIntent();
        profileName.setText(intent.getStringExtra(ORIGINAL_CONTACT_NAME));
        profileEmail.setText(intent.getStringExtra(ORIGINAL_CONTACT_EMAIL));
        profilePhoneNumber.setText(intent.getStringExtra(ORIGINAL_CONTACT_PHONENUMBER));

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

        /*LiveData<List<Grocery>> allGroceries  = groceryViewModel.getAllGroceries();
        int size = allGroceries.
        int Count = 0;
        for(int i = 0; i < size;i++){
            Grocery grocery = allGroceries.get(i);
            int amount = grocery.getGrocery_Price() * grocery.getGrocery_Quantity();
            Count += amount;
        }
        textTotalAmount.setText(String.valueOf(Count));*/


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View myNavView = navigationView.getHeaderView(0);
        textNameNavBar = myNavView.findViewById(R.id.nav_name);
        textEmailNavBar = myNavView.findViewById(R.id.nav_email);
        textPhoneNumberNavBar = myNavView.findViewById(R.id.nav_phone_number);

        textNameNavBar.setText(intent.getStringExtra(ORIGINAL_CONTACT_NAME));
        textEmailNavBar.setText(intent.getStringExtra(ORIGINAL_CONTACT_EMAIL));
        textPhoneNumberNavBar.setText(intent.getStringExtra(ORIGINAL_CONTACT_PHONENUMBER));


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

    /*@Override
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
            onBackPressed();
        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
