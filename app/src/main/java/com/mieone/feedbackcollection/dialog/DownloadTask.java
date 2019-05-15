package com.mieone.feedbackcollection.dialog;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.blankj.utilcode.util.LogUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTask extends AsyncTask<String, Integer, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ProgressDialog mProgressDialog;
//    private String fileName;
    private String folder;

    public DownloadTask(Context context) {
        this.context = context;
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Updating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
    }

    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection connection = url.openConnection();
            connection.connect();
            // getting file length
            int lengthOfFile = connection.getContentLength();


            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

//            String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

            //Extract file name from URL
//            fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1);

            //External directory path to save file
            folder = Environment.getExternalStorageDirectory() + File.separator + "NPS/";

            //Create androiddeft folder if it does not exist
            File directory = new File(folder);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Output stream to write file
            OutputStream output = new FileOutputStream(folder + "nps_feedback.apk");

            byte[] data = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress((int) ((total * 100) / lengthOfFile));
                LogUtils.e( "Progress: " + (int) ((total * 100) / lengthOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();
            return "Downloaded at: " + folder + "nps_feedback.apk";

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return "Something went wrong";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        mProgressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        mProgressDialog.dismiss();
        openNewVersion();
        if (result != null) {
            LogUtils.e(result);
//            Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
    }

    private void openNewVersion(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + File.separator + "NPS/"+"nps_feedback.apk")),
                 "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.e("TAG", "Error in opening the file!");
        }
    }
}
