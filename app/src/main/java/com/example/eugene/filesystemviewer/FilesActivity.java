package com.example.eugene.filesystemviewer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilesActivity extends ListActivity {

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.files_activity);

        path = Environment.getRootDirectory().getPath();
        if (getIntent().hasExtra("path")) {
            path = getIntent().getStringExtra("path");
        }
        setTitle(path);


        List values = new ArrayList();
        File dir = new File(path);
        String[] list = dir.list();
        if (list != null) {
            for (String file : list) {
                if (!file.startsWith(".")) {
                    values.add(file);
                }
            }
        }
        Collections.sort(values);

        ArrayAdapter adapter =
                new ArrayAdapter(this,
                        android.R.layout.simple_list_item_2,
                        android.R.id.text1,
                        values);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        String filename = (String) getListAdapter().getItem(position);
        if (path.endsWith(File.separator)) {
            filename = path + filename;
        } else {
            filename = path + File.separator + filename;
        }
        if (new File(filename).isDirectory()) {
            Intent intent = new Intent(this, FilesActivity.class);
            intent.putExtra("path", filename);
            startActivity(intent);
        } else {
            Toast.makeText(this, filename + " не является директорией", Toast.LENGTH_LONG).show();
        }
    }
}
