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

    public RecorridosAdapter(Context context, List<Recorrido> proyectos) {
        super(context, R.layout.fila_recorrido, proyectos);
        inflater = LayoutInflater.from(context);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View row = convertView;

        if (row == null) row = inflater.inflate(R.layout.fila_recorrido, parent, false);

        TextView nombre = (TextView) row.findViewById(R.id.origen_recorrido);

        TextView id = (TextView) row.findViewById(R.id.destino_recorrido);

        nombre.setText(this.getItem(position).getNombre_origen().toString());

        id.setText(this.getItem(position).getNombre_destino().toString());

        Integer color = Color.parseColor("#2196F3");

        nombre.setTextColor(color);

        id.setTextColor(color);

        SpannableString spanString = new SpannableString(nombre.getText().toString());

        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);

        nombre.setText(spanString);

        SpannableString spanString1 = new SpannableString(id.getText().toString());

        spanString1.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString1.length(), 0);

        id.setText(spanString1);

        return row;
    }
}
