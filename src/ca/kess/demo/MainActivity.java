package ca.kess.demo;

import java.util.ArrayList;
import java.util.List;

import ca.kess.scrollbarlistview.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    ListView listView = (ListView) findViewById(R.id.downloadListView);
    
    List<DownloadInfo> downloadInfo = new ArrayList<DownloadInfo>();
    for(int i = 0; i < 50; ++i) {
      downloadInfo.add(new DownloadInfo("File " + i, 1000));
    }
    
    listView.setAdapter(new DownloadInfoArrayAdapter(getApplicationContext(), R.id.downloadListView, downloadInfo));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

}
