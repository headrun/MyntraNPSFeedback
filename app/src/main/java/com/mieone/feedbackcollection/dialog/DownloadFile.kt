package com.mieone.feedbackcollection.dialog

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import com.blankj.utilcode.util.LogUtils
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class DownloadFile (val context: Context) : AsyncTask<String, String, String>() {

    var progressDialog: ProgressDialog?=null

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog = ProgressDialog(context)
        this.progressDialog!!.setMessage("Progress start")
        this.progressDialog!!.show()
    }

    override fun doInBackground(vararg params: String?): String {

        val count: Int?
        try {
            val url = URL(params[0])
            val connection = url.openConnection()
            connection.connect()
            // getting file length
            val lengthOfFile = connection.getContentLength()


            // input stream to read file - with 8k buffer
            val input =  BufferedInputStream(url.openStream(), 8192)

            val timestamp =  SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format( Date())

            //Extract file name from URL
            var fileName = params[0]?.length?.let { params[0]?.lastIndexOf('/')?.plus(1)?.let { it1 -> params[0]?.substring(it1, it) } }

            //Append timestamp to file name
            fileName = timestamp + "_" + fileName

            //External directory path to save file
            val folder = "${Environment.getExternalStorageDirectory()}/${File.separator}/nps_feedback"

            //Create androiddeft folder if it does not exist
            val directory =  File(folder)

            if (!directory.exists()) {
                directory.mkdirs()
            }

            // Output stream to write file
            val output =  FileOutputStream(folder + fileName)

            val data =  ByteArray(1024)

            var total = 0
            count = input.read(data)
            while (count != -1) {
                total += count
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" +  ((total * 100) / lengthOfFile))
                LogUtils.e( "Progress: " + ((total * 100) / lengthOfFile))

                // writing data to file
                output.write(data, 0, count)
            }

            // flushing output
            output.flush()

            // closing streams
            output.close()
            input.close()
            return "Downloaded at: $folder$fileName"

        } catch (e : java.lang.Exception) {
            Log.e("Error: ", e.message)
        }

        return "Something went wrong"
    }

    override fun onProgressUpdate(vararg values: String?) {

        progressDialog?.progress = values[0]?.toInt()!!
    }

    override fun onPostExecute(result: String?) {

        this.progressDialog?.dismiss()
    }

}