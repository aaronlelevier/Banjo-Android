package com.bwldr.banjo.preview;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bwldr.banjo.R;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            Uri photoUri = getIntent().getData();
            bundle.putString("data", photoUri.toString());

            PreviewFragment fragment = PreviewFragment.newInstance();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }
}
