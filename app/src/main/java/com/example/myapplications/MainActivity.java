package com.example.myapplications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.onAdapterListener {

    private PackageManager pm;
    private List <ApplicationInfo> packages;

    ImageView iconDialog;
    TextView textPack;
    TextView textName;
    TextView textVer;
    TextView textVerCode;
    String versionName = null;
    int versionCode = 0;
    ApplicationInfo app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView numbersList = findViewById(R.id.rv_numbers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        numbersList.setLayoutManager(layoutManager);

        pm = MainActivity.this.getPackageManager();
        packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        Adapter adapter = new Adapter(pm, packages);
        numbersList.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {

                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                LinearLayout view = (LinearLayout) inflater.inflate(R.layout.dialog_app_info, null);

                iconDialog = view.findViewById(R.id.ivAppImageD);
                textName = view.findViewById(R.id.tvAppNameD);
                textPack = view.findViewById(R.id.tvAppPackageD);
                textVer = view.findViewById(R.id.tvAppVersion);
                textVerCode = view.findViewById(R.id.tvAppVersionCode);


                app = packages.get(position);

                try {
                    versionName = pm.getPackageInfo(app.packageName, 0).versionName;
                    versionCode = pm.getPackageInfo(app.packageName, 0).versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder Dialog = new AlertDialog.Builder(MainActivity.this);
                Dialog.setView(view);

                iconDialog.setImageDrawable(app.loadIcon(pm));
                textName.setText(app.loadLabel(pm).toString());
                textPack.setText(app.packageName);
                textVer.setText(versionName);
                textVerCode.setText(versionCode + "");

                Dialog.setNegativeButton(
                        R.string.button_close, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                Dialog.show();
        }
}
