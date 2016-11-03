package com.example.cws.myappss;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   RecyclerView recyclerView;

    ArrayList<DataObject> textViews;
    MyRecycleAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViews = new ArrayList<>();
        for(int ii = 0 ; ii < 25; ii++)
        {
            DataObject dataObject = new DataObject();
            dataObject.setName("View "+ii);
            dataObject.setId("Id "+ii);
            dataObject.setTag("Tag "+ii);
            textViews.add(dataObject);

        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyRecycleAdapter(textViews);
        recyclerView.setAdapter(mAdapter);
        ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
        ith.attachToRecyclerView(recyclerView);
    }


    ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback()
    {
          public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // get the viewHolder's and target's positions in your adapter data, swap them
            Collections.swap(textViews, viewHolder.getAdapterPosition(), target.getAdapterPosition());
            // and notify the adapter that its dataset has changed
            mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //TODO
        }

        //defines the enabled move directions in each state (idle, swiping, dragging).
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                    ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
        }
    };


    public static class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter
            .DataObjectHolder>
    {
        private static MyClickListener myClickListener;
        private ArrayList<DataObject> mDataset;

        public  class DataObjectHolder extends RecyclerView.ViewHolder
                implements View
                .OnClickListener {
            TextView label;
            TextView dateTime;

            public DataObjectHolder(final View itemView) {
                super(itemView);
                label = (TextView) itemView.findViewById(R.id.textView);
                label.setTag("Tag "+getPosition());

                itemView.setOnClickListener(this);
                label.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if(view != null) {
                            Log.i("AdapterDataOBject", "AddingListener "+getPosition());
                            Log.d("Nhi","ssH"+label.getTag());

                        }
                        else
                            Log.d("Nhi","H");
                        return false;
                    }
                });
            }

            @Override
            public void onClick(View v) {
               Log.d("ClickItem"," - "+getPosition());
            }
        }

        public void setOnItemClickListener(MyClickListener myClickListener) {
            this.myClickListener = myClickListener;
        }
        public MyRecycleAdapter(ArrayList<DataObject> mDataset) {
this.mDataset = mDataset;
        }

        @Override
        public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item, parent, false);

            DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
            return dataObjectHolder;
        }

        @Override
        public void onBindViewHolder(DataObjectHolder holder, int position) {
            holder.label.setTag("Tag "+position);
            holder.label.setText(mDataset.get(position).getName());

        }

        public void addItem(DataObject dataObj, int index) {
            mDataset.add(dataObj);
            notifyItemInserted(index);
        }

        public void deleteItem(int index) {
            mDataset.remove(index);
            notifyItemRemoved(index);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public interface MyClickListener {
            public void onItemClick(int position, View v);
        }

    }
}
