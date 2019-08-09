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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>  {

    private onAdapterListener onItem;
    private PackageManager pmA;
    private List <ApplicationInfo> packagesA;
    private static int sizeA;

    public Adapter(PackageManager pm, List packages, onAdapterListener onItem){
        pmA = pm;
        packagesA = packages;
        sizeA = packagesA.size();
        this.onItem = onItem;
    }

    public interface onAdapterListener{
        void onItemClick(int pos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        int layoutIdForListItem = R.layout.list_item_app_info;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
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
                    onItem.onItemClick(getAdapterPosition());
                }
            });

            nameView = itemView.findViewById(R.id.tvAppName);
            packageIndex= itemView.findViewById(R.id.tvAppPackage);
            iconView = itemView.findViewById(R.id.ivAppImage);
        }


        void bind(int listIndex){
            ApplicationInfo app = packagesA.get(listIndex);
            nameView.setText(app.loadLabel(pmA).toString());
            packageIndex.setText(app.packageName);
            iconView.setImageDrawable(app.loadIcon(pmA));
        }
    }
}
