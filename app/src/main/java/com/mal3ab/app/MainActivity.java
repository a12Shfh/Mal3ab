package com.mal3ab.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private int green = Color.rgb(25, 190, 110);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setGravity(Gravity.CENTER_HORIZONTAL);
        page.setPadding(24, 48, 24, 24);
        page.setBackgroundColor(Color.rgb(13, 18, 16));

        TextView title = new TextView(this);
        title.setText("ملعب | MAL3AB");
        title.setTextColor(green);
        title.setTextSize(30);
        title.setGravity(Gravity.CENTER);
        page.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("تطبيقك الرياضي");
        subtitle.setTextColor(Color.WHITE);
        subtitle.setTextSize(20);
        subtitle.setGravity(Gravity.CENTER);
        page.addView(subtitle);

        addSection(page, "المباريات القادمة", "ستظهر المباريات هنا عند إضافة مصدر بيانات مباشر.");
        addSection(page, "النتائج", "تابع نتائج فريقك المفضل من داخل ملعب.");
        addSection(page, "المفضلة", "ميزة المفضلة قيد التطوير.");

        setContentView(page);
    }

    private void addSection(LinearLayout parent, String heading, String detail) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(18, 16, 18, 16);
        card.setBackgroundColor(Color.rgb(28, 38, 33));

        TextView h = new TextView(this);
        h.setText(heading);
        h.setTextColor(green);
        h.setTextSize(19);
        card.addView(h);

        TextView d = new TextView(this);
        d.setText(detail);
        d.setTextColor(Color.WHITE);
        d.setTextSize(14);
        card.addView(d);

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 10, 0, 10);
        parent.addView(card, params);
    }
}
