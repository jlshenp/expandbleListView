package com.list.listview2holder;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class LianTongActivity extends Activity {

    private List<String> fuList;
    private List<String> tuiDingList;
    private List<String> dingGouList;
    private MyAdapter myAdapter;
    private ExpandableListView exPlistView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lian_tong);
        exPlistView = (ExpandableListView) findViewById(R.id.liantong_listview);

        initData();

        myAdapter = new MyAdapter();
        exPlistView.setAdapter(myAdapter);

        for (int i = 0; i < 2; i++) {//首次展开
            exPlistView.expandGroup(i);
        }

        exPlistView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {//父控件不可点击
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    public void initData() {
        fuList = new ArrayList<>();
        fuList.add("我的订购");
        fuList.add("推荐产品");

        dingGouList = new ArrayList<>();
        dingGouList.add("4");
        dingGouList.add("5");
        dingGouList.add("6");

        tuiDingList = new ArrayList<>();
        tuiDingList.add("7");
        tuiDingList.add("8");
        tuiDingList.add("9");
        tuiDingList.add("10");
    }


    private class MyAdapter extends BaseExpandableListAdapter {
        private MyDialog ltDialog;
        private FuHolder fuHolder;
        private ZiHolder ziHolder;

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parentp) {
            if (convertView == null) {
                convertView = LayoutInflater.from(LianTongActivity.this).inflate(R.layout.item_liantong, null);
                fuHolder = new FuHolder(convertView);
                convertView.setTag(fuHolder);
            } else {
                fuHolder = (FuHolder) convertView.getTag();
            }
            fuHolder.title.setText(getGroup(groupPosition));
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(LianTongActivity.this).inflate(R.layout.item_sub_liantong, null);
                ziHolder = new ZiHolder(convertView);
                convertView.setTag(ziHolder);
            } else {
                ziHolder = (ZiHolder) convertView.getTag();
            }
            if (groupPosition == 0) {
                ziHolder.button.setText("退订");
            } else {
                ziHolder.button.setText("订购");
            }
            ziHolder.ziFei.setText(getChild(groupPosition, childPosition));//数据已经在getChild中设置过了

            ziHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (groupPosition == 0) {

                        ltDialog = new MyDialog(LianTongActivity.this, new ConfirmClickListener() {
                            @Override
                            public void onOKBtnClick() {
                                MyDialog confirmDialog = new MyDialog(LianTongActivity.this, new ConfirmClickListener() {
                                    @Override
                                    public void onOKBtnClick() {
                                        String tuidingString = tuiDingList.remove(childPosition);
                                        dingGouList.add(tuidingString);
                                        myAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelBtnClick() {}
                                });
                                confirmDialog.showDialog();
                                confirmDialog.showConfirm("系统提示", "退订成功，立即生效，继续使用将产生流量费用。");
                            }

                            @Override
                            public void onCancelBtnClick() {}
                        });
                        ltDialog.showDialog();
                        ltDialog.show("系统提示", "确认退订？");
                    } else {
                        ltDialog = new MyDialog(LianTongActivity.this, new ConfirmClickListener() {
                            @Override
                            public void onOKBtnClick() {
                                MyDialog confirmDialog = new MyDialog(LianTongActivity.this, new ConfirmClickListener() {
                                    @Override
                                    public void onOKBtnClick() {
                                        String dingGouString = dingGouList.remove(childPosition);
                                        tuiDingList.add(dingGouString);
                                        myAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelBtnClick() {}
                                });
                                confirmDialog.showDialog();
                                confirmDialog.showConfirm("系统提示", "订购成功");
                            }

                            @Override
                            public void onCancelBtnClick() {}
                        });
                        ltDialog.showDialog();
                        ltDialog.show("系统提示", "继续下一步，将成功订购i点点产品包，是否继续？");

                    }
                }
            });

            return convertView;
        }

        @Override
        public int getGroupCount() {
            return fuList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (groupPosition == 0) {
                return tuiDingList.size();
            } else {
                return dingGouList.size();
            }
        }

        @Override
        public String getGroup(int groupPosition) {
            if (groupPosition == 0) {
                return fuList.get(0);
            } else {
                return fuList.get(1);
            }
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            if (groupPosition == 0) {

                return tuiDingList.get(childPosition);
            } else {
                return dingGouList.get(childPosition);
            }

        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        /**
         * 子项目可以被点击
         *
         * @param i
         * @param i1
         * @return
         */
        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }

    class FuHolder {
        TextView title;

        FuHolder(View view) {
            title = (TextView) view.findViewById(R.id.liantong_title);
        }
    }

    class ZiHolder {
        TextView button;
        TextView ziFei;

        ZiHolder(View view) {
            button = (TextView) view.findViewById(R.id.liantong_button);
            ziFei = (TextView) view.findViewById(R.id.liantong_zifei);
        }
    }
}
