package com.example.android.grocerylist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.grocerylist.Database.RoomModel.Grocery;
import com.example.android.grocerylist.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class GroceryRecylerAdapter extends ListAdapter<Grocery, GroceryRecylerAdapter.ViewHolder> {
    private OnItemClickListener listener;

    public GroceryRecylerAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Grocery> DIFF_CALLBACK = new DiffUtil.ItemCallback<Grocery>() {
        @Override
        public boolean areItemsTheSame(@NonNull Grocery oldItem, @NonNull Grocery newItem) {
            return oldItem.getNid() == newItem.getNid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Grocery oldItem, @NonNull Grocery newItem) {
            return oldItem.getGrocery_Title().equals(newItem.getGrocery_Title()) &&
                    oldItem.getGrocery_Price() == newItem.getGrocery_Price() &&
                    oldItem.getGrocery_Quantity() == newItem.getGrocery_Quantity();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note_grocery, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Grocery currentGrocery = getItem(position);
        String groceryTitle = currentGrocery.getGrocery_Title();
        int groceryQuantity = currentGrocery.getGrocery_Quantity();
        int groceryPrice = currentGrocery.getGrocery_Price();
        holder.mTextTitle.setText(groceryTitle);
        holder.mGroceryPrice.setText(String.valueOf(groceryPrice));
        holder.mGroceryQuantity.setText(String.valueOf(groceryQuantity));
    }

    public Grocery getNoteAt(int position) {
        return getItem(position);
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */



    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Grocery grocery);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTextTitle;
        public final TextView mGroceryPrice;
        public final TextView mGroceryQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.text_title);
            mGroceryPrice = itemView.findViewById(R.id.text_price);
            mGroceryQuantity = itemView.findViewById(R.id.text_each_quantity);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });*/

        }
    }
}
