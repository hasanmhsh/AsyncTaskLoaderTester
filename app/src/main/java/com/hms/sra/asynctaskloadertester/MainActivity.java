package com.hms.sra.asynctaskloadertester;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final int CONNECT_LOADER = 46;
    private static final String BUNDLE_KEY = "BKBKBK";
    private EditText taskET;
    private EditText resultET;
    private static int startIncrement = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskET = (EditText)findViewById(R.id.taskETID);
        resultET = (EditText)findViewById(R.id.resultETID);


        LoaderManager loaderManager = this.getSupportLoaderManager();
        loaderManager.initLoader(CONNECT_LOADER, null, this);

    }

    @Override
    public Loader<String> onCreateLoader(int id,final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            protected void onStartLoading() {
                if (args!=null) {
                    //forceLoad();
                    startIncrement++;
                    Toast.makeText(getApplicationContext(),"onStartLoading"+String.valueOf(startIncrement),Toast.LENGTH_SHORT).show();
                    forceLoad();
                }
            }

            @Override
            protected void onForceLoad() {
                super.onForceLoad();
            }

            @Override
            public String loadInBackground() {
                String result = null;
                try{Thread.sleep(15000);}catch (Exception e){e.printStackTrace();}

                return result;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        resultET.setText("OLF "+String.valueOf(startIncrement));
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    public void onEnqueueTaskClickeListener(View view) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY,taskET.getText().toString());
        LoaderManager loaderManager = this.getSupportLoaderManager();
        Loader<String> connectLoader = loaderManager.getLoader(CONNECT_LOADER);
        if (connectLoader == null) {
            loaderManager.initLoader(CONNECT_LOADER, args, this);
        } else {
            loaderManager.restartLoader(CONNECT_LOADER, args, this);
        }
    }
}
