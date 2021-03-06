package com.bwldr.banjo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwldr.banjo.preview.PreviewActivity;
import com.bwldr.banjo.util.ImageFile;
import com.bwldr.banjo.util.PermissionUtil;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * Fragment that requests permissions and opens the native camera
 */
public class MainFragment extends Fragment implements MainContract.View {

    private static final int RC_IMAGE_CAPTURE = 1;
    private static final int RC_CAMERA_PERMISSIONS = 2;

    private MainPresenter mPresenter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] perms = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (PermissionUtil.hasPermissions(getActivity(), perms)) {
                    dispatchTakePictureIntent();
                } else {
                    requestPermissions(perms, RC_CAMERA_PERMISSIONS);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPresenter = new MainPresenter(this, new ImageFile());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == RC_CAMERA_PERMISSIONS
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void dispatchTakePictureIntent() {
        try {
            mPresenter.takePicture();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            Uri photoUri = FileProvider.getUriForFile(
                    getActivity(),
                    getActivity().getApplicationContext().getPackageName() + ".provider",
                    mPresenter.getFile());

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePictureIntent, RC_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent intent = new Intent(getActivity(), PreviewActivity.class);
            intent.setData(mPresenter.getUri());
            startActivity(intent);
        }
    }
}
