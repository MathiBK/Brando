package com.example.drikkelek;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;

public class drikkeAdapter extends RecyclerView.Adapter<drikkeAdapter.drikkeViewHolder> {
    public static final String LOG_TAG = drikkeAdapter.class.getSimpleName();
    private ArrayList<UserObject> mUserObjArray;
    private Context context;

    public static class drikkeViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;

        public drikkeViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.recycleText);
            imageView = v.findViewById(R.id.recycleImg);
        }
    }

    public drikkeAdapter(Context c, ArrayList<UserObject> data) {
        mUserObjArray = data;
        context = c;
    }


    @NonNull
    @Override
    public drikkeAdapter.drikkeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
        return new drikkeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull drikkeViewHolder holder, int position) {
        holder.textView.setText(mUserObjArray.get(position).name);

            String imgVal = mUserObjArray.get(position).imgUri;
            Log.d(LOG_TAG, imgVal);
            if(imgVal.contains("drawable")) {
                holder.imageView.setImageURI(Uri.parse(mUserObjArray.get(position).imgUri));
            } else {
                Glide.with(context)
                        .load(Uri.parse(mUserObjArray.get(position).imgUri).getPath())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .apply(RequestOptions.circleCropTransform())
                        .into(holder.imageView);
            }

        }



    @Override
    public int getItemCount() {
        return mUserObjArray.size();
    }
}
