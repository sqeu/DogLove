package com.andtinder.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.andtinder.R;
import com.andtinder.model.CardModel;

public final class SimpleCardStackAdapter extends CardStackAdapter {
    public SimpleCardStackAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getCardView(int position, final CardModel model, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.std_card_inner, parent, false);
            assert convertView != null;
        }

        ((ImageView) convertView.findViewById(R.id.image)).setImageDrawable(model.getCardImageDrawable());
        ((TextView) convertView.findViewById(R.id.title)).setText(model.getTitle());
      //  ((TextView) convertView.findViewById(R.id.description)).setText(model.getDescription());


       /** final TextView like_dislike_text = ((TextView) convertView.findViewById(R.id.like_dislike_text));
        if(model.isLike())
            like_dislike_text.setText("Liked");
        else
            like_dislike_text.setText("DisLiked");

        ((ImageView) convertView.findViewById(R.id.like)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                model.setLike(true);
                like_dislike_text.setText("Liked");
            }
        });

        ((ImageView) convertView.findViewById(R.id.dislike)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                model.setLike(false);
                like_dislike_text.setText("DisLiked");
            }
        });**/
        //////////////
       /* ((ImageView) convertView.findViewById(R.id.like)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                model.setLike(true);
                Log.w("hizo click", "clickkk");

            }
        });

        ((ImageView) convertView.findViewById(R.id.dislike)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                model.setLike(false);
                Log.w("hizo click", "clickkk dislike");

            }
        });*/

    /*protected void onCreate(Bundle savedInstanceState) {
        imgDisLike.setOnClickListener(this);
        imgLike.setOnClickListener(this);

    }
    public void onClick(View view) {
        if (view.getId() == R.id.like) {
            Log.w("hizo click", "clickkk");
        }else{

        }
    }*/
    ////////////////
        return convertView;
    }
}
