My attempt to get smooth ProgressBars in ListViews. Unfortunately, I am unable to figure out how to do this.

The idea is to have a ListView where each item is a ProgressBar and a Button. Clicking the Button causes an AsyncTask to execute, which notifies of its progress and updates the ProgressBar for the item in the ListView.

The problem seems to be that calling notifyDataSetChanged on the ArrayAdapter for the ListView too frequently results in touch events for the buttons from being called.

The FileDownloadTask's Thread.sleep() on line 23 controls the pace of the task. If this is set to a very large number (for example, 2000), the LIstView functions how I would suspect, however, for moderately small numbers (ie. 100), the user is limited to a single download at a time, although sometimes by rapidly clicking on a button can cause another touch event to slip through. During this time, the ListView can be scrolled, albeit with choppiness.

So the big question is: What is the proper way to accomplish having a ProgressBar smoothly update in a ListView, while not blocking touch events?

See: http://stackoverflow.com/q/11200299/31455 for my original question.

![Screenshot](https://github.com/mdkess/ProgressBarListView/raw/master/screenshot.png "ListView Screenshot")