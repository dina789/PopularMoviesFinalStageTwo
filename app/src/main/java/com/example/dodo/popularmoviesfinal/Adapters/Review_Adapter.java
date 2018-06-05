package com.example.dodo.popularmoviesfinal.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.dodo.popularmoviesfinal.Models.ReviewModel;
import com.example.dodo.popularmoviesfinal.R;

import java.util.ArrayList;
import java.util.List;

public class Review_Adapter extends RecyclerView.Adapter<Review_Adapter.ViewHolder> {

    private List<ReviewModel> mreviewList;
    private LayoutInflater mLayoutInflater;

    public void setItems(List<ReviewModel> mreviewList) {
        this.mreviewList = mreviewList;
        notifyDataSetChanged();
    }
    public Review_Adapter(List<ReviewModel> mreviewList, Context context ) {

        this.mLayoutInflater = LayoutInflater.from(context);
        this.mreviewList = new ArrayList<>();
    }




    //onCreateViewHolder() creates a view and returns it.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        //inflate review lost
        View view = mLayoutInflater.inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    //to associates the data with the view holder for a given position in the RecyclerView.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        // Retrieve the data for that position
        ReviewModel reviews = mreviewList.get(position);


        // Add the data to the view
        String reviewAuthor = reviews.getAuthor();

        String reviewContent = reviews.getContent();


        if (mreviewList == null) {
            holder.reviewAuthor.setText("No Review Available");
            holder.reviewContent.setText("");
        }
        holder.reviewAuthor.setText(reviewAuthor);
        holder.reviewContent.setText(reviewContent);

    }


    //  returns to number of data items available for displaying.
    @Override
    public int getItemCount() {

        if (mreviewList == null)

        {


            return -1;

        }
        return mreviewList.size();
    }



    public void setReviewsList(List<ReviewModel> reviewList) {
        this.mreviewList.clear();
        this.mreviewList.addAll(reviewList);
        // The adapter needs to know that the data has changed. If we don't call this, app will crash.
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reviewAuthor;
        TextView reviewContent;

        public ViewHolder(View itemView) {
            super(itemView);
            reviewAuthor = (TextView) itemView.findViewById(R.id.author);
            reviewContent = (TextView) itemView.findViewById(R.id.content);
        }

    }}