package com.example.android.grocerylist.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.android.grocerylist.Database.DataAccessObject.GroceryDao;
import com.example.android.grocerylist.Database.GroceryDatabase;
import com.example.android.grocerylist.Database.RoomModel.Grocery;

import java.util.List;

import androidx.lifecycle.LiveData;

public class GroceryRepository {
    private GroceryDao groceryDao;
    private LiveData<List<Grocery>> allGroceries;

    public GroceryRepository(Application application) {
        GroceryDatabase database = GroceryDatabase.getInstance(application);
        groceryDao = database.groceryDao();
        allGroceries = groceryDao.getAllGroceries();
    }

    public void insert(Grocery grocery) {
        new InsertNoteAsyncTask(groceryDao).execute(grocery);
    }

    public void update(Grocery grocery) {
        new UpdateNoteAsyncTask(groceryDao).execute(grocery);
    }

    public void delete(Grocery grocery) {
        new DeleteNoteAsyncTask(groceryDao).execute(grocery);
    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsyncTask(groceryDao).execute();
    }

    public LiveData<List<Grocery>> getAllGroceries() {
        return allGroceries;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Grocery, Void, Void> {
        private GroceryDao groceryDao;

        private InsertNoteAsyncTask(GroceryDao groceryDao) {
            this.groceryDao = groceryDao;
        }

        @Override
        protected Void doInBackground(Grocery... groceries) {
            groceryDao.insert(groceries[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Grocery, Void, Void> {
        private GroceryDao groceryDao;

        private UpdateNoteAsyncTask(GroceryDao groceryDao) {
            this.groceryDao = groceryDao;
        }

        @Override
        protected Void doInBackground(Grocery... groceries) {
            groceryDao.update(groceries[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Grocery, Void, Void> {
        private GroceryDao groceryDao;

        private DeleteNoteAsyncTask(GroceryDao groceryDao) {
            this.groceryDao = groceryDao;
        }

        @Override
        protected Void doInBackground(Grocery... groceries) {
            groceryDao.delete(groceries[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private GroceryDao groceryDao;

        private DeleteAllNoteAsyncTask(GroceryDao groceryDao) {
            this.groceryDao = groceryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            groceryDao.deleteAllGroceries();
            return null;
        }
    }


}
