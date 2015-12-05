package com.love.dog.doglove;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.love.dog.doglove.DTO.MensajeDTO;
import com.love.dog.doglove.adapter.ChatListAdapter;
import com.love.dog.doglove.parse.Message;
import com.love.dog.doglove.presenter.IObtenerMensajesPresenter;
import com.love.dog.doglove.presenter.IRegistroMensajePresenter;
import com.love.dog.doglove.presenter.ObtenerMensajesPresenter;
import com.love.dog.doglove.presenter.RegistroMensajePresenter;
import com.love.dog.doglove.view.ActualizarPrefView;
import com.love.dog.doglove.view.GenericView;
import com.love.dog.doglove.view.ObtenerMensajesView;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ChatFragment extends Fragment implements View.OnClickListener, ObtenerMensajesView ,GenericView{
    private EditText etMessage;
    private Button btSend;

    private ListView lvChat;
    private List<MensajeDTO> mMessages;
    private ChatListAdapter mAdapter;

    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;

    // Keep track of initial load to scroll to the bottom of the ListView
    private boolean mFirstLoad;

    private Handler handler = new Handler();

    private String idChat;
    String idMascota,idFoto2;

    private final static int INTERVAL = 2000 ; //2 minutes
    Handler mHandler=new Handler();

    Runnable mHandlerTask = new Runnable()
    {
        @Override
        public void run() {
            refreshMessages();
            mHandler.postDelayed(mHandlerTask, INTERVAL);
        }
    };

    void startRepeatingTask()
    {
        mHandlerTask.run();
    }

    void stopRepeatingTask()
    {
        mHandler.removeCallbacks(mHandlerTask);
    }


    public static ChatFragment newInstance(){
        return new ChatFragment();
    }

    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent=getActivity().getIntent();
        idChat=intent.getStringExtra("idchat");

        SharedPreferences settings = getActivity().getSharedPreferences("MiFoto", 0);
        String idFoto1 = settings.getString("idfoto1", null);

        idFoto2=intent.getStringExtra("idfoto2");
        System.out.println("idChat"+idChat);
        System.out.println("ChatFragment :" +idFoto1+"::"+idFoto2);
        idMascota=intent.getStringExtra("idPerro");
        //System.out.println("idDueno"+idMascota);

        System.out.println("Chat: "+idChat + ":: idPerro: "+idMascota);

        etMessage = (EditText) getView().findViewById(R.id.etMessage);
        btSend = (Button) getView().findViewById(R.id.btSend);
        btSend.setOnClickListener(this);
        lvChat = (ListView) getView().findViewById(R.id.lvChat);
        mMessages = new ArrayList<MensajeDTO>();
        lvChat.setTranscriptMode(1);
        mFirstLoad = true;
        mAdapter = new ChatListAdapter(getActivity(), idMascota, mMessages,idFoto1,idFoto2);
        lvChat.setAdapter(mAdapter);



        // Run the runnable object defined every 100ms
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                refreshMessages();
            }
        }, 0, 5, TimeUnit.SECONDS);



    }

    /*private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, 1000);
        }
    };*/

    private void refreshMessages() {
        IObtenerMensajesPresenter obtenerMensajesPresenter=new ObtenerMensajesPresenter(this);
        obtenerMensajesPresenter.obtenerMensajes(idChat);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btSend) {
            IRegistroMensajePresenter registroMensajePresenter = new RegistroMensajePresenter(this);
            registroMensajePresenter.registrar(idChat, etMessage.getText().toString(), idMascota);

            etMessage.setText("");
        }
    }

    @Override
    public void onObtencionCorrecto(List<MensajeDTO> mensajes) {

        mMessages.clear();
        mMessages.addAll(mensajes);
        mAdapter.notifyDataSetChanged(); // update adapter
        // Scroll to the bottom of the list on initial load
        if (mFirstLoad) {
            lvChat.setSelection(mAdapter.getCount() - 1);
            mFirstLoad = false;
        }
      //  handler.postDelayed(runnable, 1000);

    }

    @Override
    public void onRegistroCorrecto() {
        //recibir mensajes
        // Construct query to execute
        IObtenerMensajesPresenter obtenerMensajesPresenter=new ObtenerMensajesPresenter(this);
        obtenerMensajesPresenter.obtenerMensajes(idChat);

    }

    @Override
    public void onError(String msg) {
        /*Toast.makeText(
                getActivity(),
                "EXC: " + msg,
                Toast.LENGTH_LONG
        ).show();*/
    }

    @Override
    public ApplicationController getApplicationController() {
        return (ApplicationController) getActivity().getApplication();
    }


}
