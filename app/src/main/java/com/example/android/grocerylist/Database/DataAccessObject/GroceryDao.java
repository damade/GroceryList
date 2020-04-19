package com.example.android.grocerylist.Database.DataAccessObject;

import com.example.android.grocerylist.Database.RoomModel.Grocery;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface GroceryDao {

    @Insert
    void insert(Grocery grocery);

    @Update
    void update(Grocery grocery);

    @Delete
    void delete(Grocery grocery);

    @Query("SELECT * FROM grocery_table ORDER BY grocery_title DESC")
    LiveData<List<Grocery>> getAllGroceries();

    /*@Query("SELECT * FROM note_table ORDER BY time_stamp")
    LiveData<List<Grocery>> getAllGroceries();*/

    @Query("DELETE FROM grocery_table")
    void deleteAllGroceries();

    @Query("SELECT * FROM grocery_table WHERE nid IN (:noteIds)")
    LiveData<List<Grocery>> loadAllByIds(int[] noteIds);

    @Query("SELECT * FROM grocery_table WHERE grocery_title LIKE :title LIMIT 1")
    LiveData<List<Grocery>> findByTitle(String title);


}
