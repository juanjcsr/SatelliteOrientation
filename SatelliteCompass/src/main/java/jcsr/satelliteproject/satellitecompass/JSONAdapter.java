package jcsr.satelliteproject.satellitecompass;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by JuanCarlos on 4/12/14.
 */
public class JSONAdapter extends BaseAdapter {

    private static final String IMAGE_URL = "http://covers.openlibrary.org/b/id/";

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }

    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int pos) {
        return mJsonArray.optJSONObject(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //revisa si ya existe la vista
        if ( convertView == null ) {
            convertView = mInflater.inflate(R.layout.satellite_row,null);

            //Holder con subviews
            holder = new ViewHolder();
            holder.satImg = (ImageView)convertView.findViewById(R.id.img_satellite);
            holder.satName = (TextView)convertView.findViewById(R.id.sat_name);
            holder.satDesc = (TextView)convertView.findViewById(R.id.sat_desc);
            //tag para poder reciclar
            convertView.setTag(holder);
        } else {
            //reusa la vista
            holder = (ViewHolder) convertView.getTag();
        }
        //Obtener el satelite actual
        JSONObject jsonObject = (JSONObject)getItem(pos);
        if ( jsonObject.has("cover_i")) {
            String imageid = jsonObject.optString("cover_i");
            String imageurl = IMAGE_URL + imageid + "-S.jpg";
            Log.d("imagen", imageurl);
            //Picasso.with(mContext).load(imageurl).placeholder(R.drawable.sateliteicon).into(holder.satImg);
        } else {
            holder.satImg.setImageResource(R.drawable.sateliteicon);
        }
        String satname = "";
        String satdesc = "";
        if ( jsonObject.has("titulo")) {
            satname = jsonObject.optString("titulo");
        }
        if ( jsonObject.has("author_name")) {
            satdesc = jsonObject.optJSONArray("author_name").optString(0);
        }

        //muestralas en los TextView
        holder.satName.setText(satname);
        holder.satDesc.setText(satdesc);

        return convertView;
    }

    //ViewHolder par alas vistas
    private static class ViewHolder {
        public ImageView satImg;
        public TextView satName;
        public  TextView satDesc;
    }

    public void updateData(JSONArray jsonArray) {
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }
}
