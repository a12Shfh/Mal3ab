

package com.mal3ab.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends Activity {

    int green = Color.rgb(25, 190, 110);
    int dark = Color.rgb(13, 18, 16);
    int cardColor = Color.rgb(28, 38, 33);

    LinearLayout page;
    TextView matchesText;
    TextView resultsText;
    TextView statusText;

    String serverUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHome();
    }

    void showHome() {
        ScrollView scroll = new ScrollView(this);

        page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(20, 25, 20, 25);
        page.setBackgroundColor(dark);

        TextView title = new TextView(this);
        title.setText("⚽ ملعب | MAL3AB");
        title.setTextColor(green);
        title.setTextSize(30);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        page.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("عالم كرة القدم بين يديك");
        subtitle.setTextColor(Color.WHITE);
        subtitle.setTextSize(17);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 10, 0, 25);
        page.addView(subtitle);

        statusText = addSection("حالة الاتصال",
                "بانتظار إعداد خادم بيانات المباريات");

        addSection("🏆 البطولات",
                "الدوري الإنجليزي\nالدوري الإسباني\nالدوري الألماني\nدوري أبطال أوروبا");

        matchesText = addSection("⚽ المباريات القادمة",
                "سيتم عرض المباريات هنا بعد ربط الخادم.");

        resultsText = addSection("📊 النتائج",
                "سيتم عرض النتائج هنا بعد ربط الخادم.");

        addSection("❤️ المفضلة",
                "متابعة الفرق المفضلة قريباً.");

        addButton("🔄 تحديث المباريات", () -> {
            if (serverUrl.isEmpty()) {
                message("ربط خادم البيانات لم يكتمل بعد.");
            } else {
                message("سيتم تفعيل التحديث بعد إعداد الخادم.");
            }
        });

        scroll.addView(page);
        setContentView(scroll);
    }

    TextView addSection(String heading, String detail) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(18, 18, 18, 18);
        card.setBackgroundColor(cardColor);

        TextView h = new TextView(this);
        h.setText(heading);
        h.setTextColor(green);
        h.setTextSize(20);
        h.setTypeface(null, Typeface.BOLD);
        card.addView(h);

        TextView d = new TextView(this);
        d.setText(detail);
        d.setTextColor(Color.WHITE);
        d.setTextSize(15);
        d.setPadding(0, 12, 0, 0);
        card.addView(d);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 10, 0, 10);

        page.addView(card, params);
        return d;
    }

    void addButton(String text, Runnable action) {
        TextView button = new TextView(this);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setTextSize(17);
        button.setGravity(Gravity.CENTER);
        button.setPadding(15, 18, 15, 18);
        button.setBackgroundColor(green);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 12, 0, 12);

        page.addView(button, params);
        button.setOnClickListener(v -> action.run());
    }

    void message(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
