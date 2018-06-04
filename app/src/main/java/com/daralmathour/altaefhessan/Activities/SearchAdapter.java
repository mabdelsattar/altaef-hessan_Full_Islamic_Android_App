package com.daralmathour.altaefhessan.Activities;

/**
 * Created by codelab on 6/3/2018.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daralmathour.altaefhessan.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>  implements Filterable {

    private List<SearchViewModel> searchList;
    private List<SearchViewModel> searchListFilter;
    private Context _context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView searchPageNumber, searchSoraName,AyahNumber,AyahContent;
        LinearLayout mainLayout;
        

        public MyViewHolder(View view) {
            
            super(view);
            searchPageNumber = (TextView) view.findViewById(R.id.searchPageNumber);
            searchSoraName = (TextView) view.findViewById(R.id.searchSoraName);
            AyahNumber = (TextView) view.findViewById(R.id.AyahNumber);
            AyahContent = (TextView) view.findViewById(R.id.AyahContent);
            mainLayout = (LinearLayout) view.findViewById(R.id.mainSearch);

        }
    }


    public SearchAdapter(List<SearchViewModel> searchList, Context context) {
        this.searchList = searchList;
        this._context = context;
        this.searchListFilter = searchList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchrow, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    searchListFilter = new ArrayList<>();
                } else {
                    List<SearchViewModel> filteredList = new ArrayList<>();
                    for (SearchViewModel row : searchList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getContent().toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row);
                        }
                    }

                    searchListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = searchListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                searchListFilter = (ArrayList<SearchViewModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SearchViewModel search = searchListFilter.get(position);
        //searchPageNumber, searchSoraName,AyahNumber,AyahContent
        holder.searchPageNumber.setText("صفحة: " +search.getPageNumber());
        holder.searchSoraName.setText(search.getSoraName());
        holder.AyahNumber.setText("ايه: " +search.getAyahNumber());
        holder.AyahContent.setText(search.getContent());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Page",search.getPageNumber());
                intent.putExtra("AyahNumber",search.getAyahNumber());
                _context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchListFilter.size();
    }
}