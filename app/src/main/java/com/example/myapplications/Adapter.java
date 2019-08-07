package com.example.myapplications;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context contextAd;
    private test con;
    private PackageManager pmA;
    private List packagesA;
    private static int sizeA;

    public Adapter(Context context, PackageManager pm, List packages, int size){
        contextAd = context;
        pmA = pm;
        packagesA = packages;
        sizeA = size;
    }


    public interface test{
        void item(int pos);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Context context = recyclerView.getContext();
        if(context instanceof test)
            con = (test)context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        int layoutIdForListItem = R.layout.list_item;

        LayoutInflater inflater = LayoutInflater.from(contextAd);
        View view = inflater.inflate(layoutIdForListItem,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder ViewHolder, int i) {
        ViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return sizeA;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameView;
        TextView packageIndex;
        ImageView iconView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    con.item(getAdapterPosition());
                }
            });

            nameView = itemView.findViewById(R.id.app_name);
            packageIndex= itemView.findViewById(R.id.app_package);
            iconView = itemView.findViewById(R.id.app_icon);
        }


        void bind(int listIndex){
            ApplicationInfo app = (ApplicationInfo) packagesA.get(listIndex);
            nameView.setText(app.loadLabel(pmA).toString());
            packageIndex.setText(app.packageName);
            iconView.setImageDrawable(app.loadIcon(pmA));
        }
    }
}
