package com.example.test02;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WalkListViewAdapter extends BaseAdapter {
    private ArrayList<WalkListViewItem> walklistviewitemList=new ArrayList<WalkListViewItem>();
    //생성자
    public WalkListViewAdapter(){

    }

    //어댑터에 사용되는 데이터의 개수 리턴 필수!
    @Override
    public int getCount() {
        return walklistviewitemList.size();
    }

    //지정한 위치에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return walklistviewitemList.get(position);
    }

    //지정한 위치에 있는 데이터와 관계된 아이템의 id를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }
    // 아이템 데이터 추가를 위한 함수.
    public void addItem(Drawable icon, String title, String desc, String nick) {
        WalkListViewItem item = new WalkListViewItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);
        item.setNick(nick);

        walklistviewitemList.add(item);
    }

    // 아이템 데이터 추가를 위한 함수. ( Test 용 )
    public void addItem() {
        WalkListViewItem item = new WalkListViewItem();
        walklistviewitemList.add(item);
    }

    // position : 리턴 할 자식 뷰의 위치
    // convertView : 메소드 호출 시 position에 위치하는 자식 뷰 ( if == null 자식뷰 생성 )
    // parent : 리턴할 부모 뷰, 어댑터 뷰
    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        //walk_list_item 레이아웃을 inflate하여 convertView 참조획득

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.walk_list_item, parent, false);
        }
        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.walkImage) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.context) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.walkTitle) ;
        TextView userNickView = (TextView) convertView.findViewById(R.id.user_nic) ;


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        WalkListViewItem listViewItem = walklistviewitemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영 ( Test 중 )
        //iconImageView.setImageDrawable(listViewItem.getIcon());
        //titleTextView.setText(listViewItem.getTitle());
        //descTextView.setText(listViewItem.getDesc());

        return convertView;
    }
}
