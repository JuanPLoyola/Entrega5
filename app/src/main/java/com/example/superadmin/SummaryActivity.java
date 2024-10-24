package com.example.superadmin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.example.superadmin.adapters.AdapterSummary;
import com.example.superadmin.adapters.ProductAdapter;
import com.example.superadmin.adapters.ProductCarAdapter;
import com.example.superadmin.model.Product;
import com.example.superadmin.model.ProductInCar;

import java.util.Arrays;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "order_notification_channel";
    MaterialButton btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_summary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Crear el canal de notificación (solo es necesario en API 26+)
        createNotificationChannel();

        btnFinish = findViewById(R.id.btn_return_to_home);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lanzar la notificación
                showOrderCompletedNotification();

                // Hacer vibrar el dispositivo
                vibrateDevice();

                // Cambiar a la pantalla de inicio
                startActivity(new Intent(SummaryActivity.this, HomeActivity.class));

                // Terminar la actividad actual
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_summary_order_client);
        List<ProductInCar> products = Arrays.asList(
                new ProductInCar("Alitas BBQ", 2, 45.0, R.drawable.comida),
                new ProductInCar("Ceviche Mixto", 3, 650.0, R.drawable.logo),
                new ProductInCar("Chicharrón de Calamar", 1, 350.0, R.drawable.logo),
                new ProductInCar("Arroz con Mariscos", 2, 800.0, R.drawable.logo),
                new ProductInCar("Ají de Gallina", 4, 550.0, R.drawable.logo),
                new ProductInCar("Causa Limeña", 1, 450.0, R.drawable.logo),
                new ProductInCar("Tacos de Pollo Crocante", 1, 650.0, R.drawable.logo),
                new ProductInCar("Leche de Tigre", 3, 350.0, R.drawable.logo),
                new ProductInCar("Mazamorra de Calabaza", 6, 800.0, R.drawable.logo),
                new ProductInCar("Tallarines Rojos", 1, 550.0, R.drawable.logo)
        );
        AdapterSummary adapter = new AdapterSummary(SummaryActivity.this, products);
        recyclerView.setAdapter(adapter);
    }

    // Crear un canal de notificación para Android Oreo y superiores
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Order Completed";
            String description = "Notificación cuando un pedido es completado.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Registrar el canal con el sistema
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Método para mostrar la notificación de pedido completado
    private void showOrderCompletedNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo) // Icono de la notificación
                .setContentTitle("Pedido completado")
                .setContentText("Tu pedido ha sido completado con éxito.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // Mostrar la notificación
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1001, builder.build());
    }

    // Método para hacer vibrar el dispositivo
    private void vibrateDevice() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            long[] vibrationPattern = {0, 500, 100, 500}; // Patrón de vibración
            vibrator.vibrate(vibrationPattern, -1); // '-1' significa que no se repite
        }
    }
}