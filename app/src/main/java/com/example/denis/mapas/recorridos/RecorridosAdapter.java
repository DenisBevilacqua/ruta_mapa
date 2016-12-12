package com.example.denis.mapas.recorridos;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.denis.mapas.R;
import com.example.denis.mapas.modelo.Recorrido;

import java.util.List;

//import android.icu.util.Calendar;
//import android.icu.util.GregorianCalendar;

/**
 * Created by RAIMONDI on 29/09/2016.
 */

public class RecorridosAdapter extends ArrayAdapter<Recorrido> {

    private LayoutInflater inflater;

    public RecorridosAdapter(Context context, List<Recorrido> recorridos) {
        super(context, R.layout.fila_recorrido, recorridos);
        inflater = LayoutInflater.from(context);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View row = convertView;

        //if (row == null) {
            row = inflater.inflate(R.layout.fila_recorrido, parent, false);
        //}

        TextView origen = (TextView) row.findViewById(R.id.origen_recorrido);
        TextView destino = (TextView) row.findViewById(R.id.destino_recorrido);
        TextView id = (TextView) row.findViewById(R.id.id_recorrido);

        origen.setText(this.getItem(position).getNombre_origen().toString());
        destino.setText(this.getItem(position).getNombre_destino().toString());
        id.setText(this.getItem(position).getId().toString());

        Integer color = Color.parseColor("#2196F3");

        origen.setTextColor(color);
        destino.setTextColor(color);
        id.setTextColor(color);

        SpannableString spanString = new SpannableString(origen.getText().toString());
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        origen.setText(spanString);

        SpannableString spanString1 = new SpannableString(destino.getText().toString());
        spanString1.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString1.length(), 0);
        destino.setText(spanString1);

        return row;
    }
}
