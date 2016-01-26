package com.braintri.tumblr.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.braintri.tumblr.R;
import com.braintri.tumblr.model.Post;
import com.braintri.tumblr.parser.PostsParser;
import com.braintri.tumblr.adapter.PostListAdapter;
import com.braintri.tumblr.util.DividerItemDecoration;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;


/**
 * Created by mike on 25.01.16.
 */
public class PostListActivity extends AppCompatActivity {

    private ArrayList<Post> postsData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_list_activity);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setURLEncodingEnabled(false);

        //fetch posts feed
        client.get(getReqUrl(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject postsFeed) {
                PostsParser postsParser = new PostsParser();
                postsData = postsParser.parseRepo(postsFeed);
                updateRecyclerView();
            }
        });
    }

    /**
     * Create and return url to be requesteds
     * @return url to be requested
     */
    private String getReqUrl() {

        String userName;
        String reqUrl;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        userName= sharedPrefs.getString("userName", null);
        reqUrl = "http://"+userName+".tumblr.com/api/read/json?debug=1";

        return reqUrl;
    }

    private void updateRecyclerView() {
        // get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // create an adapter
        PostListAdapter mAdapter = new PostListAdapter(postsData);
        // set adapter
        recyclerView.setAdapter(mAdapter);
        // set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // add divder line
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
    }
}

