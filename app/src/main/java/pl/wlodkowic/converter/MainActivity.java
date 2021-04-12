package pl.wlodkowic.converter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Spinner conversion;
    private EditText number;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conversion = (Spinner) findViewById(R.id.spinner_conversion);
        number = (EditText) findViewById(R.id.editText_input);
        result = (TextView) findViewById(R.id.textView_result);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.converter_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_info:
                Intent i = new Intent(this, InfoActivity.class);
                startActivity(i);
                return true;
            case R.id.action_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("score", result.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        result.setText(savedInstanceState.getString("score", ""));
    }

    public void onClickConvertButton(View view) {
        int method;
        int inBase;
        int outBase;
        String outcome;

        if (TextUtils.isEmpty(number.getText())) {
            statement(0);
        } else {
            method = (int) conversion.getSelectedItemId();
            switch (method) {
                case 0:
                default:
                    inBase = 10;
                    outBase = 2;
                    break;
                case 1:
                    inBase = 10;
                    outBase = 16;
                    break;
                case 2:
                    inBase = 2;
                    outBase = 10;
                    break;
                case 3:
                    inBase = 2;
                    outBase = 16;
                    break;
                case 4:
                    inBase = 16;
                    outBase = 10;
                    break;
                case 5:
                    inBase = 16;
                    outBase = 2;
                    break;

            }
            outcome = convert(number.getText().toString(), inBase, outBase);

            result.setText(outcome.toUpperCase());

            if ((TextUtils.isEmpty(outcome))) {
                statement(1);
            }
        }
    }

    public String convert(String number, int inBase, int outBase) {
        try {
            return Integer.toString(Integer.parseInt(number, inBase), outBase);
        } catch (Exception e) {
            return "";
        }
    }

    private void statement(Integer error) {
        Vibrator v;
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.statementTitle);
        if (error == 0) {
            adb.setMessage(R.string.empty);
        } else {
            adb.setMessage(R.string.wrong);
        }
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        AlertDialog ad = adb.create();
        ad.show();

    }
}