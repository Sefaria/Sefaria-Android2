package org.sefaria.sefaria.LinkElements;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.sefaria.sefaria.MyApp;
import org.sefaria.sefaria.R;
import org.sefaria.sefaria.Util;
import org.sefaria.sefaria.database.Book;
import org.sefaria.sefaria.database.Link;
import org.sefaria.sefaria.database.Text;

import java.util.List;

/**
 * Created by nss on 1/17/16.
 */

public class LinkTextAdapter extends RecyclerView.Adapter<LinkTextAdapter.LinkTextHolder> {

    private List<Text> itemList;
    private Context context;
    private Link.LinkCount currLinkCount;

    public class LinkTextHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public TextView verseNum;

        public LinkTextHolder(View v) {
            super(v);
            tv = (TextView) v.findViewById(R.id.tv);
            verseNum = (TextView) v.findViewById(R.id.verseNum);
        }
    }


    public LinkTextAdapter(Context context, List<Text> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public LinkTextHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_text, null);
        LinkTextHolder linkHolder = new LinkTextHolder(layoutView);
        return linkHolder;
    }

    @Override
    public void onBindViewHolder(LinkTextHolder holder, int position) {
        Text link = itemList.get(position);
        holder.verseNum.setText(""+link.levels[0]);
        holder.tv.setText(Html.fromHtml(link.heText));
        holder.tv.setTypeface(MyApp.getFont(MyApp.TAAMEY_FRANK_FONT));
        holder.tv.setTextSize(20);

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void setItemList(List<Text> items) {
        itemList = items;
        notifyDataSetChanged();
    }

    public Text getItem(int position) {
        return itemList.get(position);
    }

    //segment is used to update text list
    public void setCurrLinkCount(Link.LinkCount linkCount, Text segment) {
        //try not to update too often
        if (!linkCount.equals(currLinkCount)) {
            currLinkCount = linkCount;
            if (segment != null) //o/w no need to update itemList. You probably just initialized LinkTextAdapter
                setItemList(Link.getLinkedTexts(segment, currLinkCount));
        }
    }
    public Link.LinkCount getCurrLinkCount() { return currLinkCount; }

}