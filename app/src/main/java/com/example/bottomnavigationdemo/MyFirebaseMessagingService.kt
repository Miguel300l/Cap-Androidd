package com.example.bottomnavigationdemo


import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

// Definición de constantes para el ID y el nombre del canal de notificación
const val CHANNEL_ID = "channel_id"
const val CHANNEL_NAME = "com.example.bottomnavigationdemo"

// Clase que extiende de FirebaseMessagingService para recibir mensajes de Firebase Cloud Messaging
class MyFirebaseMessagingService : FirebaseMessagingService() {

//    override fun onCreate() {
//        super.onCreate()
//
//        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w("TOKEN_TAG", "Error al obtener el token.", task.exception)
//                return@addOnCompleteListener
//            }
//
//            // El token se obtuvo correctamente.
//            val token = task.result
//
//            // Utiliza el token para enviar notificaciones personalizadas al usuario.
//            Log.d("TOKEN_TAG", "Token: $token")
//        }
//    }
//    override fun onNewToken(token: String) {
//        Log.d("TOKEN_TAG", "New Token: $token")
//
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // FCM registration token to your app server.
//
//    }


    // Método llamado cuando se recibe un mensaje de notificación
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Verificar si el mensaje contiene una notificación
        if(remoteMessage.notification != null){
            // Generar la notificación utilizando el título y el cuerpo del mensaje recibido
            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TOKEN_TAG", "Error al obtener el token.", task.exception)
                return@addOnCompleteListener
            }

            // El token se obtuvo correctamente.
            val token = task.result

            // Utiliza el token para enviar notificaciones personalizadas al usuario.
            Log.d("TOKEN_TAG", "Token: $token")
        }
    }

    // Método para obtener una vista remota personalizada para la notificación
    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, message: String): RemoteViews {
        // Crear una instancia de RemoteViews con el layout personalizado de la notificación
        val remoteView = RemoteViews("com.example.bottomnavigationdemo", R.layout.fragment_notificaciones)

        // Establecer el texto del título y el mensaje en la vista remota
        remoteView.setTextViewText(R.id.title1, title)
        remoteView.setTextViewText(R.id.message1, message)
        remoteView.setImageViewResource(R.id.app_logo1, R.drawable.logocap)

        return remoteView
    }


    // Método para generar y mostrar la notificación
    fun generateNotification(title: String, message: String) {
        // Crear un intent para abrir la MainActivity cuando se haga clic en la notificación
        val intent = Intent(this, Notificaciones::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // Crear un PendingIntent con el intent anterior
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        // Construir el objeto NotificationCompat.Builder para crear la notificación
        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.logocap)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

        // Establecer la vista remota personalizada en la notificación
        builder = builder.setContent(getRemoteView(title, message))

        // Obtener una instancia de NotificationManager para mostrar la notificación
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear el canal de notificación si el dispositivo está en una versión Android Oreo (API 26) o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // Mostrar la notificación utilizando el ID de notificación 0
        notificationManager.notify(0, builder.build())
    }




}