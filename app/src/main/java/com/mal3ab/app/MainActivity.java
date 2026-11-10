

package com.mal3ab.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {

    private final int BG = Color.rgb(15, 20, 25);
    private final int CARD = Color.rgb(27, 35, 42);
    private final int GREEN = Color.rgb(0, 210, 125);
    private final int WHITE = Color.WHITE;
    private final int GRAY = Color.rgb(165, 175, 185);

    private LinearLayout content;
    private TextView status;
    private Button refresh;

    private final String API_URL =
            "https://mal3ab-api.jbr997705.workers.dev/matches";

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    private final Handler handler =
            new Handler(Looper.getMainLooper());

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
        header.addView(logo, new LinearLayout.LayoutParams(0, -2, 1));

        root.addView(header);

        TextView subtitle = text(
                "عالم كرة القدم بين يديك", 14, GRAY, false);
        subtitle.setPadding(dp(18), 0, dp(18), dp(15));
        root.addView(subtitle);

        status = text("● جاري الاتصال بخادم المباريات...",
                13, GREEN, false);
        status.setPadding(dp(18), dp(10), dp(18), dp(10));
        root.addView(status);

        ScrollView scroll = new ScrollView(this);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(14), dp(10), dp(14), dp(20));

        addTitle("مباريات اليوم");
        addMessage("جاري تحميل المباريات...");

        addTitle("البطولات");
        addLeague("🏴", "الدوري الإنجليزي الممتاز");
        addLeague("🇪🇸", "الدوري الإسباني");
        addLeague("🇩🇪", "الدوري الألماني");
        addLeague("🏆", "دوري أبطال أوروبا");
        addLeague("🇮🇹", "الدوري الإيطالي");
        addLeague("🇫🇷", "الدوري الفرنسي");

        refresh = new Button(this);
        refresh.setText("تحديث المباريات");
        refresh.setTextColor(Color.BLACK);
        refresh.setAllCaps(false);
        refresh.setTextSize(15);
        refresh.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(GREEN));

        content.addView(refresh, params(-1, dp(52), 15));

        refresh.setOnClickListener(v -> loadMatches());

        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        TextView footer = text(
                "الرئيسية       المباريات       البطولات       المفضلة",
                13, GREEN, true);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(dp(5), dp(17), dp(5), dp(17));
        footer.setBackgroundColor(CARD);
        root.addView(footer);

        setContentView(root);

        loadMatches();
    }

    private void loadMatches() {
        status.setText("● جاري تحميل المباريات...");
        refresh.setEnabled(false);
        refresh.setText("جاري التحديث...");

        executor.execute(() -> {
            HttpURLConnection connection = null;

            try {
                URL url = new URL(API_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);

                int code = connection.getResponseCode();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                code >= 200 && code < 300
                                ? connection.getInputStream()
                                : connection.getErrorStream()
                        )
                );

                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();

                if (code < 200 || code >= 300) {
                    throw new Exception("HTTP " + code);
                }

                JSONObject data = new JSONObject(result.toString());

                if (data.has("error")) {
                    throw new Exception(data.optString("message",
                            data.optString("error")));
                }

                JSONArray matches = data.optJSONArray("matches");

                handler.post(() -> showMatches(matches));

            } catch (Exception e) {
                String error = e.getMessage();

                handler.post(() -> {
                    status.setText("● تعذر تحميل المباريات");
                    refresh.setEnabled(true);
                    refresh.setText("إعادة المحاولة");

                    Toast.makeText(
                            this,
                            "خطأ: " + error,
                            Toast.LENGTH_LONG
                    ).show();
                });

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }

    private void showMatches(JSONArray matches) {
        content.removeAllViews();

        addTitle("مباريات اليوم");

        if (matches == null || matches.length() == 0) {
            addMessage("لا توجد مباريات متاحة حالياً.");
        } else {
            for (int i = 0; i < matches.length(); i++) {
                JSONObject match = matches.optJSONObject(i);

                if (match == null) continue;

                JSONObject competition =
                        match.optJSONObject("competition");

                JSONObject home =
                        match.optJSONObject("homeTeam");

                JSONObject away =
                        match.optJSONObject("awayTeam");

                JSONObject score =
                        match.optJSONObject("score");

                JSONObject fullTime =
                        score != null
                        ? score.optJSONObject("fullTime")
                        : null;

                String league = competition != null
                        ? competition.optString("name", "البطولة")
                        : "البطولة";

                String homeName = home != null
                        ? home.optString("name", "الفريق الأول")
                        : "الفريق الأول";

                String awayName = away != null
                        ? away.optString("name", "الفريق الثاني")
                        : "الفريق الثاني";

                String matchStatus =
                        match.optString("status", "SCHEDULED");

                String time = getStatusText(matchStatus);

                String homeScore = fullTime != null
                        && !fullTime.isNull("home")
                        ? fullTime.optString("home")
                        : "—";

                String awayScore = fullTime != null
                        && !fullTime.isNull("away")
                        ? fullTime.optString("away")
                        : "—";

                addMatch(league, homeName, awayName,
                        homeScore + " : " + awayScore, time);
            }
        }

        addTitle("البطولات");
        addLeague("🏴", "الدوري الإنجليزي الممتاز");
        addLeague("🇪🇸", "الدوري الإسباني");
        addLeague("🇩🇪", "الدوري الألماني");
        addLeague("🏆", "دوري أبطال أوروبا");
        addLeague("🇮🇹", "الدوري الإيطالي");
        addLeague("🇫🇷", "الدوري الفرنسي");

        content.addView(refresh, params(-1, dp(52), 15));

        status.setText("● تم الاتصال بخادم ملعب");
        refresh.setEnabled(true);
        refresh.setText("تحديث المباريات");
    }

    private String getStatusText(String value) {
        switch (value) {
            case "IN_PLAY":
            case "PAUSED":
                return "مباشرة";

            case "FINISHED":
                return "انتهت";

            case "POSTPONED":
                return "مؤجلة";

            case "CANCELLED":
                return "ملغاة";

            case "SCHEDULED":
            case "TIMED":
                return "لم تبدأ";

            default:
                return value;
        }
    }

    private void addTitle(String title) {
        TextView t = text(title, 19, WHITE, true);
        t.setPadding(dp(5), dp(20), dp(5), dp(12));
        content.addView(t);
    }

    private void addMatch(String league, String home,
                          String away, String score, String time) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(15), dp(12), dp(15), dp(12));
        card.setBackground(round(CARD, 14));

        card.addView(text(league, 12, GRAY, false));

        LinearLayout teams = new LinearLayout(this);
        teams.setGravity(Gravity.CENTER_VERTICAL);
        teams.setPadding(0, dp(14), 0, dp(10));

        TextView homeText = text(home, 13, WHITE, true);
        teams.addView(homeText,
                new LinearLayout.LayoutParams(0, -2, 1));

        TextView scoreText = text(score, 17, GREEN, true);
        scoreText.setGravity(Gravity.CENTER);
        teams.addView(scoreText);

        TextView awayText = text(away, 13, WHITE, true);
        awayText.setGravity(Gravity.END);
        teams.addView(awayText,
                new LinearLayout.LayoutParams(0, -2, 1));

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}
