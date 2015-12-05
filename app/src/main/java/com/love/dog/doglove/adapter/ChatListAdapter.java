package com.love.dog.doglove.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.love.dog.doglove.DTO.MensajeDTO;
import com.love.dog.doglove.R;
import com.love.dog.doglove.parse.Message;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created by Hugo on 10/18/2015.
 */
public class ChatListAdapter extends ArrayAdapter<MensajeDTO> {
    private String mUserId,idFoto1,idFoto2;
    private Bitmap foto1=null, foto2=null;
    public ChatListAdapter(Context context, String userId, List<MensajeDTO> messages, String idFoto1, String idFoto2) {
        super(context, 0, messages);
        this.mUserId = userId;
        this.idFoto1 = idFoto1;
        this.idFoto2 = idFoto2;
    }

    /*public ChatListAdapter(Context context, String userId, List<MensajeDTO> messages) {
        super(context, 0, messages);
        this.mUserId = userId;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.chat_item, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.imageLeft = (CircularImageView)convertView.findViewById(R.id.ivProfileLeft);
            holder.imageRight = (CircularImageView)convertView.findViewById(R.id.ivProfileRight);
            holder.body = (TextView)convertView.findViewById(R.id.tvBody);
            convertView.setTag(holder);
        }
        final MensajeDTO message = (MensajeDTO)getItem(position);
        final ViewHolder holder = (ViewHolder)convertView.getTag();
        final boolean isMe = message.getIdCliente().equals(mUserId);
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            holder.imageRight.setVisibility(View.VISIBLE);
            holder.imageLeft.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        } else {
            holder.imageLeft.setVisibility(View.VISIBLE);
            holder.imageRight.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        }
        final ImageView profileView;
        if (isMe) {
            profileView = holder.imageRight;
            if(foto1!=null){
                profileView.setImageBitmap(foto1);
            }else{
                setImagen(idFoto1,profileView,foto1);
                foto1=profileView.getDrawingCache();
            }

        }       else{
            profileView = holder.imageLeft;
            if(foto2!=null){
                profileView.setImageBitmap(foto2);
            }else{
                setImagen(idFoto2,profileView,foto2);
                foto2=profileView.getDrawingCache();
            }


        }

        //Picasso.with(getContext()).load(getProfileUrl(message.getIdCliente())).into(profileView);
        holder.body.setText(message.getMensaje());
        return convertView;
    }

    public void setImagen(String idFoto,  final ImageView imagen, final Bitmap foto){

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "ImagenMascota");

        // Locate the objectId from the class
        query.getInBackground(idFoto,
                new GetCallback<ParseObject>() {

                    @Override
                    public void done(ParseObject object, com.parse.ParseException e) {
                        ParseFile fileObject = (ParseFile) object
                                .get("ImageFile");
                        fileObject
                                .getDataInBackground(new GetDataCallback() {

                                    @Override
                                    public void done(byte[] data, com.parse.ParseException e) {
                                        if (e == null) {
                                            Log.d("test",
                                                    "We've got data in data.");
                                            // Decode the Byte[] into
                                            // Bitmap
                                            Bitmap bmp= BitmapFactory
                                                    .decodeByteArray(
                                                            data, 0,
                                                            data.length);
                                            imagen.setImageBitmap(bmp);
                                            // Close progress dialog
                                            // progressDialog.dismiss();

                                        } else {
                                            Log.d("test",
                                                    "There was a problem downloading the data.");
                                        }
                                    }

                                });
                    }

                });


    }

    // Create a gravatar image based on the hash value obtained from userId
    private static String getProfileUrl(final String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }

    final class ViewHolder {
        public CircularImageView imageLeft;
        public CircularImageView imageRight;
        public TextView body;
    }
}
