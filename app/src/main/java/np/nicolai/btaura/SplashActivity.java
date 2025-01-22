package np.nicolai.btaura;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


public class SplashActivity extends AppCompatActivity {
    View o_1, o_2, o_3;
    int o_val=1;
    TextView splash_message;
    ConstraintLayout mainLayout;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences=getSharedPreferences("SplashActivitySharedPreferences",MODE_PRIVATE);
        boolean isAlreadyLaunched=sharedPreferences.getBoolean("isAlreadyLaunched",true);//if isAlreadyLaunched doesn't exists it returns true
        if(isAlreadyLaunched){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("isAlreadyLaunched",false);
            editor.apply();
            setContentView(R.layout.activity_splash);
        }
        else{
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        splash_message=findViewById(R.id.splash_message);
        o_1=findViewById(R.id.o_1);
        o_2=findViewById(R.id.o_2);
        o_3=findViewById(R.id.o_3);
        o_fill_color();
        mainLayout = findViewById(R.id.main);
        logo=findViewById(R.id.logo);
    }

    public void nextSplash(View v){
        o_transparent();
        o_val++;
        o_fill_color();
        if(o_val==4) {
            o_val=1;
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void o_fill_color(){
        if(o_val==1)
            o_1.setBackgroundColor(Color.WHITE);
        else if(o_val==2) {
            o_2.setBackgroundColor(Color.WHITE);
            logo.setImageResource(R.drawable.bt_logo_2);
            splash_message.setText("Real-time communication with active devices");

        }
        else if(o_val==3) {
            o_3.setBackgroundColor(Color.WHITE);
            logo.setImageResource(R.drawable.bt_logo_3);
            splash_message.setText("Seamless pairing and device management.");
        }
    }
    private void o_transparent(){
        if(o_val==1)
            o_1.setBackgroundColor(Color.TRANSPARENT);
        else if(o_val==2)
            o_2.setBackgroundColor(Color.TRANSPARENT);
        else if(o_val==3)
            o_3.setBackgroundColor(Color.TRANSPARENT);
    }

}