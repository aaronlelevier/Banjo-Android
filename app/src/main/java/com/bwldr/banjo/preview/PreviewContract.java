package com.bwldr.banjo.preview;

import android.net.Uri;

import java.io.File;

/**
 * Interfaces to be followed by the Preview View and Presenter
 */
public class PreviewContract {

    interface View {
        void showToast(String text);

        void scanFileWithMediaScanner(String filePath);
    }

    interface Presenter {
        void saveAndBroadcastNewPhoto(Uri uri);

        void copyFileToProtoDir(String inputPath, String outputPath);

        File getFileFromUri(Uri uri);

        File getOrCreatePhotoDirectory();
    }
}
