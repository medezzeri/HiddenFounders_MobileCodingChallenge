package com.hf.hiddenfounders.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hf.hiddenfounders.R;
import com.hf.hiddenfounders.classes.Repo;

import java.util.ArrayList;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Admin on 04/01/2018.
 */

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {

    Context context;
    ArrayList<Repo> repos = new ArrayList<>();
    private final LayoutInflater layoutInflater;

    public ReposAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public ArrayList<Repo> getRepos() {
        return repos;
    }

    public void setRepos(ArrayList<Repo> repos) {
        this.repos = repos;
        notifyItemRangeChanged(0, repos.size());
    }

    //--- Intitiating the view holder
    @Override
    public ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        ReposViewHolder holder = new ReposViewHolder(view);

        return holder;
    }

    //--- Passing data to the ViewHolder.
    @Override
    public void onBindViewHolder(ReposViewHolder holder, int position) {
        Repo repo = repos.get(position);
        holder.repo_name.setText(repo.getName());
        holder.repo_description.setText(repo.getDescription());
        holder.repo_star.setText(String.valueOf(format(repo.getStars())));
        holder.owner_name.setText(repo.getOwner_username());

        Glide.with(context)
                .load(repo.getOwner_avatar())
                .override(500, 200)
                .into(holder.owner_img);
    }

    //--- Returning the size of the collection
    @Override
    public int getItemCount() {
        return repos.size();
    }

    //--- Creating the ViewHolder
    static class ReposViewHolder extends RecyclerView.ViewHolder {

        TextView repo_name, repo_description, repo_star, owner_name;
        ImageView owner_img, icon_star;

        public ReposViewHolder(View itemView) {
            super(itemView);

            repo_name = itemView.findViewById(R.id.repo_name);
            repo_description = itemView.findViewById(R.id.repo_description);
            repo_star = itemView.findViewById(R.id.repo_star);
            owner_name = itemView.findViewById(R.id.owner_name);
            owner_img = itemView.findViewById(R.id.owner_img);
            icon_star = itemView.findViewById(R.id.icon_star);


        }
    }


    //--- Truncing thousands to K format
    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
    }

    public static String format(long value) {
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value);

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}