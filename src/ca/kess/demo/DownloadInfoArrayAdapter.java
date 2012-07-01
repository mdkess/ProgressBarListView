package ca.kess.demo;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
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
  // Simple class to make it so that we don't have to call findViewById frequently
  private static class ViewHolder {
    TextView textView;
    ProgressBar progressBar;
    Button button;
    DownloadInfo info;
  }
  
  
  private static final String TAG = DownloadInfoArrayAdapter.class.getSimpleName();

  public DownloadInfoArrayAdapter(Context context, int textViewResourceId,
      List<DownloadInfo> objects) {
    super(context, textViewResourceId, objects);
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View row = convertView;
    final DownloadInfo info = getItem(position);
    // We need to set the convertView's progressBar to null.

    ViewHolder holder = null;
    
    if(null == row) {
      LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      row = inflater.inflate(R.layout.file_download_row, parent, false);
      
      holder = new ViewHolder();
      holder.textView = (TextView) row.findViewById(R.id.downloadFileName);
      holder.progressBar = (ProgressBar) row.findViewById(R.id.downloadProgressBar);
      holder.button = (Button)row.findViewById(R.id.downloadButton);
      holder.info = info;
      
      row.setTag(holder);
    } else {
      holder = (ViewHolder) row.getTag();
      
      holder.info.setProgressBar(null);
      holder.info = info;
      holder.info.setProgressBar(holder.progressBar);
    }

    holder.textView.setText(info.getFilename());
    holder.progressBar.setProgress(info.getProgress());
    holder.progressBar.setMax(info.getFileSize());
    info.setProgressBar(holder.progressBar);
    
    holder.button.setEnabled(info.getDownloadState() == DownloadState.NOT_STARTED);
    final Button button = holder.button;
    holder.button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        info.setDownloadState(DownloadState.QUEUED);
        button.setEnabled(false);
        button.invalidate();
        FileDownloadTask task = new FileDownloadTask(info);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
      }
    });
    
    
    //TODO: When reusing a view, invalidate the current progressBar.
    
    return row;
  }

}
