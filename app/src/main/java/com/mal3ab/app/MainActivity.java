
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
import android.widget.ImageView;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends Activity {

    int green = Color.rgb(25, 190, 110);
    int dark = Color.rgb(13, 18, 16);
    int cardColor = Color.rgb(28, 38, 33);

    String officialUrl = "";

    LinearLayout page;

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
        subtitle.setTextSize(18);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 10, 0, 20);
        page.addView(subtitle);

        section("🏟️ واجهة ملعب",
                "مرحباً بك في تطبيق ملعب الرياضي");

        section("⚽ المباريات القادمة",
                "ستظهر المواعيد عند ربط مصدر بيانات المباريات.");

        section("📊 النتائج المباشرة",
                "تابع نتائج المباريات بعد ربط مصدر النتائج.");

        section("🏆 البطولات",
                "الدوريات العالمية والعربية والعراقية.");

        section("❤️ الفرق المفضلة",
                "قريباً يمكنك متابعة فرقك المفضلة.");

        section("📺 مشاهدة المباريات",
                "شاهد المباريات من خلال المصادر الرسمية.");

        addButton("مشاهدة داخل التطبيق", () -> {
            if (officialUrl.isEmpty()) {
                message("أضف رابط بث رسمي أولاً.");
            } else {
                showVideo(officialUrl);
            }
        });

        addButton("مشاهدة خارج التطبيق", () -> {
            if (officialUrl.isEmpty()) {
                message("أضف رابط بث رسمي أولاً.");
            } else {
                Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(officialUrl)
                );
                startActivity(intent);
            }
        });

        scroll.addView(page);
        setContentView(scroll);
    }

    void section(String heading, String detail) {

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
        d.setPadding(0, 10, 0, 0);
        card.addView(d);

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);

        params.setMargins(0, 10, 0, 10);
        page.addView(card, params);
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

        params.setMargins(0, 8, 0, 8);
        page.addView(button, params);

        button.setOnClickListener(v -> action.run());
    }

    void showVideo(String url) {

        WebView web = new WebView(this);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(url);

        setContentView(web);
    }

    void message(String text) {
        android.widget.Toast.makeText(
            this, text, android.widget.Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public void onBackPressed() {
        showHome();
    }
}

