package org.sefaria.sefaria.TOCElements;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.sefaria.sefaria.MyApp;
import org.sefaria.sefaria.R;
import org.sefaria.sefaria.Util;
import org.sefaria.sefaria.activities.SectionActivity;
import org.sefaria.sefaria.activities.TextActivity;
import org.sefaria.sefaria.database.Node;

import java.util.Objects;

/**
 * Created by LenJ on 12/29/2015.
 */
public class TOCSectionName extends LinearLayout implements TOCElement {

    private TextView sectionroot;
    private Context context;
    private Node node;
    private boolean displayLevel;
    private LinearLayout This = this;
    private Util.Lang lang;

    /*
    public  TOCSectionName(Context context){
        super(context);
        this.context = context;
        this.setVisibility(View.INVISIBLE);
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.setOrientation(VERTICAL);
    }
    */
    public TOCSectionName(Context context, Node node, Util.Lang lang, boolean displayLevel){
        super(context);
        inflate(context, R.layout.toc_sectionname, this);
        this.setOrientation(VERTICAL);
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        this.lang = lang;
        this.node = node;
        this.context = context;
        this.displayLevel = displayLevel;
        init(lang);

    }

    private void init(Util.Lang lang){
        sectionroot = (TextView) findViewById(R.id.toc_sectionroot);
        sectionroot.setTypeface(MyApp.getFont(MyApp.TAAMEY_FRANK_FONT));

        setLang(lang);
        this.setOnClickListener(clickListener);
    }

    @Override
    public void setLang(Util.Lang lang) {
        if (!displayLevel) {
            sectionroot.setVisibility(View.INVISIBLE);
            this.setPadding(0, 0, 0, 0);
            return;
        }
        final int padding = 12;
        final int sidePadding = 50;
        if(lang == Util.Lang.EN) {
            this.setPadding(sidePadding, padding, 0, padding);
            this.setGravity(Gravity.LEFT);
        }else {
            this.setPadding(0, padding, sidePadding, padding);
            this.setGravity(Gravity.RIGHT);
        }
        String text = ""+ node.getTitle(lang);
        if(isContainer()) {
            //text += " v";
            ;//text += " " + "\u2228";
        }else {
            text += " >";
        }
        sectionroot.setText(text);

    }

    private boolean isContainer(){
        return node.getChildren().size() > 0;

    }


    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO go to intent of text page
            Log.d("toc", "sectionanem _ node:" + node);
            try {
                Log.d("toc", "texts:" + node.getTexts());
            }catch (Exception e){
                ;
            }
            if(false && isContainer()){ //TODO maybe try to make this work at some point .. && get rid of false
                for(int i=0;i<This.getChildCount();i++){
                    View child = This.getChildAt(i);
                    if(child == sectionroot)
                        continue;
                    if(child.getVisibility() == View.INVISIBLE)
                        setVisibility(View.VISIBLE);
                    else
                        setVisibility(View.INVISIBLE);
                }
            }

            //TODO determine if it's a leaf and if so then display text
            if(isContainer())
                return;
            Node.saveNode(node);
            Intent intent = new Intent(context, SectionActivity.class);
            intent.putExtra("nodeHash", node.hashCode());
            intent.putExtra("lang", lang);
            context.startActivity(intent);


        }
    };
}
