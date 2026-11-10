
package com.mal3ab.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final int BG = Color.rgb(15, 20, 25);
    private final int CARD = Color.rgb(27, 35, 42);
    private final int GREEN = Color.rgb(0, 210, 125);
    private final int WHITE = Color.WHITE;
    private final int GRAY = Color.rgb(165, 175, 185);

    private LinearLayout content;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);
        root.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(dp(18), dp(15), dp(18), dp(12));

        TextView logo = text("MAL3AB ⚽", 25, GREEN, true);
        header.addView(logo, new LinearLayout.LayoutParams(
                0, -2, 1));

        TextView notification = text("🔔", 22, WHITE, false);
        header.addView(notification);

        root.addView(header);

        TextView subtitle = text("عالم كرة القدم بين يديك",
                14, GRAY, false);
        subtitle.setPadding(dp(18), 0, dp(18), dp(15));
        root.addView(subtitle);

        status = text("● بانتظار ربط بيانات المباريات",
                13, GREEN, false);
        status.setPadding(dp(18), dp(10), dp(18), dp(10));
        root.addView(status);

        LinearLayout tabs = new LinearLayout(this);
        tabs.setPadding(dp(10), dp(8), dp(10), dp(8));
        tabs.setGravity(Gravity.CENTER);

        String[] tabNames = {
                "اليوم", "المباريات", "البطولات", "المفضلة"
        };

        for (String name : tabNames) {
            Button tab = new Button(this);
            tab.setText(name);
            tab.setTextColor(WHITE);
            tab.setTextSize(12);
            tab.setAllCaps(false);
            tab.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(CARD));
            tabs.addView(tab, new LinearLayout.LayoutParams(
                    0, dp(45), 1));

            tab.setOnClickListener(v ->
                    Toast.makeText(this,
                            name + " - سيتم تفعيل القسم لاحقاً",
                            Toast.LENGTH_SHORT).show());
        }

        root.addView(tabs);

        ScrollView scroll = new ScrollView(this);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(14), dp(10), dp(14), dp(20));

        addTitle("مباريات اليوم");
        addMatch("الدوري الإنجليزي",
                "مانشستر سيتي", "ليفربول", "لم تبدأ");

        addMatch("الدوري الإسباني",
                "ريال مدريد", "برشلونة", "لم تبدأ");

        addMatch("دوري أبطال أوروبا",
                "الفريق الأول", "الفريق الثاني", "لم تبدأ");

        addTitle("البطولات");

        addLeague("🏴", "الدوري الإنجليزي الممتاز");
        addLeague("🇪🇸", "الدوري الإسباني");
        addLeague("🇩🇪", "الدوري الألماني");
        addLeague("🏆", "دوري أبطال أوروبا");
        addLeague("🇮🇹", "الدوري الإيطالي");
        addLeague("🇫🇷", "الدوري الفرنسي");

        addTitle("النتائج");
        addMessage("ستظهر النتائج الحقيقية بعد ربط خادم البيانات.");

        addTitle("المفضلة");
        addMessage("أضف فرقك المفضلة لمتابعة أخبارها ومبارياتها.");

        Button refresh = new Button(this);
        refresh.setText("تحديث المباريات");
        refresh.setTextColor(Color.BLACK);
        refresh.setAllCaps(false);
        refresh.setTextSize(15);
        refresh.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(GREEN));

        content.addView(refresh, params(-1, dp(52), 15));

        refresh.setOnClickListener(v ->
                Toast.makeText(this,
                        "خادم بيانات المباريات غير مربوط بعد",
                        Toast.LENGTH_LONG).show());

        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(
                -1, 0, 1));

        TextView footer = text(
                "الرئيسية       المباريات       البطولات       المفضلة",
                13, GREEN, true);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(dp(5), dp(17), dp(5), dp(17));
        footer.setBackgroundColor(CARD);
        root.addView(footer);

        setContentView(root);
    }

    private void addTitle(String title) {
        TextView t = text(title, 19, WHITE, true);
        t.setPadding(dp(5), dp(20), dp(5), dp(12));
        content.addView(t);
    }

    private void addMatch(String league, String home,
                          String away, String time) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(15), dp(12), dp(15), dp(12));
        card.setBackground(round(CARD, 14));

        TextView leagueText = text(league, 12, GRAY, false);
        card.addView(leagueText);

        LinearLayout teams = new LinearLayout(this);
        teams.setGravity(Gravity.CENTER_VERTICAL);
        teams.setPadding(0, dp(14), 0, dp(10));

        TextView homeText = text("⚽ " + home, 14, WHITE, true);
        teams.addView(homeText, new LinearLayout.LayoutParams(
                0, -2, 1));

        TextView score = text("—  :  —", 16, GREEN, true);
        score.setGravity(Gravity.CENTER);
        teams.addView(score);

        TextView awayText = text("⚽ " + away, 14, WHITE, true);
        awayText.setGravity(Gravity.END);
        teams.addView(awayText, new LinearLayout.LayoutParams(
                0, -2, 1));

        card.addView(teams);

        TextView timeText = text(time, 12, GREEN, false);
        timeText.setGravity(Gravity.CENTER);
        card.addView(timeText);

        content.addView(card, params(-1, -2, 7));
    }

    private void addLeague(String icon, String name) {
        TextView league = text(icon + "     " + name,
                15, WHITE, true);
        league.setPadding(dp(15), dp(16), dp(15), dp(16));
        league.setBackground(round(CARD, 12));
        content.addView(league, params(-1, -2, 5));
    }

    private void addMessage(String message) {
        TextView t = text(message, 13, GRAY, false);
        t.setPadding(dp(15), dp(18), dp(15), dp(18));
        t.setBackground(round(CARD, 12));
        content.addView(t, params(-1, -2, 5));
    }

    private TextView text(String value, int size,
                          int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);
        if (bold) {
            t.setTypeface(null, Typeface.BOLD);
        }
        return t;
    }

    private GradientDrawable round(int color, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(dp(radius));
        return shape;
    }

    private LinearLayout.LayoutParams params(
            int width, int height, int bottom) {
        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(width, height);
        p.bottomMargin = dp(bottom);
        return p;
    }

    private int dp(int value) {
        return (int) (value *
                getResources().getDisplayMetrics().density);
    }
}
