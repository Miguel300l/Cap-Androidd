package com.example.bottomnavigationdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigationdemo.model.Programa;
import com.example.bottomnavigationdemo.network.ApiAprendiz;
import com.example.bottomnavigationdemo.network.UserService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
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

public class RegistroProfesional extends AppCompatActivity {

    EditText signupPassword, signupNombres, signupApellidos,
            signupNumeroDocumento, signupCorreo, signupNumTelefono;

    Spinner signupTipo, signupGenero, signupProfesion;
    TextView loginRedirectText, imageUrlTextView;
    Button signupButton, buttonSeleccionarImagen;

    String rol = "profesional";
    private Uri selectedImageUri;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_profesional);

        signupPassword = findViewById(R.id.signup_passwordPro);
        signupNombres = findViewById(R.id.signup_nombresPro);
        signupApellidos = findViewById(R.id.signup_apellidosPro);
        signupNumeroDocumento = findViewById(R.id.signup_numero_documento);
        signupCorreo = findViewById(R.id.signup_correo);
        signupNumTelefono = findViewById(R.id.signup_num_telefono);
        signupButton = findViewById(R.id.buttonregistrarPro);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupTipo = findViewById(R.id.signup_tipo);
        signupGenero = findViewById(R.id.signup_genero);
        signupProfesion = findViewById(R.id.signupProfesion);
        buttonSeleccionarImagen = findViewById(R.id.btn_select_image);
        imageUrlTextView = findViewById(R.id.txt_selected_image);

        // Configurar los adaptadores para los spinners
        ArrayAdapter<CharSequence> tipoAdapter = ArrayAdapter.createFromResource(this, R.array.tipos_array, android.R.layout.simple_spinner_item);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupTipo.setAdapter(tipoAdapter);

        ArrayAdapter<CharSequence> generoAdapter = ArrayAdapter.createFromResource(this, R.array.generos_array, android.R.layout.simple_spinner_item);
        generoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupGenero.setAdapter(generoAdapter);

        ArrayAdapter<CharSequence> profesionAdapter = ArrayAdapter.createFromResource(this, R.array.profesion_array, android.R.layout.simple_spinner_item);
        profesionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupProfesion.setAdapter(profesionAdapter);

        buttonSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValidEmail(signupCorreo.getText().toString())) {
                    Toast.makeText(RegistroProfesional.this, "Correo inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (signupPassword.length() < 8 || !isValidPassword(signupPassword.getText().toString())) {
                    Toast.makeText(RegistroProfesional.this, "La contraseña debe tener al menos 8 caracteres y contener una combinación de letras, números y caracteres especiales", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar el formato del número de teléfono
                if (!isValidPhoneNumber(signupNumTelefono.getText().toString())) {
                    Toast.makeText(RegistroProfesional.this, "El número de teléfono debe comenzar con 3 y tener 10 dígitos", Toast.LENGTH_SHORT).show();
                    return;
                }

                String contrasena = signupPassword.getText().toString();
                String nombres = signupNombres.getText().toString();
                String apellidos = signupApellidos.getText().toString();
                String tipo = signupTipo.getSelectedItem().toString();
                String numeroDocumento = signupNumeroDocumento.getText().toString();
                String genero = signupGenero.getSelectedItem().toString();
                String correo = signupCorreo.getText().toString();
                String profesion = signupProfesion.getSelectedItem().toString();
                String numTelefono = signupNumTelefono.getText().toString();


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
                        .addFormDataPart("rol", rol)
                        .addFormDataPart("profesion", profesion);

                if (selectedImageUri != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        byte[] fileBytes = getBytes(inputStream);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), fileBytes);
                        requestBodyBuilder.addFormDataPart("imgProfesional", "imgProfesional", requestBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegistroProfesional.this, "Error al leer el archivo de imagen", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                }

                RequestBody requestBody = requestBodyBuilder.build();

                // Crea la solicitud POST a la URL de Vercel
                Request request = new Request.Builder()
                        .url("https://backend-cap-273v.vercel.app/registrarProfesional")
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
                                Toast.makeText(RegistroProfesional.this, "Error al enviar la solicitud", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(RegistroProfesional.this, "Te has registrado correctamente", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistroProfesional.this, LoginProfesional.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RegistroProfesional.this, "Error al registrar: " + responseBody, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroProfesional.this, LoginProfesional.class);
                startActivity(intent);
            }
        });
    }

    // Verificar si el correo es válido utilizando una expresión regular
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    // Función para validar el número de teléfono con expresión regular
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Verificar si el número de teléfono comienza con "3" y tiene exactamente 10 dígitos
        return phoneNumber.matches("^3\\d{9}$");
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@.#$%¿)><(*´}{°|~`^&+=!?¡]).*$";
        return password.matches(passwordRegex);
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imageUrlTextView.setText(selectedImageUri.toString());
        }
    }
}