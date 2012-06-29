package ca.kess.demo;

import java.util.List;

import ca.kess.scrollbarlistview.R;

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

public class DownloadInfoArrayAdapter extends ArrayAdapter<DownloadInfo> {
  private static final String TAG = DownloadInfoArrayAdapter.class.getSimpleName();
  private static class DownloadInfoHolder {
    TextView filenameTextView;
    ProgressBar downloadProgressBar;
    Button downloadButton;
  }
  
  public DownloadInfoArrayAdapter(Context context, int textViewResourceId,
      List<DownloadInfo> objects) {
    super(context, textViewResourceId, objects);
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View row = convertView;
    DownloadInfoHolder holder;
    
    if(null == row) {
      LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      row = inflater.inflate(R.layout.file_download_row, parent, false);
      
      holder = new DownloadInfoHolder();
      holder.filenameTextView = (TextView) row.findViewById(R.id.downloadFileName);
      holder.downloadProgressBar = (ProgressBar) row.findViewById(R.id.downloadProgressBar);
      holder.downloadButton = (Button) row.findViewById(R.id.downloadButton);
      row.setTag(holder);
    } else {
      holder = (DownloadInfoHolder) row.getTag();
    }
    final DownloadInfo info = getItem(position);
    holder.filenameTextView.setText(info.getFilename());
    holder.downloadProgressBar.setMax(info.getFileSize());
    holder.downloadProgressBar.setProgress(info.getProgress());
    
    holder.downloadButton.setOnClickListener(new OnClickListener() {
      
      public void onClick(View v) {
         Log.i(TAG, "Clicked " + info.getFilename());
         FileDownloadTask task = new FileDownloadTask(DownloadInfoArrayAdapter.this, info);
         task.execute();
      }
    });
    
    return row;
  }

}
