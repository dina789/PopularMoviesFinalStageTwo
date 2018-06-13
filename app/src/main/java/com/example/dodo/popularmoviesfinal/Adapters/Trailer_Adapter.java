package com.example.dodo.popularmoviesfinal.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dodo.popularmoviesfinal.Activities.Details_Activity;
import com.example.dodo.popularmoviesfinal.Models.VideoModel;
import com.example.dodo.popularmoviesfinal.R;

import java.util.ArrayList;
import java.util.List;

public class Trailer_Adapter extends RecyclerView.Adapter <Trailer_Adapter.ViewHolder>{

    List<VideoModel> trailer;
    private Context mContext;

    public Trailer_Adapter(Context mContext) {
        this.mContext= mContext;
        this.trailer = new ArrayList<>();
    }

    //onCreateViewHolder() creates a view and returns it.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        //inflate review lost
        View view = LayoutInflater.from(mContext).inflate(R.layout.trailer_item, parent, false);
        return new ViewHolder(view);
    }

    //to associates the data with the view holder for a given position in the RecyclerView.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder. tv_trailer_title.setText(trailer.get(position).getName());
    }


    //  returns to number of data items available for displaying.
    @Override
    public int getItemCount() {

        if (trailer == null)

        {
            return -1;

        }
        return trailer.size();
    }

    public void updateTrailers(List<VideoModel> videoModels) {
        this.trailer = videoModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_trailer_title;

        ImageView iv_trailers;

        public ViewHolder(final View itemView) {
            super(itemView);
            iv_trailers = (ImageView) itemView.findViewById(R.id.iv_trailers);
            tv_trailer_title = (TextView) itemView.findViewById(R.id.tv_trailer_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)

                    {

                        String videoId = trailer.get(pos).getKey();
                        ;
                        //to open utube intent


                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoId));
                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("vnd.youtube://"+ videoId));
                        try {
                            mContext.startActivity(appIntent);
                        } catch (ActivityNotFoundException ex) {
                            mContext.startActivity(webIntent);
                        }
                    }

                }

            });

        }
    }}



