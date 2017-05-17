package com.bwldr.banjo.preview;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bwldr.banjo.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PreviewFragment extends Fragment implements PreviewContract.View {

    private static final String TAG = PreviewFragment.class.getSimpleName();

    private PreviewContract.Presenter mPresenter;

    public PreviewFragment() {
    }

    public static PreviewFragment newInstance() {
        return new PreviewFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new PreviewPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preview, container, false);

        final Uri photoUri = Uri.parse(getArguments().getString("data"));
        ((ImageView) view.findViewById(R.id.image)).setImageURI(photoUri);

        Button saveButton = (Button) view.findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.broadcastNewFile(getActivity(), photoUri);
            }
        });

        return view;
    }
}
