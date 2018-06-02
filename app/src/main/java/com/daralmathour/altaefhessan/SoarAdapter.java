package com.daralmathour.altaefhessan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daralmathour.altaefhessan.Activities.QuranActivity;

import java.util.List;

/**
 * Created by codelab on 1/1/2018.
 */

public class SoarAdapter extends RecyclerView.Adapter<SoarAdapter.MyViewHolder> {
    private List<SoarInfo> soarList;
    private Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name, Type;//, Count;
        public RelativeLayout mymainview;

        public MyViewHolder(View view) {
            super(view);
            Name = (TextView) view.findViewById(R.id.Name);
            Type = (TextView) view.findViewById(R.id.Type);
            mymainview = (RelativeLayout) view.findViewById(R.id.mymainview);
            // Count = (TextView) view.findViewById(R.id.Count);
        }
    }

    public SoarAdapter(List<SoarInfo> soarList, Context context) {
        this.soarList = soarList;
        this._context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sora_row, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SoarInfo sora = soarList.get(position);
        holder.Name.setText((position + 1) + "- " + sora.Name);
        if (sora.isMecca)
            holder.Type.setText("مكية");
        else
            holder.Type.setText("مدنية");

        holder.mymainview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, QuranActivity.class);
                int index = sora.FromPage;
                intent.putExtra("index", index-1);
                intent.putExtra("position", position);
                _context.startActivity(intent);

            }
        });

        //  holder.CountCount.setText(sora.AyatCount+"");
    }

    @Override
    public int getItemCount() {
        return soarList.size();
    }

}
