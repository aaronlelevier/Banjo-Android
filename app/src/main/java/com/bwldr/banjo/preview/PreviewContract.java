package com.bwldr.banjo.preview;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import java.io.File;

/**
 * Interfaces to be followed by the Preview View and Presenter
 */
public class PreviewContract {

    interface View {
    }

    interface Presenter {
        void broadcastNewFile(FragmentActivity activity, Uri uri);

        void copyFileToProtoDir(String inputPath, String outputPath);

        File getFileFromUri(FragmentActivity activity, Uri uri);

        File getOrCreatePhotoDirectory(FragmentActivity activity);
    }
}
