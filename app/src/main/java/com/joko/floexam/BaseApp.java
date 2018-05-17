package com.joko.floexam;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.joko.floexam.deps.DaggerDeps;
import com.joko.floexam.deps.Deps;
import com.joko.floexam.networking.NetworkModule;

import java.io.File;

/**
 * Created by john.mista on 6/28/16.
 */
public class BaseApp  extends AppCompatActivity{
    Deps deps;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();
        initDialogs();
    }

    public Deps getDeps() {
        return deps;
    }

    private void initDialogs(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    public void showLoading(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void showAlertDialog(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    public void hideLoading() {
        progressDialog.hide();
    }
}
