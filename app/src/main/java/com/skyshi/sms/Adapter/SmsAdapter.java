package com.skyshi.sms.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skyshi.sms.Model.SmsModel;
import com.skyshi.sms.R;

import java.util.ArrayList;

/**
 * Created by skyshi on 12/04/16.
 */
public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.ViewHolder> {
    ArrayList<SmsModel> smsModels;
    ArrayList<SmsModel> smsModelNoDuplicate = new ArrayList<>();
    Activity act;
    Context ctx;
    public SmsAdapter(ArrayList<SmsModel>smsModels,Activity act,Context context){
        this.smsModels = smsModels;
        this.act = act;
        this.ctx = context;
        for (int i = 0; i <smsModels.size() ; i++) {
            if(smsModelNoDuplicate.size()>0) {
                if(!smsModelNoDuplicate.contains(smsModels.get(i))){
                    smsModelNoDuplicate.add(smsModels.get(i));
                }
            }else{
                smsModelNoDuplicate.add(smsModels.get(i));
            }
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_sms,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.address.setText(smsModelNoDuplicate.get(position).getAddress());
        holder.body.setText(smsModelNoDuplicate.get(position).getBody());
        holder.count.setText(smsModelNoDuplicate.get(position).getCount()+"");
        holder.date.setText("Tanggal : "+smsModelNoDuplicate.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return smsModelNoDuplicate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView address,body,date,count;
        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardView);
            address = (TextView)itemView.findViewById(R.id.txt_address);
            body = (TextView)itemView.findViewById(R.id.txt_body);
            date = (TextView)itemView.findViewById(R.id.txt_date);
            count = (TextView)itemView.findViewById(R.id.txt_count);
        }
    }
}
