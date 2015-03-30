package com.example.myexpandableswipelisttest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.myexpandableswipelisttest.myexpandableswipelistview.BaseSwipeListViewListener;
import com.example.myexpandableswipelisttest.myexpandableswipelistview.MyExpandableSwipeListView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

	private MyExpandableSwipeListView listView;
	private MyExpandableListAdapter mAdapter;
//	private SwipeAdapter mAdapter;
	
	public static int deviceWidth ;
	private List<String> testData ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		testData = getTestData();
		deviceWidth = getDeviceWidth();
		listView = (MyExpandableSwipeListView)findViewById(R.id.example_lv_list);
		mAdapter = new MyExpandableListAdapter(this, testData, listView);
		listView.setAdapter(mAdapter);
//		mAdapter = new SwipeAdapter(this, testData, listView);
		listView.setSwipeListViewListener( new TestBaseSwipeListViewListener());
		reload();
	}
	
	private List<String> getTestData() {
		String [] obj = new String[]{"背对背拥抱","第几个一百天","江南","那些你很冒险的梦","醉赤壁","西界","爱与希望","你要的不是我","不潮不用花钱","只对你有感觉","简简单单","喳喳"};
		List<String> list = new ArrayList<String>(Arrays.asList(obj));
		return list;
	}
	
	private int getDeviceWidth() {
		return getResources().getDisplayMetrics().widthPixels;
	}

	private void reload() {
		listView.setSwipeMode(MyExpandableSwipeListView.SWIPE_MODE_LEFT);
		listView.setSwipeActionLeft(MyExpandableSwipeListView.SWIPE_ACTION_REVEAL);
//		mSwipeListView.setSwipeActionRight(settings.getSwipeActionRight());
		listView.setOffsetLeft(deviceWidth * 1 / 3);
//		mSwipeListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
		listView.setAnimationTime(0);
		listView.setSwipeOpenOnLongPress(false);
    }
	
	class TestBaseSwipeListViewListener extends BaseSwipeListViewListener{

		@Override
		public void onClickFrontView(int position) {
			Log.d("position",String.valueOf(position));
//			int[] array = listView.getGroupAndChildPositions(position);
			int[] array = listView.getPositionArray(position);
			Log.d("array","groupPosition: "+String.valueOf(array[0])+" childPosition "+String.valueOf(array[1]));
			super.onClickFrontView(position);
//			Toast.makeText(getApplicationContext(), testData.get(position), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onDismiss(int[] reverseSortedPositions) {
			for (int position : reverseSortedPositions) {
				testData.remove(position);
			}
			mAdapter.notifyDataSetChanged();
		}
	}
	
}
