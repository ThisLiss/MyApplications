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

public class MainActivity extends AppCompatActivity implements Adapter.test {

    private PackageManager pm;
    private List packages;

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
        int size = packages.size();

        Adapter adapter = new Adapter(MainActivity.this, pm, packages, size);
        numbersList.setAdapter(adapter);

    }

    @Override
    public void item(int position) {

                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                LinearLayout view = (LinearLayout) inflater.inflate(R.layout.for_dialog, null);

                iconDialog = view.findViewById(R.id.imageView);
                textName = view.findViewById(R.id.textName);
                textPack = view.findViewById(R.id.textPackage);
                textVer = view.findViewById(R.id.textVer);
                textVerCode = view.findViewById(R.id.textVerCode);


                app = (ApplicationInfo) packages.get(position);

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
