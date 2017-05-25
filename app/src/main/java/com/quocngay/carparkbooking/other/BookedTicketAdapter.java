package com.quocngay.carparkbooking.other;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.quocngay.carparkbooking.R;
import com.quocngay.carparkbooking.model.BookedTicketModel;
import com.quocngay.carparkbooking.model.GaraModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ninhh on 5/23/2017.
 */

public class BookedTicketAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<BookedTicketModel> listTicket;
    private Context context;

    public BookedTicketAdapter(List<BookedTicketModel> listTicket, Context context) {
        if(listTicket != null) {
            this.listTicket = listTicket;
        } else {
            this.listTicket = new ArrayList<>();
        }
        this.listTicket = listTicket;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BookedTicketAdapter.ViewHolder holder = null;

        if(convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.item_booked_ticket, null);

            holder = new BookedTicketAdapter.ViewHolder();
            holder.txtGaraName = (TextView) convertView.findViewById(R.id.tv_gara_name);
            holder.txtGaraAddress = (TextView) convertView.findViewById(R.id.tv_gara_address);
            holder.txtGaraTotalSlot = (TextView) convertView.findViewById(R.id.tv_gara_total_slot);
            holder.txtGaraBookedSlot = (TextView) convertView.findViewById(R.id.tv_gara_booked_slot);
            holder.txtCountTime = (TextView) convertView.findViewById(R.id.tv_time_count);
            holder.btnCheck = (Button) convertView.findViewById(R.id.btn_check);
            convertView.setTag(holder);

            //on click event
            holder.btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            holder = (BookedTicketAdapter.ViewHolder) convertView.getTag();
        }

        BookedTicketModel bookedTicketModel = listTicket.get(position);
        GaraModel garaModel = bookedTicketModel.getGaraModel();
        holder.txtGaraName.setText(garaModel.getName());
        holder.txtGaraAddress.setText(garaModel.getAddress());
        holder.txtGaraTotalSlot.setText(context.getResources().getString(R.string.gara_total) + " " + garaModel.getTotalSlot());
        holder.txtGaraBookedSlot.setText(context.getResources().getString(R.string.gara_booked) + " " + garaModel.getBookedSlot());

        if(bookedTicketModel.getCheckoutTime() != null) {
            long diff = bookedTicketModel.getCheckoutTime().getTime() - bookedTicketModel.getCheckinTime().getTime();
            holder.txtCountTime.setText(Constant.KEY_DATE_TIME_DURATION_FORMAT.format(new Date(diff)));
            holder.btnCheck.setVisibility(View.GONE);
        } else if(bookedTicketModel.getCheckinTime() != null) {
            long diff = (new Date()).getTime() - bookedTicketModel.getCheckinTime().getTime();
            holder.txtCountTime.setText(Constant.KEY_DATE_TIME_DURATION_FORMAT.format(new Date(diff)));
            holder.btnCheck.setText(context.getResources().getString(R.string.btn_checkout));
        } else {
            long diff = (new Date()).getTime() - bookedTicketModel.getBookedTime().getTime();
            if(diff < Constant.KEY_EXPIRED_TICKET) {
                holder.txtCountTime.setText(Constant.KEY_TIME_DURATION_FORMAT.format(new Date(diff)));
                holder.btnCheck.setText(context.getResources().getString(R.string.btn_checkin));
            } else {
                holder.txtCountTime.setText(context.getResources().getString(R.string.ticket_expired));
                holder.txtCountTime.setBackground(context.getResources().getDrawable(R.color.expriedCountTime_background));
                holder.btnCheck.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    public void swap(List<BookedTicketModel> list1){
        this.listTicket.clear();
        this.listTicket.addAll(list1);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listTicket.size();
    }

    @Override
    public Object getItem(int position) {
        return listTicket.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtGaraName;
        TextView txtGaraAddress;
        TextView txtGaraTotalSlot;
        TextView txtGaraBookedSlot;
        TextView txtCountTime;
        Button btnCheck;
    }
}
