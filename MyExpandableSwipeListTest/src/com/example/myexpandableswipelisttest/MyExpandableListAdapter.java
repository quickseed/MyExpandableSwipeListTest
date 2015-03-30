package com.example.myexpandableswipelisttest;

import java.util.ArrayList;
import java.util.List;

import com.example.myexpandableswipelisttest.myexpandableswipelistview.MyExpandableSwipeListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter{

	private String[] group ;
	private ArrayList<ArrayList<String>> childs;
	private List<String> mDatas;
	
	private MyExpandableSwipeListView listView;
	private Context mContext;
	
	public MyExpandableListAdapter(Context mContext,List<String> data,MyExpandableSwipeListView listView) {
		this.listView = listView;
		this.mDatas = data;
		this.mContext = mContext;
		initGroup();
	}
	
	private void initGroup(){
		group =new String[]{"下属部门","下属人员"};
		childs = new ArrayList<ArrayList<String>>(group.length);
		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		for (String obj : mDatas) {
			if(obj.equals("江南")||obj.equals("那些你很冒险的梦")){
				list2.add(obj);
			}else{
				list1.add(obj);
			}
		}
		childs.add(list1);
		childs.add(list2);
	}
	
	@Override
	public int getGroupCount() {
		if (group != null) {
			return group.length;
		}
		return 0;
	}

	public int getFinalPosition(int groupPosition, int childPosition){
		if(groupPosition==0){
			return childPosition;
		}else{
			int position = 0;
			for (int i = 0; i < getGroupCount()-1; i++) {
				position+=getChildrenCount(i);
			}
			return position + childPosition;
		}
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		if (childs != null && childs.get(groupPosition) != null) {
			return childs.get(groupPosition).size();
		}
		return 0;
	}

	@Override
	public String getGroup(int groupPosition) {
		return group[groupPosition];
	}

	@Override
	public String getChild(int groupPosition, int childPosition) {
		return childs.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupViewHolder holder = null;
		if (null == convertView) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.cms_group_item, null);
			holder = new GroupViewHolder() ;
			holder.groupName = (TextView) convertView.findViewById(R.id.group_name);

			convertView.setTag(holder);
		}else {
			holder = (GroupViewHolder)convertView.getTag();
		}
//		if(isExpanded){
//			convertView.setBackgroundResource(R.drawable.title_bar_unfold);
//		}else{
//			convertView.setBackgroundResource(R.drawable.title_bar_packup);
//		}
		holder.groupName.setText(getGroup(groupPosition));
		return convertView ;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder holder = null ;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.package_row, parent, false);
			holder = new ChildViewHolder();
			holder.mFrontText = (TextView) convertView.findViewById(R.id.example_row_tv_title);
			holder.mBackEdit = (Button) convertView.findViewById(R.id.example_row_b_action_3);
			holder.mBackDelete = (Button) convertView.findViewById(R.id.example_row_b_action_2);
			convertView.setTag(holder);
		}else{
			holder = (ChildViewHolder) convertView.getTag();
		}
		
		String itemObject = getChild(groupPosition, childPosition);
		holder.mBackDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listView.closeAnimate(getFinalPosition(groupPosition,childPosition));
				listView.dismiss(getFinalPosition(groupPosition,childPosition));
			}
		});
		holder.mFrontText.setText(itemObject);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	class GroupViewHolder{
		TextView groupName ;
	}
	
	class ChildViewHolder{
		TextView mFrontText ;
		Button mBackEdit,mBackDelete ;
	}
}
