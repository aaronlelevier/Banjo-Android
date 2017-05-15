package com.bwldr.banjo.preview;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bwldr.banjo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A placeholder fragment containing a simple view.
 */
public class PreviewFragment extends Fragment {

    private static final String TAG = PreviewFragment.class.getSimpleName();

    public PreviewFragment() {
    }

    public static PreviewFragment newInstance() {
        return new PreviewFragment();
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
                broadcastNewFile(photoUri);
            }
        });

        return view;
    }

    private void broadcastNewFile(Uri uri) {
        File inputFile = new File(uri.getPath());
        String inputPath = inputFile.getAbsolutePath();
        File outputFile = getFileFromUri(uri);
        String outputPath = outputFile.getPath();

        copyFiletoProtoDir(inputPath, outputPath);

        MediaScannerConnection.scanFile(
                getActivity(),
                new String[]{outputFile.getPath()},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String s, Uri uri) {
                        Log.d(TAG, "media scan completed");
                    }
                }
        );
    }

    private void copyFiletoProtoDir(String inputPath, String outputPath) {
        InputStream in;
        OutputStream out;

        try {
            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        }  catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private File getFileFromUri(Uri uri) {
        String fileName = uri.getLastPathSegment();
        File outputDir = getOrCreatePhotoDirectory();
        return new File(outputDir, fileName);
    }

    private File getOrCreatePhotoDirectory() {
        File outputDir = null;
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            File picturesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            outputDir = new File(picturesDir, getActivity().getString(R.string.app_name));
            if (!outputDir.exists()) {
                if (!outputDir.mkdirs()) {
                    Log.e(TAG, "outputDir.mkdirs: error");
                    outputDir = null;
                }
            }
        }
        return outputDir;
    }
}
