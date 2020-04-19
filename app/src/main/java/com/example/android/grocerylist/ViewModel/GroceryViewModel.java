package com.example.android.grocerylist.ViewModel;

import android.app.Application;

import com.example.android.grocerylist.Database.RoomModel.Grocery;
import com.example.android.grocerylist.Repository.GroceryRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class GroceryViewModel extends AndroidViewModel {
    private GroceryRepository repository;
    private LiveData<List<Grocery>> allGroceries;

    public GroceryViewModel(@NonNull Application application) {
        super(application);
        repository = new GroceryRepository(application);
        allGroceries = repository.getAllGroceries();
    }

    public void insert(Grocery grocery) {
        repository.insert(grocery);
    }

    public void update(Grocery grocery) {
        repository.update(grocery);
    }

    public void delete(Grocery grocery) {
        repository.delete(grocery);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Grocery>> getAllGroceries() {
        return allGroceries;
    }
}
