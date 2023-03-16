package com.QLTC.adapter;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.QLTC.Module.Detail;
import com.QLTC.Module.Save;
import com.QLTC.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

public class SaveListAdapter extends RecyclerView.Adapter<SaveListAdapter.SaveViewHolder> {

    private List<Save> mSaveList;

    private static String formatCurrency(String number){
        DecimalFormat formatter = new DecimalFormat("###,###,###,###,### VND");
        return formatter.format(Double.parseDouble(number));
    }

    public SaveListAdapter(List<Save> mSaveList) {
        this.mSaveList = mSaveList;
    }

    @NonNull
    @Override
    public SaveListAdapter.SaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_info, parent, false);
        return new SaveListAdapter.SaveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveListAdapter.SaveViewHolder holder, int position) {
        Save saveinfo = mSaveList.get(position);
        if (saveinfo == null) {
            return;
        }
        holder.mSaveInfo.setText("Khoản Tiết Kiệm : " + saveinfo.getmName() +"\nNgày Bắt Đầu: " + saveinfo.getmTime() + " \nMục Tiêu: " + formatCurrency(saveinfo.getmTargetMoney()) + " \nĐã Tiết Kiệm: " + formatCurrency(saveinfo.getmCurrentMoney()) + "");

    }

    @Override
    public int getItemCount() {
        if (mSaveList != null) {
            return mSaveList.size();
        }
        return 0;
    }

    static class SaveViewHolder extends RecyclerView.ViewHolder {
        private TextView mSaveInfo;
        ProgressBar mProgress;

        public SaveViewHolder(@NonNull View itemView) {
            super(itemView);
            mSaveInfo = itemView.findViewById(com.QLTC.R.id.tvSaveinfo);
            mProgress = itemView.findViewById(R.id.pbSave);
        }
    }
}
