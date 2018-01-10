package com.hf.hiddenfounders;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hf.hiddenfounders.adapters.ReposAdapter;
import com.hf.hiddenfounders.classes.Repo;
import com.hf.hiddenfounders.utility.DividerItemDecoration;
import com.hf.hiddenfounders.utility.EndlessRecyclerViewScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    ReposAdapter adapter;
    RequestQueue queue;
    ArrayList<Repo> repos = new ArrayList<>();
    int page = 1;

    //--- Getting repos created from 02/12/2017
    String url = "https://api.github.com/search/repositories?q=created:>2017-12-05&sort=stars&order=desc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("HiddenFounders");
        }
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ReposAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        // Infinite Scroll
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int p, int totalItemsCount, RecyclerView view) {
                page++;
                progressBar.setIndeterminate(true);
                new Handler().postDelayed(() -> {
                    sendJsonRequest(page);
                    Toast.makeText(getApplicationContext(), "Page "+page+" loaded succefully", Toast.LENGTH_SHORT).show();
                }, 2000);
            }
        });
        sendJsonRequest(page);
    }

    // Sending request and parsing JSON object
    private void sendJsonRequest(int page) {
        queue = Volley.newRequestQueue(getApplicationContext());
        String requestUrl = url + "&page=" + page;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestUrl, null, (response) -> {
            if (response == null)
                return;
            try {
                JSONArray reposArray = response.getJSONArray("items");
                for (int i = 0; i<reposArray.length(); i++) {
                    JSONObject repo = reposArray.getJSONObject(i);
                    String repo_name = repo.getString("name");
                    String repo_description = repo.getString("description");
                    int repo_stars = repo.getInt("watchers_count");
                    JSONObject owner = repo.getJSONObject("owner");
                    String owner_username = owner.getString("login");
                    String owner_avatar = owner.getString("avatar_url");

                    Repo r = new Repo(repo_name, repo_description, owner_username, owner_avatar, repo_stars);
                    repos.add(r);
                    adapter.setRepos(repos);
                    progressBar.setIndeterminate(false);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, (error) -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());


        queue.add(request);
        Log.e("sendJsonRequest", requestUrl);
    }
}
