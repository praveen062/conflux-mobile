package com.mifos.mifosxdroid.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mifos.mifosxdroid.R;
import com.mifos.objects.CgtData;
import com.mifos.objects.ClientMember;
import com.mifos.objects.GrtData;
import com.mifos.objects.client.Client;
import com.mifos.services.API;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by conflux37 on 12/7/2015.
 */
public class ClientListAttendanceAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    List<Client> pageItems;
    API api;
    ReusableViewHolder reusableViewHolder;
    List<ClientMember> clientMembers;
    private GrtData grtData;
    public ClientListAttendanceAdapter(Context context, List<Client> pageItems, API api,GrtData grtData) {

        layoutInflater = LayoutInflater.from(context);
        this.pageItems = pageItems;
        this.api = api;
        this.grtData = grtData;
    }

    @Override
    public int getCount() {
        return pageItems.size();
    }

    @Override
    public Client getItem(int position) {
        return pageItems.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.row_client_attendance, null);
            reusableViewHolder = new ReusableViewHolder(view);
            view.setTag(reusableViewHolder);

        } else {
            reusableViewHolder = (ReusableViewHolder) view.getTag();
        }
        Client client = pageItems.get(i);

        for (ClientMember clientMember : grtData.getClientMembers()) {
            System.out.println("client id" + client.getId() + " client members " + clientMember.getId());
            if (clientMember.getId().equals(client.getId())) {
                reusableViewHolder.tvClientName.setText(client.getFirstname() + ", " + client.getLastname());
                reusableViewHolder.tvClientAttendance.setText(clientMember.getAttendance());
            }
        }
     /*   ClientMember clientMem = clientMembers.get(i);
        System.out.println("client id" +clientMem.getId()+"attendance"+ clientMem.getAttendance());
        reusableViewHolder.tvClientAttendance.setText(clientMem.getAttendance());
        reusableViewHolder.tvClientName.setText(pageItems.get(i).getFirstname()+", "+pageItems.get(i).getLastname());

    */
        return view;
    }

    public int absentCount(Client client)
    {
        int count =0;
        for(ClientMember clientMem: clientMembers)
        {
            if(clientMem.getId().equals(client.getId()))
            {
                count++;
            }
        }
        return  count;
    }

    static class ReusableViewHolder {

        @InjectView(R.id.tv_client_name)
        TextView tvClientName;
        @InjectView(R.id.tv_client_attendance)
        TextView tvClientAttendance;
        public ReusableViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }
}
