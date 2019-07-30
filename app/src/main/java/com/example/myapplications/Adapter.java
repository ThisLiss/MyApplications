package com.example.myapplications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context contextAd;
    private PackageManager pm;
    private List packages;
    private static int size;
    private LinearLayout view;

    public Adapter(Context context){
        contextAd = context;
        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        size = packages.size();
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
        return size;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameView;
        TextView packageIndex;
        ImageView iconView;

        ImageView iconDialog;
        TextView textPack;
        TextView textName;
        TextView textVer;
        TextView textVerCode;

        String versionName = null;
        int versionCode = 0;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.app_name);
            packageIndex= itemView.findViewById(R.id.app_package);
            iconView = itemView.findViewById(R.id.app_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = LayoutInflater.from(contextAd);
                    view = (LinearLayout) inflater.inflate(R.layout.for_dialog, null);

                    iconDialog = view.findViewById(R.id.imageView);
                    textName = view.findViewById(R.id.textName);
                    textPack = view.findViewById(R.id.textPackage);
                    textVer = view.findViewById(R.id.textVer);
                    textVerCode = view.findViewById(R.id.textVerCode);

                    int position = getAdapterPosition();
                    ApplicationInfo app = (ApplicationInfo) packages.get(position);

                    try {
                        versionName = pm.getPackageInfo(app.packageName, 0).versionName;
                        versionCode = pm.getPackageInfo(app.packageName, 0).versionCode;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                    AlertDialog.Builder Dialog = new AlertDialog.Builder(contextAd);
                    Dialog.setView(view);

                    iconDialog.setImageDrawable(app.loadIcon(pm));
                    textName.setText(app.loadLabel(pm).toString());
                    textPack.setText(app.packageName);
                    textVer.setText(versionName);
                    textVerCode.setText(versionCode+"");

                    Dialog.setNegativeButton(
                            "Закрыть", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which){
                                    dialog.cancel();
                                }
                            });

                    Dialog.show();
                }
            });
        }


        void bind(int listIndex){
            ApplicationInfo app = (ApplicationInfo) packages.get(listIndex);
            nameView.setText(app.loadLabel(pm).toString());
            packageIndex.setText(app.packageName);
            iconView.setImageDrawable(app.loadIcon(pm));
        }
    }
}
