package com.example.bottomnavigationdemo;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.bottomnavigationdemo.entity.UserCharla;
import com.example.bottomnavigationdemo.model.charla.DataRequestModel;
import com.example.bottomnavigationdemo.network.RetrofitHelper;
import com.example.bottomnavigationdemo.service.ServiceCharla;
import com.example.bottomnavigationdemo.util.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Charla extends Fragment {
    private EditText editTextDateTime, editTextMotive;
    private Calendar calendar;
    private Spinner spnProfesionales;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> profesionales = new ArrayList<>();
    private ServiceCharla serviceCharla;
    private SharedPreferences sharedPreferences;
    private String id_professional = "_id";
    private List<UserCharla> lstProfesionales;
    private String token = "";
    private String id = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charla, container, false);

        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editTextDateTime = view.findViewById(R.id.editTextDateTime);
        editTextMotive = view.findViewById(R.id.editTextMotive);
        calendar = Calendar.getInstance();
        editTextDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });
        spnProfesionales = view.findViewById(R.id.spnProfesionales);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, profesionales);
        spnProfesionales.setAdapter(adapter);

        serviceCharla = Connection.getConnecion().create(ServiceCharla.class);
        loadData();

        id = sharedPreferences.getString("id", "");
        token = sharedPreferences.getString("token", "");
        Button sendButton = view.findViewById(R.id.buttonSubmit);
        DataRequestModel dataRequestModel = new DataRequestModel();
        dataRequestModel.setId_aprendiz(id);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el id del profesional almacenado en SharedPreferences
                String id = sharedPreferences.getString("idProfesional", "");
                // Verificar si el usuario es un profesional
                if (!TextUtils.isEmpty(id) && id.equals(id)) {
                    Toast.makeText(getContext(), "Solo los aprendices pueden solicitar charlas", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obtener los valores de los campos de texto
                String dateTime = editTextDateTime.getText().toString().trim();
                String motive = editTextMotive.getText().toString().trim();

                // Verificar si alguno de los campos está vacío
                if (TextUtils.isEmpty(dateTime) || TextUtils.isEmpty(motive)) {
                    Toast.makeText(getContext(), "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }


                id_professional = lstProfesionales.get(spnProfesionales.getSelectedItemPosition()).get_id();
                String fechaSolicitada = editTextDateTime.getText().toString();
                dataRequestModel.setFechaSolicitada(fechaSolicitada);
                dataRequestModel.setMotivo(editTextMotive.getText().toString());

                dataRequestModel.setId_profesional(id_professional);
                executeServiceNewRequest(dataRequestModel);
            }
        });
        return view;
    }

    private void executeServiceNewRequest(DataRequestModel dataRequestModel) {
        try {
            Retrofit retrofit = RetrofitHelper.getInstance();
            ServiceCharla apiService = retrofit.create(ServiceCharla.class);
            Call<String> call = apiService.createNewRequest(dataRequestModel, token);
            Log.e(TAG, "executeServiceNewRequest: " + dataRequestModel);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e(TAG, "onResponse: " + response.body());
                    Toast.makeText(getContext(), "Solicitud enviada exitosamente", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            Log.e(TAG, "executeServiceNewRequest: " + ex.getMessage());
            Toast.makeText(getContext(), "Error al enviar la solicitud", Toast.LENGTH_SHORT).show();
        }
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

                        // Validar si la fecha seleccionada es anterior a la fecha actual
                        if (selectedDate.before(currentDate)) {
                            Toast.makeText(getActivity(), "La fecha seleccionada no puede ser anterior a la fecha actual", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        selectedDate.set(Calendar.MINUTE, minute);

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.getDefault());
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

    public void loadData() {
        Call<List<UserCharla>> call = serviceCharla.listaProfesionales();
        call.enqueue(new Callback<List<UserCharla>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserCharla>> call, @NonNull Response<List<UserCharla>> response) {
                if (!response.isSuccessful())
                    return;
                lstProfesionales = response.body();
                for (UserCharla userCharla : lstProfesionales) {
                    String nombreCompleto = userCharla.getNombres() + " " + userCharla.getApellidos() + " - " + userCharla.getProfesion();
                    profesionales.add(nombreCompleto);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<UserCharla>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de acceso al servicio REST", Toast.LENGTH_SHORT).show();
            }
        });
    }
}