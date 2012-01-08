package be.baes.thisDevelopersLifePlayer.view.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import be.baes.thisDevelopersLifePlayer.Constants;
import be.baes.thisDevelopersLifePlayer.R;

import java.io.File;
import java.util.List;

public class PodCastAdapterImpl extends ArrayAdapter<be.baes.thisDevelopersLifePlayer.model.PodCast>
{
    private	int resource;
    private be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    private be.baes.thisDevelopersLifePlayer.resources.ColorResources colorResources;
    private be.baes.thisDevelopersLifePlayer.facade.Settings settings;

	public PodCastAdapterImpl(Context context, int resource, List<be.baes.thisDevelopersLifePlayer.model.PodCast> items, be.baes.thisDevelopersLifePlayer.facade.Settings settings, be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources1, be.baes.thisDevelopersLifePlayer.resources.ColorResources colorResources1)
	{
        super(context, resource, items);
        this.stringResources = stringResources1;
        this.colorResources = colorResources1;
        this.settings = settings;
		this.resource=resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LinearLayout rssItemView;
        be.baes.thisDevelopersLifePlayer.model.PodCast podCast = getItem(position);

		if(convertView==null)
		{
			rssItemView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi;
			vi = (LayoutInflater)getContext().getSystemService(inflater);
			vi.inflate(resource, rssItemView, true);
		}
		else
		{
			rssItemView = (LinearLayout) convertView;
		}
		TextView rowTitle =(TextView)rssItemView.findViewById(R.id.rowTitle);
		TextView rowPubDate =(TextView)rssItemView.findViewById(R.id.rowPubDate);
        ImageView img = (ImageView)rssItemView.findViewById(R.id.img);

        File downloadedFile = new File(settings.getPodCastsDirectory(), podCast.getPodCastName());
        if (downloadedFile.exists()) {
            rowTitle.setTextColor(colorResources.getColorGreen());
        }
        else
        {
            rowTitle.setTextColor(colorResources.getColorWhite());
        }
        rowTitle.setText(String.format(stringResources.getListViewTitleText(), podCast.getTitle()));
        rowPubDate.setText(podCast.getPubDate());
        File imgFile = new  File(settings.getImagesDirectory(), podCast.getFileName());
        Log.i(Constants.LOG_ID, "Image path: " + imgFile.getPath());
        if(imgFile.exists()){
            Log.i(Constants.LOG_ID, "Loading image");
            Bitmap bmImg = BitmapFactory.decodeFile(imgFile.getPath());
            img.setImageBitmap(bmImg);
        }
        return rssItemView;
	}

}
