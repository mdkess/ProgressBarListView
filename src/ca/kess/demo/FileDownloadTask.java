package ca.kess.demo;

import android.os.AsyncTask;

public class FileDownloadTask extends AsyncTask<Void, Void, Void> {
  final DownloadInfo mInfo;
  final DownloadInfoArrayAdapter mAdapter;
  
  public FileDownloadTask(DownloadInfoArrayAdapter adapter, DownloadInfo info) {
    mInfo = info;
    mAdapter = adapter;
  }
  
  @Override
  protected void onProgressUpdate(Void... values) {
    mAdapter.notifyDataSetChanged();
  }

  @Override
  protected Void doInBackground(Void... params) {
    for(int i=0; i < mInfo.getFileSize(); ++i) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      mInfo.setProgress(i);
      publishProgress();
    }
    return null;
  }

}
