package com.mal3ab.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ScrollView;

public class MainActivity extends Activity {

    private int green = Color.rgb(25, 190, 110);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(24, 35, 24, 24);
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
        subtitle.setPadding(0, 10, 0, 20);
        page.addView(subtitle);

        addSection(page, "المباريات القادمة",
            "تابع مواعيد المباريات القادمة.");

        addSection(page, "النتائج المباشرة",
            "تابع نتائج المباريات والفرق.");

        addSection(page, "المشاهدة المباشرة",
            "قريباً: مشاهدة المباريات عبر مصادر البث الرسمية داخل ملعب.");

        addSection(page, "البطولات",
            "البطولات العالمية والعربية والعراقية.");

        addSection(page, "المفضلة",
            "اختر فرقك المفضلة وتابع أخبارها.");

        scroll.addView(page);
        setContentView(scroll);
    }

    private void addSection(
        LinearLayout parent,
        String heading,
        String detail
    ) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(18, 18, 18, 18);
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
        d.setPadding(0, 10, 0, 0);
        card.addView(d);

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);

        params.setMargins(0, 10, 0, 10);
        parent.addView(card, params);
    }
}
