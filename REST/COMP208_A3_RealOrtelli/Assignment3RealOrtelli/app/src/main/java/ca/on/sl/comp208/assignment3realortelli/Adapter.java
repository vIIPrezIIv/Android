package ca.on.sl.comp208.assignment3realortelli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray on 2017-04-07.
 */

public class Adapter extends ArrayAdapter<Items>
{
    private LayoutInflater inflater = LayoutInflater.from(getContext());
    private List<Items> itemList = new ArrayList<>();

    public Adapter(Context context, int textViewResourceId, List<Items> objects){
        super(context, textViewResourceId, objects);
        this.itemList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_layout, null);
        }

        TextView memeText = (TextView) convertView.findViewById(R.id.memeText);
        memeText.setText(itemList.get(position).getTitle());
        ImageView memeImage = (ImageView) convertView.findViewById(R.id.memeImage);
        memeImage.setImageBitmap(itemList.get(position).getImage());
        Button like = (Button) convertView.findViewById(R.id.Like);
        like.setTag(itemList.get(position).getEntityID());
        Button dislike = (Button) convertView.findViewById(R.id.Dislike);
        dislike.setTag(itemList.get(position).getEntityID());

        return convertView;
    }

}
