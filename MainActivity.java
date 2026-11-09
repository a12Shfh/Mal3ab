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
        title.setText("ملعب  |  MAL3AB");
        title.setTextColor(green);
        title.setTextSize(30);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 12, 0, 24);
        page.addView(title, new LinearLayout.LayoutParams(-1, -2));

        TextView subtitle = new TextView(this);
        subtitle.setText("تطبيقك الرياضي");
        subtitle.setTextColor(Color.WHITE);
        subtitle.setTextSize(20);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 8, 0, 28);
        page.addView(subtitle, new LinearLayout.LayoutParams(-1, -2));

        addSection(page, "المباريات القادمة", "ستظهر المباريات هنا عند إضافة مصدر بيانات مباشر.");
        addSection(page, "النتائج", "تابع نتائج فريقك المفضل من داخل ملعب.");
        addSection(page, "المفضلة", "ميزة المفضلة قيد التطوير.");

        TextView note = new TextView(this);
        note.setText("نسخة تجريبية أولية — لا تعرض نتائج مباشرة حالياً");
        note.setTextColor(Color.LTGRAY);
        note.setTextSize(13);
        note.setGravity(Gravity.CENTER);
        note.setPadding(0, 30, 0, 0);
        page.addView(note, new LinearLayout.LayoutParams(-1, -2));

        setContentView(page);
    }

    private void addSection(LinearLayout parent, String heading, String detail) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(18, 16, 18, 16);
        card.setBackgroundColor(Color.rgb(28, 38, 33));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 10, 0, 10);

        TextView h = new TextView(this);
        h.setText(heading);
        h.setTextColor(green);
        h.setTextSize(19);
        card.addView(h);

        TextView d = new TextView(this);
        d.setText(detail);
        d.setTextColor(Color.WHITE);
        d.setTextSize(14);
        d.setPadding(0, 8, 0, 0);
        card.addView(d);

        parent.addView(card, params);
    }
}
