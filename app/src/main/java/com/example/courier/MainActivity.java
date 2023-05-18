package com.example.courier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mainLV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Courier courier = new Courier("Курьеров Курьер Курьерович", "Водитель");


        Order[] orders = {
                new Order(new Company("KFC", "ул. Центральная, д. 3"),
                        new SmallPackage(false, ""), "ул. Центральная, д. 3",
                        "Красная пл., д. 1", 400),
                new Order(new Company("М Видео", "ул. Пушкина, д. 52"),
                        new BigPackage(true, 30), "ул. Пушкина, д. 52",
                        "Измайловский пр-т, д. 73/2", 1200),
                new Order(new Company("МГТУ им. Н.Э. Баумана",
                        "ул. Бауманская 2-я, д. 5"), new Document(false,
                        "Не допускать попадания жидкости",
                        "МГТУ им. Н.Э. Баумана - Басманный районный суд"),
                        "ул. Пушкина, д. 52",
                        "Измайловский пр-т, д. 73/2", 700)
        };

        ArrayList<Boolean> areClicked = new ArrayList<>();
        for (int i = 0; i < orders.length; ++i) {
            areClicked.add(false);
        }


        setContentView(R.layout.activity_main);

        TextView mainTV = (TextView) findViewById(R.id.mainTV);
        mainTV.setText(courier.getFullName().concat("\n").concat(courier.getCurrentAccount()));


        mainLV = (ListView) findViewById(R.id.mainLV);
        OrderAdapter orderAdapter = new OrderAdapter(this, orders);
        mainLV.setAdapter(orderAdapter);

        mainLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int color = ContextCompat.getColor(getApplicationContext(), R.color.onClicked);
                int backColor = ContextCompat.getColor(getApplicationContext(), R.color.backgroundDown);
                if (areClicked.get(position)) {
                    courier.getOrders().remove((Order) parent.getAdapter().getItem(position));
                    view.setBackgroundColor(backColor);
                    areClicked.set(position, false);
                } else {
                    courier.getOrders().add((Order) parent.getAdapter().getItem(position));
                    view.setBackgroundColor(color);
                    areClicked.set(position, true);
                }


            }
        });
        Button clean_button = findViewById(R.id.clean_button);
        clean_button.setOnClickListener(v -> {
            for (int i = 0; i < mainLV.getChildCount(); ++i) {
                View listItem = mainLV.getChildAt(i);
                listItem.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.backgroundDown));
            }
            courier.getOrders().clear();
        });

        Button ok_button = findViewById(R.id.ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = 0;
                for (Order order : courier.getOrders()) {
                    sum += order.getPrice();
                }
                if (sum > 0) {
                    Toast.makeText(MainActivity.this, String.valueOf(sum), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "No orders", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}