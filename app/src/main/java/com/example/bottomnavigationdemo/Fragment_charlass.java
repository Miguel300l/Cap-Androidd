package com.example.bottomnavigationdemo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.bottomnavigationdemo.entity.UserCharla;
import com.example.bottomnavigationdemo.service.ServiceCharla;
import com.example.bottomnavigationdemo.util.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_charlass extends Fragment {

    private EditText editTextDateTime;
    private Calendar calendar;

    private Spinner spnProfesionales;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> profesionales = new ArrayList<>();

    private ServiceCharla serviceCharla;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charlass, container, false);

        editTextDateTime = view.findViewById(R.id.editTextDateTime);
        calendar = Calendar.getInstance();

        editTextDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        // Agregar el OnClickListener al botón de enviar
        Button sendButton = view.findViewById(R.id.buttonSubmit);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí puedes llamar a tu AsyncTask para realizar la solicitud HTTP
            }
        });

        spnProfesionales = view.findViewById(R.id.spnProfesionales);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, profesionales);
        spnProfesionales.setAdapter(adapter);

        serviceCharla = Connection.getConnecion().create(ServiceCharla.class);
        cargaData();

        return view;
    }

    private void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar selectedDate = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, monthOfYear);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        selectedDate.set(Calendar.MINUTE, minute);

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
                                        String formattedDateTime = dateFormat.format(selectedDate.getTime());
                                        editTextDateTime.setText(formattedDateTime);
                                    }
                                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false);

                        timePickerDialog.show();
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(currentDate.getTimeInMillis());
        datePickerDialog.show();
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {
        private static final String URL_STRING = "https://backend-cap-273v.vercel.app/crearSolicitud";
        private static final String DATE = "2023-05-22";
        private static final String MOTIVE = "Some motive";

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(URL_STRING);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                String aprendiz = params[0];
                String profesional = params[1];

                // Construye el cuerpo de la solicitud en formato JSON
                String requestBody = "{\"fechaSolicitada\":\"" + DATE + "\",\"motivo\":\"" + MOTIVE + "\",\"id_aprendiz\":\"" +
                        aprendiz + "\",\"id_profesional\":\"" + profesional + "\"}";

                // Envía los datos al servidor
                try (OutputStream outputStream = urlConnection.getOutputStream()) {
                    byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }

                // Lee la respuesta del servidor
                int statusCode = urlConnection.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    return response.toString();
                } else {
                    return "Error: " + statusCode;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Aquí puedes manejar la respuesta del servidor
            // result contiene la respuesta del servidor en formato de texto

            if (result.startsWith("Error")) {
                Toast.makeText(getActivity(), "Error en la solicitud: " + result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Solicitud enviada exitosamente", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cargaData() {
        Call<List<UserCharla>> call = serviceCharla.listaProfesionales();
        call.enqueue(new Callback<List<UserCharla>>() {
            @Override
            public void onResponse(Call<List<UserCharla>> call, Response<List<UserCharla>> response) {
                mensajeToast("Acceso exitoso al servicio REST");
                if (response.isSuccessful()) {
                    mensajeToast("Acceso exitoso al servicio REST");
                    List<UserCharla> lstProfesionales = response.body();
                    for (UserCharla userCharla : lstProfesionales) {
                        String nombreCompleto = userCharla.getNombres() + " " + userCharla.getApellidos() + " - " + userCharla.getProfesion();
                        profesionales.add(nombreCompleto);
                    }
                    adapter.notifyDataSetChanged();

                } else {
                    mensajeToast("Error de acceso al servicio REST");
                }
            }

            @Override
            public void onFailure(Call<List<UserCharla>> call, Throwable t) {
                mensajeToast("Error de acceso al servicio REST");
            }
        });
    }

    public void mensajeAlert(String titulo, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setMessage(msg);
        alertDialog.setTitle(titulo);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    void mensajeToast(String mensaje) {
        Toast toast1 = Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }
}
