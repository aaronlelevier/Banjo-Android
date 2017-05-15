package com.bwldr.banjo.preview;

import android.support.v4.app.FragmentActivity;

import java.io.File;

/**
 * Interfaces to be followed by the Preview View and Presenter
 */
public class PreviewContract {

    interface View {
    }

    interface Presenter {
        File getOrCreatePhotoDirectory(FragmentActivity activity);
    }
}
