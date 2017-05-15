package com.bwldr.banjo.preview;

import android.os.Environment;
import android.support.v4.app.FragmentActivity;

import com.bwldr.banjo.R;

import java.io.File;

public class PreviewPresenter implements PreviewContract.Presenter {

    private final PreviewContract.View mView;

    public PreviewPresenter(PreviewContract.View view) {
        mView = view;
    }

    @Override
    public File getOrCreatePhotoDirectory(FragmentActivity activity) {
        File outputDir = null;
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            File picturesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            outputDir = new File(picturesDir, activity.getString(R.string.app_name));
            if (!outputDir.exists()) {
                if (!outputDir.mkdirs()) {
                    outputDir = null;
                }
            }
        }
        return outputDir;
    }
}
