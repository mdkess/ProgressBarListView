package ca.kess.demo;

public class DownloadInfo {
  private final String mFilename;
  private Integer mProgress;
  private Integer mFileSize;
  
  public DownloadInfo(String filename, Integer size) {
    mFilename = filename;
    mProgress = 0;
    mFileSize = size;
  }

  public Integer getProgress() {
    return mProgress;
  }

  public void setProgress(Integer progress) {
    this.mProgress = progress;
  }

  public Integer getFileSize() {
    return mFileSize;
  }

  public void setFileSize(Integer fileSize) {
    this.mFileSize = fileSize;
  }

  public String getFilename() {
    return mFilename;
  }
}
