package com.bwldr.banjo;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import java.io.IOException;

/**
 * Interfaces to be followed by the Main View and Presenter
 */
public class MainContract {

    interface View {
        void dispatchTakePictureIntent();

        void openCamera(Uri uri);
    }

    interface Presenter {
        void takePicture(FragmentActivity activity) throws IOException;
    }
}
