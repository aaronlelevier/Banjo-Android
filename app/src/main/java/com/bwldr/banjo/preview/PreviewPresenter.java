package com.bwldr.banjo.preview;

import android.net.Uri;
import android.os.Environment;

import com.bwldr.banjo.util.Constants;

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
    public void saveAndBroadcastNewPhoto(Uri uri) {
        File inputFile = new File(uri.getPath());
        String inputPath = inputFile.getAbsolutePath();
        File outputFile = getFileFromUri(uri);
        String outputPath = outputFile.getPath();

        copyFileToProtoDir(inputPath, outputPath);

        mView.scanFileWithMediaScanner(outputPath);
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
            e.printStackTrace();
        }
    }

    @Override
    public File getFileFromUri(Uri uri) {
        String fileName = uri.getLastPathSegment();
        File outputDir = getOrCreatePhotoDirectory();
        return new File(outputDir, fileName);
    }

    @Override
    public File getOrCreatePhotoDirectory() {
        File outputDir = null;
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            File picturesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            outputDir = new File(picturesDir, Constants.APP_NAME);
            if (!outputDir.exists()) {
                if (!outputDir.mkdirs()) {
                    outputDir = null;
                }
            }
        }
        return outputDir;
    }
}
