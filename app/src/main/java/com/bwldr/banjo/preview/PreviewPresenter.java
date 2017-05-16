package com.bwldr.banjo.preview;

import android.net.Uri;
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
    public void broadcastNewFile(FragmentActivity activity, Uri uri) {
//        File inputFile = new File(uri.getPath());
//        String inputPath = inputFile.getAbsolutePath();
        File outputFile = getFileFromUri(activity, uri);
//        String outputPath = outputFile.getPath();

    }

    @Override
    public File getFileFromUri(FragmentActivity activity, Uri uri) {
        String fileName = uri.getLastPathSegment();
        File outputDir = getOrCreatePhotoDirectory(activity);
        return new File(outputDir, fileName);
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
