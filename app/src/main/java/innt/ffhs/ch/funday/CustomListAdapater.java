package innt.ffhs.ch.funday;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class CustomListAdapater extends BaseAdapter {

    private ArrayList<String> names;
    private ArrayList<String> availabilitys;
    private ArrayList<String> prices;
    private ListFragment2 activity;

    private int x=0;


    public CustomListAdapater(ArrayList<String> names, ArrayList<String> availabilitys, ArrayList<String> prices, ListFragment2 activity){
        this.names=names;
        this.availabilitys=availabilitys;
        this.prices=prices;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view =  LayoutInflater.from(activity.getContext()).inflate(R.layout.row,viewGroup,false);
        ((TextView)view.findViewById(R.id.names)).setText(String.valueOf(names.get(i)));
        ((TextView)view.findViewById(R.id.availabilitys)).setText(availabilitys.get(i));
        ((TextView)view.findViewById(R.id.prices)).setText(prices.get(i));
        return view;
    }
}