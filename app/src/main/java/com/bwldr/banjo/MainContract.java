package com.bwldr.banjo;

import android.net.Uri;

import java.io.File;
import java.io.IOException;

/**
 * Interfaces to be followed by the Main View and Presenter
 */
public class MainContract {

    interface View {
        void dispatchTakePictureIntent();

        void openCamera();
    }

    interface Presenter {
        void takePicture() throws IOException;

        Uri getUri();

        File getFile();
    }
}
