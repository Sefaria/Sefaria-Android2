package org.sefaria.sefaria.TOCElements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.sefaria.sefaria.R;
import org.sefaria.sefaria.Util;
import org.sefaria.sefaria.activities.SectionActivity;
import org.sefaria.sefaria.activities.TextActivity;
import org.sefaria.sefaria.database.API;
import org.sefaria.sefaria.database.Node;
import org.sefaria.sefaria.menu.MenuNode;
import org.sefaria.sefaria.menu.MenuState;

/**
 *
 */
public class TOCNumBox extends TextView implements TOCElement {

    private Context context;
    private Node node;
    private Util.Lang lang;

    public  TOCNumBox(Context context){
        super(context);
        this.context = context;
        this.setVisibility(View.INVISIBLE);
    }

    public TOCNumBox(Context context, Node node, Util.Lang lang){
        super(context);

        //FORMATTING
        Resources r = getResources();

        setBackgroundColor(r.getColor(R.color.menu_foreground));
        setTextColor(r.getColor(R.color.toc_front));


        //TODO why doesn't margins work!??
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) r.getDimension(R.dimen.toc_numbox),(int) r.getDimension(R.dimen.toc_numbox));
        lp.setMargins(10,10,10,10);

        setLayoutParams(lp);

        //setWidth((int) r.getDimension(R.dimen.toc_numbox));
        //setHeight((int) r.getDimension(R.dimen.toc_numbox));

        setPadding(1, 1, 1, 1);
        setTextSize(10);
        setGravity(Gravity.CENTER);

        this.node = node;
        this.context = context;
        this.lang = lang;
        init(lang);

    }

    private void init(Util.Lang lang){
        setLang(lang);

        this.setOnClickListener(clickListener);
    }

    @Override
    public void setLang(Util.Lang lang) {
        if(Util.Lang.HE == lang)
            setText(Util.int2heb(node.getGridNum()));
        else
            setText("" + node.getGridNum());
    }



    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!node.isTextSection()) {
                Log.e("TOCNumBox","TOCNumBox.clickListener isn't a textSection");
                return;
            }
            Node.saveNode(node);
            /*
            try {
                Log.d("toc", "go to:" + node  + node.getGridNum());
            }catch(API.APIException e){//use API.Excet
                ;
            }*/




            Node.saveNode(node);
            Intent intent = new Intent(context, SectionActivity.class);
            intent.putExtra("nodeHash", node.hashCode());
            intent.putExtra("lang", lang);
            //TODO determine if SectionActivity was already open... Make sure to be careful of multi-tab stuff
            context.startActivity(intent);
            /*
            Activity act = (Activity) context; //stupid casting
            act.setResult(Activity.RESULT_OK,intent);
            act.finish();//close the TOC
            */

        }
    };
}