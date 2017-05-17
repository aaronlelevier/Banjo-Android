package com.bwldr.banjo.preview;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.bwldr.banjo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PreviewPresenter implements PreviewContract.Presenter {

    private final PreviewContract.View mView;

    public PreviewPresenter(PreviewContract.View view) {
        mView = view;
    }

    @Override
    public void broadcastNewFile(FragmentActivity activity, Uri uri) {
        File inputFile = new File(uri.getPath());
        String inputPath = inputFile.getAbsolutePath();
        File outputFile = getFileFromUri(activity, uri);
        String outputPath = outputFile.getPath();

        copyFileToProtoDir(inputPath, outputPath);

        MediaScannerConnection.scanFile(
                activity,
                new String[]{outputFile.getPath()},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String s, Uri uri) {
                    }
                }
        );
    }

    @Override
    public void copyFileToProtoDir(String inputPath, String outputPath) {
        InputStream in;
        OutputStream out;

        try {
            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
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
