package com.example.bottomnavigationdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigationdemo.model.Programa;
import com.example.bottomnavigationdemo.network.ApiAprendiz;
import com.example.bottomnavigationdemo.network.UserService;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registro_Aprendiz extends AppCompatActivity {

    Spinner spnProfesionales;
    ArrayAdapter<String> adapter;

    private List<String> programaIds = new ArrayList<>();
    private List<String>programas=new ArrayList<>();
    UserService userService;

    EditText signupPassword, signupNombres, signupApellidos,
            signupNumeroDocumento, signupCorreo, signupNumTelefono;

    Spinner signupTipo, signupGenero, signupPrograma;
    TextView loginRedirectText, imageUrlTextView, profileImageView;
    Button signupButton, selectImageButton;

    private static final int PICK_IMAGE_REQUEST = 1;

    LinearLayout linearLayout;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registro_aprendiz);

        linearLayout = (LinearLayout) findViewById(R.id.LayoutRegister);
        // Inicializar Spinner y adaptador
        spnProfesionales = findViewById(R.id.signupPrograma);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, programas);
        spnProfesionales.setAdapter(adapter);

        // Establecer listener para guardar el ID del programa seleccionado en SharedPreferences
        spnProfesionales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Guardar el ID del programa seleccionado en SharedPreferences
                String selectedProgramId = programaIds.get(position);
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", selectedProgramId);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No se seleccionó ningún programa
            }
        });



        spnProfesionales = findViewById(R.id.signupPrograma);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, programas);
        spnProfesionales.setAdapter(adapter);
        userService = ApiAprendiz.getAprendiz().create(UserService.class);
        cargaData();

        signupPassword = findViewById(R.id.signup_password);
        signupNombres = findViewById(R.id.signup_nombres);
        signupApellidos = findViewById(R.id.signup_apellidos);
        signupNumeroDocumento = findViewById(R.id.signup_numero_documento);
        signupCorreo = findViewById(R.id.signup_correo);
        signupNumTelefono = findViewById(R.id.signup_num_telefono);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupTipo = findViewById(R.id.signup_tipo);
        signupGenero = findViewById(R.id.signup_genero);
        signupPrograma = findViewById(R.id.signupPrograma);
        selectImageButton = findViewById(R.id.btn_select_image1);
        imageUrlTextView = findViewById(R.id.txt_selected_image1);


        // Configurar los adaptadores para los spinners
        ArrayAdapter<CharSequence> tipoAdapter = ArrayAdapter.createFromResource(this, R.array.tipos_array, android.R.layout.simple_spinner_item);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupTipo.setAdapter(tipoAdapter);

        ArrayAdapter<CharSequence> generoAdapter = ArrayAdapter.createFromResource(this, R.array.generos_array, android.R.layout.simple_spinner_item);
        generoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupGenero.setAdapter(generoAdapter);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los datos ingresados por el usuario
                String contrasena = signupPassword.getText().toString();
                String nombres = signupNombres.getText().toString();
                String apellidos = signupApellidos.getText().toString();
                String tipo = signupTipo.getSelectedItem().toString();
                String numeroDocumento = signupNumeroDocumento.getText().toString();
                String genero = signupGenero.getSelectedItem().toString();
                String correo = signupCorreo.getText().toString();
                String numTelefono = signupNumTelefono.getText().toString();

                // Obtener el ID del programa seleccionado de SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String selectedProgramId = sharedPreferences.getString("id", "");

                // Verificar si se seleccionó un programa
                if (selectedProgramId.isEmpty()) {
                    Toast.makeText(Registro_Aprendiz.this, "No se seleccionó ningún programa", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar si el correo ingresado es válido
                if (!isValidEmail(correo)) {
                    Snackbar snackbar = Snackbar.make(linearLayout, "Correo invalido", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    return;
                }

                // Verificar la longitud mínima de la contraseña y su formato
                if (contrasena.length() < 8 || !isValidPassword(contrasena)) {
                    Snackbar snackbar = Snackbar.make(linearLayout, "Contraseña Insegura", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    return;
                }

                // Verificar el formato del número de teléfono
                if (!isValidPhoneNumber(numTelefono)) {
                    Snackbar snackbar = Snackbar.make(linearLayout, "Celular incorrecto", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    return;
                }

                // Crea el cuerpo de la solicitud POST como formulario multipart
                MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("contrasena", contrasena)
                        .addFormDataPart("nombres", nombres)
                        .addFormDataPart("apellidos", apellidos)
                        .addFormDataPart("tipo", tipo)
                        .addFormDataPart("numeroDocumento", numeroDocumento)
                        .addFormDataPart("genero", genero)
                        .addFormDataPart("correo", correo)
                        .addFormDataPart("numTelefono", numTelefono)
                        .addFormDataPart("programa", selectedProgramId);

                if (selectedImageUri != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        byte[] imageBytes = getBytes(inputStream);
                        requestBodyBuilder.addFormDataPart("imgAprendiz", "imgAprendiz", RequestBody.create(MediaType.parse("image/*"), imageBytes));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                RequestBody requestBody = requestBodyBuilder.build();

                // Crea la solicitud POST a la URL de Vercel
                Request request = new Request.Builder()
                        .url("https://backend-cap-273v.vercel.app/registrarAprendiz")
                        .post(requestBody)
                        .build();

                // Crea el cliente HTTP
                OkHttpClient client = new OkHttpClient();

                // Envía la solicitud asíncronamente
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Manejo del error en caso de fallo de la solicitud
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Registro_Aprendiz.this, "Error al enviar la solicitud", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // Manejo de la respuesta de la solicitud
                        final String responseBody = response.body().string();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (response.isSuccessful()) {
                                    Snackbar snackbar = Snackbar.make(linearLayout, "Te has registrado correctamente", Snackbar.LENGTH_SHORT);
                                    snackbar.show();
                                    Intent intent = new Intent(Registro_Aprendiz.this, Login_Aprendiz.class);
                                    startActivity(intent);
                                } else {
                                    Snackbar snackbar = Snackbar.make(linearLayout, "Error al registrar: "  + responseBody, Snackbar.LENGTH_SHORT);
                                    snackbar.show();
                                }
                            }
                        });
                    }
                });
            }
        });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro_Aprendiz.this, Login_Aprendiz.class);
                startActivity(intent);
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imageUrlTextView.setText(selectedImageUri.toString());
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public void cargaData() {
        retrofit2.Call<List<Programa>> call = userService.listaProfesionales();
        call.enqueue(new retrofit2.Callback<List<Programa>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Programa>> call, retrofit2.Response<List<Programa>> response) {
                if (response.isSuccessful()) {
                    List<Programa> lstProfesionales = response.body();
                    for (Programa programa : lstProfesionales) {
                        String nombreCompleto = programa.getNombre() + " " + programa.getFicha();
                        programaIds.add(programa.get_id());
                        programas.add(nombreCompleto);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    mensajeToast("Error de acceso al servicio REST");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Programa>> call, Throwable t) {
                mensajeToast("Error de acceso al servicio REST");
            }
        });
    }
    public void mensajeAlert(String titulo, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setTitle(titulo);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    void mensajeToast(String mensaje){
        Toast toast1 = Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    // Función para validar el número de teléfono con expresión regular
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Verificar si el número de teléfono comienza con "3" y tiene exactamente 10 dígitos
        return phoneNumber.matches("^3\\d{9}$");
    }
    // Verificar si el correo es válido utilizando una expresión regular
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    // Verificar si la contraseña cumple con los requisitos mínimos utilizando una expresión regular
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@.#$%¿)><(*´}{°|~`^&+=!?¡]).*$";
        return password.matches(passwordRegex);
    }
}