package com.example.projectfive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/**
 * Class used to manage the ArrayList Adapter for the RecyclerView
 *
 * @author Oliver Rzepecki, Tom Linteau
 */
public class RecyclerArrayListAdapter extends RecyclerView.Adapter<RecyclerArrayListAdapter.ViewHolder> {

    private ArrayList<Donut> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    /**
     * Used to pass data into the constructor
     * @param context
     * @param data The list of donuts being displayed
     */
    RecyclerArrayListAdapter(Context context, ArrayList<Donut> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    /**
     * Inflates the row layout from xml when needed
     * @param parent -
     * @param viewType The view type
     * @return Returns new layout
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds data to the TextView in each row
     * @param holder The row to be set
     * @param position The donut
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Donut donut = mData.get(position);
        holder.myTextView.setText(donut.toString());
    }

    /**
     * Retrieves the amount of items
     * @return The amount of items
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * Stores and recycles views as they are scrolled off screen
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.donutName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    /**
     * Allows clicks events to be caught
     * @param itemClickListener -
     */
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * Parent activity implements this method to respond to click events
     */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}