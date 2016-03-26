package fanaticfour.waffle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JoinEvent extends Activity {

    // UI references.
    private View mProgressView;
    private View mJoinFormView;

    // parameters for taking pictures
    private static final int SELECT_PICTURE = 1;
    private String mImageFullPathAndName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);

        Button mJoinButton = (Button) findViewById(R.id.join_button);
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                join(view);
            }
        });

//        Button mScanButton = (Button) findViewById(R.id.scan_button);
//        mScanButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                scan(view);
//            }
//        });

        mJoinFormView = findViewById(R.id.join_form);
        mProgressView = findViewById(R.id.join_progress);
    }

    private void join(View v) {
        showProgress(true);
        String ticketNum = ((EditText) findViewById(R.id.ticketNumber)).getText().toString();

        Intent intent = new Intent(this, ShowEvent.class);
        startActivity(intent);
    }

    // function called from a launch camera button click event
    private void scan(View v) {
        int TAKE_PICTURE = 2;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE ||
                requestCode == SELECT_PICTURE) {
            if (resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                // get the selected image full path and name
                mImageFullPathAndName = cursor.getString(columnIndex);
                cursor.close();
            }
        }
        System.out.println(mImageFullPathAndName);
        //sendRequest();
    }

//    private void sendRequest(){
//        String idol_ocr_service = "https://api.idolondemand.com/1/api/async/ocrdocument/v1";
//        URI uri = null;
//        try {
//            uri = new URI(idol_ocr_service);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        HttpPost httpPost = new HttpPost();
//        httpPost.setURI(uri);
//        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();                reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        reqEntity.addPart("apikey", new StringBody(apikey, ContentType.TEXT_PLAIN));
//        reqEntity.addBinaryBody("file", new File(mImageFullPathAndName);
//        reqEntity.addPart("mode", new StringBody("document_photo", ContentType.TEXT_PLAIN));
//        httpPost.setEntity(reqEntity.build());
//        HTTPClient httpClient = new DefaultHttpClient();
//        HttpResponse response = httpClient.execute(httpPost);
//    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mJoinFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mJoinFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mJoinFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mJoinFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}
