package ca.kess.demo;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import ca.kess.demo.DownloadInfo.DownloadState;

/**
 * Simulate downloading a file, by increasing the progress of the FileInfo from 0 to size - 1.
 */
public class FileDownloadTask extends AsyncTask<Void, Integer, Void> {
  private static final String    TAG = FileDownloadTask.class.getSimpleName();
  final DownloadInfo             mInfo;
  
  public FileDownloadTask(DownloadInfo info) {
    mInfo = info;
  }

  @Override
  protected void onProgressUpdate(Integer... values) {
    mInfo.setProgress(values[0]);
    ProgressBar bar = mInfo.getProgressBar();
    if(bar != null) {
      bar.setProgress(mInfo.getProgress());
      bar.invalidate();
    }
  }

  @Override
  protected Void doInBackground(Void... params) {
    Log.d(TAG, "Starting download for " + mInfo.getFilename());
    mInfo.setDownloadState(DownloadState.DOWNLOADING);
    for (int i = 0; i <= mInfo.getFileSize(); ++i) {
      try {
        Thread.sleep(16);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      publishProgress(i);
    }
    mInfo.setDownloadState(DownloadState.COMPLETE);
    return null;
  }

  @Override
  protected void onPostExecute(Void result) {
    mInfo.setDownloadState(DownloadState.COMPLETE);
  }

  @Override
  protected void onPreExecute() {
    mInfo.setDownloadState(DownloadState.DOWNLOADING);
  }

}
