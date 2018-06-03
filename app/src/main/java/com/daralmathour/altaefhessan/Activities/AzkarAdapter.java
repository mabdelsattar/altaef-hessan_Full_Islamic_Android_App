package com.daralmathour.altaefhessan.Activities;

/**
 * Created by codelab on 6/3/2018.
 */


import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daralmathour.altaefhessan.R;

import java.util.List;

public class AzkarAdapter extends RecyclerView.Adapter<AzkarAdapter.MyViewHolder> {

    private List<AzkarModel> azkarList;
    private Context _context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView azkarDetails, azkarSawap,azkarRepeat;
        public ImageView shareImage;

        public MyViewHolder(View view) {
            super(view);
            azkarDetails = (TextView) view.findViewById(R.id.azkardetails);
            azkarSawap = (TextView) view.findViewById(R.id.azkarSawap);
            azkarRepeat = (TextView) view.findViewById(R.id.azkarRepeat);
            shareImage = (ImageView) view.findViewById(R.id.azkarshare);
        }
    }


    public AzkarAdapter(List<AzkarModel> azkarList, Context context) {
        this.azkarList = azkarList;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.azarrow, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AzkarModel azkar = azkarList.get(position);
        holder.azkarDetails.setText(azkar.getContent());
        holder.azkarSawap.setText(azkar.getSawap());
        holder.azkarRepeat.setText(azkar.getRepeat());

        holder.shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, azkar.getContent()+"\n"+azkar.getSawap());
                _context.startActivity(Intent.createChooser(intent, "Share"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return azkarList.size();
    }
}