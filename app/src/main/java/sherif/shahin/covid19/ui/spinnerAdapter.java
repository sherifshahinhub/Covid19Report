package sherif.shahin.covid19.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import sherif.shahin.covid19.R;

public class spinnerAdapter extends ArrayAdapter<Object> {

    private Context context;
    int resId;
    private String[] mAnimListItems;

    public spinnerAdapter(Context context, int textViewResourceId,
                          String[] strText) {
        super(context, textViewResourceId, strText);
        this.resId = textViewResourceId;
        this.context = context;
        this.mAnimListItems = strText;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(resId, null);
        }

        TextView tv =  convertView.findViewById(R.id.tvItem);
        tv.setTextSize(25f);

        tv.setText("   " + mAnimListItems[position]);

        return convertView;
    }

}
