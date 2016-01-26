package com.braintri.tumblr.parser;


import com.braintri.tumblr.model.Post;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by mike on 25.01.16.
 */
public class PostsParser {

    public ArrayList<Post> parseRepo(JSONObject jObject){

        ArrayList<Post> postsDataArray = new ArrayList<>();
        JSONObject jPost;
        JSONArray jPosts = null;

        try {
            jPosts = jObject.getJSONArray("posts");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0; i<jPosts.length();i++){

            Post postData = new Post();

            try {
                jPost = (JSONObject)jPosts.get(i);
                String type = jPost.getString("type");

                if (type.equals("photo")) {
                    postData.setType("photo");
                    postData.setId(jPost.getString("id"));
                    String photoCaption = jPost.getString("photo-caption");
                    String trimmedPhotoCaption = android.text.Html.fromHtml(photoCaption).toString().trim();
                    postData.setPhotoCaption(trimmedPhotoCaption);
                    String photoUrl = jPost.getString("photo-url-1280");
                    postData.setPhotoUrl(android.text.Html.fromHtml(photoUrl).toString());
                    JSONArray jTags = jPost.getJSONArray("tags");
                    ArrayList<String> tagsArray = new ArrayList<>();
                    for (int j = 0; j < jTags.length(); j++) {
                        tagsArray.add(jTags.getString(j));
                    }
                    postData.setTags(tagsArray);
                    postsDataArray.add(postData);
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return postsDataArray;
    }
}

