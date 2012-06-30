package ca.kess.demo;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import ca.kess.demo.DownloadInfo.DownloadState;
import ca.kess.scrollbarlistview.R;

public class DownloadInfoArrayAdapter extends ArrayAdapter<DownloadInfo> {
  private static final String TAG = DownloadInfoArrayAdapter.class.getSimpleName();
  private static class DownloadInfoHolder {
    TextView filenameTextView;
    ProgressBar downloadProgressBar;
    Button downloadButton;
    DownloadInfo info;
    int lastPosition;
  }
  
  public DownloadInfoArrayAdapter(Context context, int textViewResourceId,
      List<DownloadInfo> objects) {
    super(context, textViewResourceId, objects);
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    //Log.d(TAG, "Getting item at position " + position + " - convertView is "
    //    + (convertView == null ? "NULL" : "NOT NULL"));
    View row = convertView;
    final DownloadInfo info = getItem(position);

    if(null == row) {
      LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      row = inflater.inflate(R.layout.file_download_row, parent, false);
      Log.d(TAG, "Created new row for " + info.getFilename());
    } else {
      DownloadInfo tmp = (DownloadInfo) row.getTag();
      tmp.setProgressBar(null);
      Log.d(TAG, "Setting progress bar on " + tmp.getFilename() +
          " to null since it is being used by " + info.getFilename());
    }
    
    row.setTag(info);
    
    final TextView textView = (TextView) row.findViewById(R.id.downloadFileName);
    final ProgressBar progressBar = (ProgressBar) row.findViewById(R.id.downloadProgressBar);
    final Button downloadButton = (Button)row.findViewById(R.id.downloadButton); 
    
    info.setProgressBar(progressBar);
    textView.setText(info.getFilename());
    //Now set up the button
    switch(info.getDownloadState()) {
    case NOT_STARTED:
      downloadButton.setText("Download");
      downloadButton.setEnabled(true);
      break;
    case ENQUEUED:
      downloadButton.setText("Enqueued...");
      downloadButton.setEnabled(false);
      break;
    case DOWNLOADING:
      downloadButton.setText("Downloading...");
      downloadButton.setEnabled(false);
      break;
    case COMPELETED:
      downloadButton.setText("Done!");
      downloadButton.setEnabled(false);
      break;
    }
    
    downloadButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
         Log.i(TAG, "Clicked " + info.getFilename());
         info.setDownloadState(DownloadState.ENQUEUED);
         downloadButton.setEnabled(false);
         downloadButton.invalidate();
         FileDownloadTask task = new FileDownloadTask(DownloadInfoArrayAdapter.this, info);
         task.execute();
      }
    });
    
    return row;
  }

}
