package jcsr.satelliteproject.satellitecompass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by JuanCarlos on 4/13/14.
 */
public class JSONRowAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONRowAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }

    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        return mJsonArray.optJSONObject(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if ( convertView == null ) {
            convertView = mInflater.inflate(R.layout.orbit_row, null);
            holder = new ViewHolder();
            holder.fechaPase = (TextView)convertView.findViewById(R.id.fecha_pase);
            holder.horaPase = (TextView)convertView.findViewById(R.id.hora_pase);
            holder.azimuth = (TextView)convertView.findViewById(R.id.azimuth);
            convertView.setTag(holder);
        } else {
            holder = ( ViewHolder )convertView.getTag();
        }
        JSONObject jsonObject = (JSONObject)getItem(pos);
        String fechaPase = "";
        String horaPase = "";
        String azimuth = "";
        String elev = "";
        if (jsonObject.has("AOS_Fecha")) {
            fechaPase = jsonObject.optString("AOS_Fecha");
        }
        if ( jsonObject.has("AOS_Hora")) {
            horaPase = jsonObject.optString("AOS_Hora");
        }
        if ( jsonObject.has("AOSAz")) {
            azimuth = jsonObject.optString("AOSAz");
            elev = jsonObject.optString("MaxEl");

        }

        holder.fechaPase.setText( fechaPase);
        holder.horaPase.setText(horaPase);
        holder.azimuth.setText("AZ = "  + azimuth + "/ EL = " + elev);


        return convertView;
    }


    private static class ViewHolder {
        public TextView fechaPase;
        public TextView horaPase;
        public TextView azimuth;
    }

    public void updateData(JSONArray jsonArray) {
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }
}
