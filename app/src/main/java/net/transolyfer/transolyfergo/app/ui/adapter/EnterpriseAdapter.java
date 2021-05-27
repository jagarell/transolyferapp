package net.transolyfer.transolyfergo.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.transolyfer.transolyfergo.R;
import net.transolyfer.transolyfergo.app.ui.components.CustomTextView;
import net.transolyfer.transolyfergo.domain.model.Enterprise;

import java.util.List;

/**
 * Created by josecontreras on 7/10/17.
 */

public class EnterpriseAdapter extends BaseAdapter {

    private List<Enterprise> enterpriseList;
    private LayoutInflater inflater = null;

    public EnterpriseAdapter(List<Enterprise> enterpriseList, Context context) {
        this.enterpriseList = enterpriseList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return enterpriseList.size();
    }

    @Override
    public Object getItem(int position) {
        return enterpriseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.row_single_view, null);
            holder = new ViewHolder();
            holder.tviEnterprise = (CustomTextView) convertView
                    .findViewById(R.id.tvTextView);

            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }

        Enterprise enterprise = (Enterprise) getItem(position);
        holder.tviEnterprise.setText(enterprise.getEnterpriseDescription());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.row_drop_down, null);
            holder = new ViewHolder();
            holder.tviEnterprise = (CustomTextView) convertView
                    .findViewById(R.id.tvTextView);

            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }

        Enterprise enterprise = (Enterprise) getItem(position);
        holder.tviEnterprise.setText(enterprise.getEnterpriseDescription());

        return convertView;
    }

    class ViewHolder {
        CustomTextView tviEnterprise;
    }
}
