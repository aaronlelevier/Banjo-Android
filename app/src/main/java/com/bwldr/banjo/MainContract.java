package com.bwldr.banjo;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import java.io.IOException;

/**
 * Interfaces to be followed by the View and Presenter
 */

public class MainContract {

    interface View {
        void openCamera(Uri uri);

        void setPreviewImage();
    }

    interface Presenter {
        void takePicture(FragmentActivity activity) throws IOException;
    }
}
