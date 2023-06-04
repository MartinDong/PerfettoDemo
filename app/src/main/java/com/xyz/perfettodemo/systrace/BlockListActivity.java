package com.xyz.perfettodemo.systrace;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xyz.perfettodemo.BitmapUtils;
import com.xyz.perfettodemo.R;
import com.xyz.perfettodemo.bean.Droid;

public class BlockListActivity extends AppCompatActivity {
    private ListView mListView;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_list);
        mInflater = LayoutInflater.from(this);
        mListView = findViewById(R.id.id_listview_chats);
        mListView.setAdapter(new ArrayAdapter<Droid>(this, -1, Droid.generateDatas()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_list_block, parent, false);
                    holder = new ViewHolder();
                    holder.icon = (ImageView) convertView.findViewById(R.id.id_chat_icon);
                    holder.name = (TextView) convertView.findViewById(R.id.id_chat_name);
                    holder.date = (TextView) convertView.findViewById(R.id.id_chat_date);
                    holder.msg = (TextView) convertView.findViewById(R.id.id_chat_msg);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                Droid droid = getItem(position);
                holder.icon.setBackgroundColor(0x44ff0000);
                // 压缩图片
                Bitmap bitmap = BitmapUtils.INSTANCE.zipBitMap(getResources(),droid.imageId);
                holder.icon.setImageBitmap(bitmap);
                holder.date.setText(droid.date);
                holder.msg.setText(droid.msg);
                holder.name.setText(droid.name);
                return convertView;
            }

            class ViewHolder {
                ImageView icon;
                TextView name;
                TextView date;
                TextView msg;
            }
        });

    }
}