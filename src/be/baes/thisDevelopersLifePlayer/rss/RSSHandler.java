package be.baes.thisDevelopersLifePlayer.rss;

import android.util.Log;
import be.baes.thisDevelopersLifePlayer.Constants;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RSSHandler extends DefaultHandler 
{
	
	RSSFeed _feed;
	RSSItem _item;
	final int RSS_TITLE = 1;
	final int RSS_LINK = 2;
	final int RSS_PUBDATE = 5;
    final int RSS_DESCRIPTION = 3;

	int depth = 0;
	int currentstate = 0;
    private static final int RSS_IMAGE = 4;

    /*
      * Constructor 
      */
	public RSSHandler()
	{
	}
	
	/*
	 * getFeed - this returns our feed when all of the parsing is complete
	 */
	public RSSFeed getFeed()
	{
		return _feed;
	}
	
	
	public void startDocument() throws SAXException
	{
		// initialize our RSSFeed object - this will hold our parsed contents
		_feed = new RSSFeed();
		// initialize the RSSItem object - we will use this as a crutch to grab the info from the channel
		// because the channel and items have very similar entries..
		_item = new RSSItem();

	}
	public void endDocument() throws SAXException
	{
	}
	
	public void startElement(String namespaceURI, String localName,String qName, Attributes atts) throws SAXException
	{
		depth++;
		if (localName.equals("channel"))
		{
			currentstate = 0;
			return;
		}
		if (localName.equals("item"))
		{
			// create a new item
			_item = new RSSItem();
			return;
		}
		if (localName.equals("title"))
		{
			currentstate = RSS_TITLE;
			return;
		}
        if(localName.equals("description"))
        {
            currentstate = RSS_IMAGE;
            return;
        }
		if (localName.equals("link"))
		{
			currentstate = RSS_LINK;
			return;
		}
		if (localName.equals("pubDate"))
		{
			currentstate = RSS_PUBDATE;
			return;
		}
        if(qName.equals("itunes:summary"))
        {
            currentstate = RSS_DESCRIPTION;
            return;
        }
		if (qName.equals("media:content"))
		{
			_item.setMp3Link(atts.getValue(0));
			return;
		}
		// if we don't explicitly handle the element, make sure we don't wind up erroneously 
		// storing a newline or other bogus data into one of our existing elements
		currentstate = 0;
	}
	
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException
	{
        switch (currentstate)
        {
            case RSS_TITLE:
                _item.setTitle(buffer.toString());
                currentstate = 0;
                break;
            case RSS_LINK:
                _item.setLink(buffer.toString());
                currentstate = 0;
                break;
            case RSS_PUBDATE:
                _item.setPubDate(buffer.toString());
                currentstate = 0;
                break;
            case RSS_DESCRIPTION:
                _item.setDescription(buffer.toString());
                currentstate = 0;
                break;
            case RSS_IMAGE:
                _item.setImageLink(makeImageLink(buffer.toString()));
                currentstate = 0;
                break;
        }
        buffer = new StringBuffer();
		depth--;
		if (localName.equals("item"))
		{
			_feed.addItem(_item);
		}
	}

    private StringBuffer buffer = new StringBuffer();
                             
	public void characters(char ch[], int start, int length)
	{
		buffer.append(new String(ch,start,length));
	}
    
    private String makeImageLink(String description)
    {
        Log.i(Constants.LOG_ID, "Description:" + description);
        Pattern pattern = Pattern.compile("\\<img[^\\&]*src=[\"']([^'\"]*)['\"]");
        Matcher matcher = pattern.matcher(description);
        boolean matchFound = matcher.find();
        String path = "";
        Log.i(Constants.LOG_ID, "match found: " + matchFound);
        if (matchFound) {
            Log.i(Constants.LOG_ID, "Groupcount: " + matcher.groupCount());
            Log.i(Constants.LOG_ID, "Group 1 = " + matcher.group(1));
            path = matcher.group(1);
        }
        return path;
    }
}
