package com.braintri.tumblr.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.braintri.tumblr.R;
import com.braintri.tumblr.model.Post;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mike on 25.01.16.
 */
public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {

    private ArrayList<Post> itemsData;

    public PostListAdapter(ArrayList<Post> itemsData) {
        this.itemsData = itemsData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PostListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_layout, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        // - replace the contents of the view with itemsData

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(itemsData.get(position).getPhotoUrl(), new BinaryHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                Bitmap image = BitmapFactory.decodeByteArray(binaryData, 0, binaryData.length);
                viewHolder.imgViewPhoto.setImageBitmap(image);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {

            }
        });

        viewHolder.txtViewPhotoCaption.setText(itemsData.get(position).getPhotoCaption());
        String viewTags = "";
        if (itemsData.get(position).getTags() != null){
            for (int i=0;i<itemsData.get(position).getTags().size();i++) {
                viewTags = viewTags + "#"+itemsData.get(position).getTagsByIndex(i)+" ";
                viewHolder.txtViewTags.setText(viewTags);
            }
        }

    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder  {

        public ImageView imgViewPhoto;
        public TextView txtViewPhotoCaption;
        public TextView txtViewTags;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            imgViewPhoto = (ImageView) itemLayoutView.findViewById(R.id.post_photo);
            txtViewPhotoCaption = (TextView) itemLayoutView.findViewById(R.id.post_photo_caption);
            txtViewTags = (TextView) itemLayoutView.findViewById(R.id.post_tags);
        }
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }

}


