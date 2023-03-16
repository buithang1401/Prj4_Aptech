package com.QLTC.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.QLTC.DetailInfo;
import com.QLTC.Module.Detail;
import com.QLTC.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.DetailViewHolder> {
    private List<Detail> mDetailList;

    private static String formatCurrency(String number){
        DecimalFormat formatter = new DecimalFormat("###,###,###,###,### VND");
        return formatter.format(Double.parseDouble(number));
    }

    public DetailListAdapter(List<Detail> mDetailList) {
        this.mDetailList = mDetailList;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_info, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        Detail detailinfo = mDetailList.get(position);
        if (detailinfo == null) {
            return;
        }
        holder.mDetailInfo.setText("Phương thưc: " + detailinfo.getmType() +"      "+"Phân loại:"+detailinfo.getmCategory() +" \nChi tiết: " + detailinfo.getmDetail() + " \nThời gian: " + detailinfo.getmTime() + " \nSố Tiền: " + formatCurrency(detailinfo.getmMoney()) + "");

        holder.mDelete.setOnClickListener(v -> {
            DatabaseReference reference = FirebaseDatabase.getInstance("https://qltc-53f51-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("User");
            Query query = reference.equalTo(detailinfo.getmDetail(), detailinfo.getmTime());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled", databaseError.toException());
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        if (mDetailList != null) {
            return mDetailList.size();
        }
        return 0;
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView mDetailInfo;
        Button mDelete;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            mDetailInfo = itemView.findViewById(com.QLTC.R.id.tvDetailInfo);
            mDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
