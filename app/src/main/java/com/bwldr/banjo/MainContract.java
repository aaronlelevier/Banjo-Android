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

        FragmentActivity getActivity();
    }

    interface Presenter {
        void takePicture() throws IOException;
    }
}
