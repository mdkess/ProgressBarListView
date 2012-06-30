package ca.kess.demo;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import ca.kess.demo.DownloadInfo.DownloadState;

public class FileDownloadTask extends AsyncTask<Void, Integer, Void> {
  private static final String TAG = FileDownloadTask.class.getSimpleName();
  final DownloadInfo mInfo;
  final DownloadInfoArrayAdapter mAdapter;
  
  public FileDownloadTask(DownloadInfoArrayAdapter adapter, DownloadInfo info) {
    mInfo = info;
    mAdapter = adapter;
  }
  
  @Override
  protected void onProgressUpdate(Integer... values) {
    ProgressBar bar = mInfo.getProgressBar();
    if(bar != null) {
      Log.d(TAG, "Setting bar to " + values[0]);
      bar.setProgress(values[0]);
      bar.invalidate();
    }
  }

  @Override
  protected Void doInBackground(Void... params) {
    Log.d(TAG, "Starting download for " + mInfo.getFilename());
    mInfo.setDownloadState(DownloadState.DOWNLOADING);
    for(int i=0; i < mInfo.getFileSize(); ++i) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      mInfo.setProgress(i);
      publishProgress(i);
    }
    mInfo.setDownloadState(DownloadState.COMPELETED);
    return null;
  }

}
