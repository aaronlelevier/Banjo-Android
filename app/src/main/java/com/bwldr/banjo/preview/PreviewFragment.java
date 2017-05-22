package com.bwldr.banjo.preview;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bwldr.banjo.R;
import com.bwldr.banjo.util.Util;

/**
 * A placeholder fragment containing a simple view.
 */
public class PreviewFragment extends Fragment implements PreviewContract.View {

    private static final int MSG_SAVE_PHOTO = 0;
    private static final int MSG_SHUTDOWN = 1;

    private PreviewContract.Presenter mPresenter;
    private Uri mPhotoUri;
    private SavePhotoThread mSavePhotoThread;

    public PreviewFragment() {
    }

    public static PreviewFragment newInstance() {
        return new PreviewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSavePhotoThread = new SavePhotoThread("SavePhotoThread");
        mSavePhotoThread.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preview, container, false);

        mPhotoUri = Uri.parse(getArguments().getString("data"));
        ((ImageView) view.findViewById(R.id.image)).setImageURI(mPhotoUri);

        ImageButton saveButton = (ImageButton) view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSavePhotoThread.mWorkerHandler.obtainMessage(MSG_SAVE_PHOTO)
                        .sendToTarget();
            }
        });

        ImageButton cancelButton = (ImageButton) view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new PreviewPresenter(this);
    }

    @Override
    public void onDestroy() {
        mSavePhotoThread.mWorkerHandler.obtainMessage(MSG_SHUTDOWN);
        try {
            mSavePhotoThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * Broadcast that a new image has been saved to other apps
     *
     * @param filePath of new image file to broadcast
     */
    @Override
    public void scanFileWithMediaScanner(String filePath) {
        MediaScannerConnection.scanFile(
                getActivity(),
                new String[]{filePath},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String s, Uri uri) {
                        showToast(getActivity().getString(R.string.image_saved));
                    }
                }
        );
    }

    @Override
    public void showToast(String text) {
        Util.showToast(getActivity(), text);
    }

    private class SavePhotoThread extends HandlerThread implements Handler.Callback {

        Handler mWorkerHandler;

        public SavePhotoThread(String name) {
            super(name);
        }

        @Override
        protected void onLooperPrepared() {
            mWorkerHandler = new Handler(getLooper(), this);
        }

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAVE_PHOTO:
                    mPresenter.saveAndBroadcastNewPhoto(mPhotoUri);
                    break;
            }
            return true;
        }
    }
}
