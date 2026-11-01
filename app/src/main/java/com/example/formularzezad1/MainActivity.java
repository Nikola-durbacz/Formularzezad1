
package com.example.formularzezad1;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etEmail;
    private Button btnBirthDate, btnSubmit;
    private RadioGroup rgMode;
    private SeekBar sbLevel;
    private TextView tvLevelValue, tvSummary;
    private MaterialSwitch swNewsletter;
    private CheckBox cbTerms;
    private Spinner spCourse;

    private String birthDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        btnBirthDate = findViewById(R.id.btnBirthDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        rgMode = findViewById(R.id.rgMode);
        sbLevel = findViewById(R.id.sbLevel);
        tvLevelValue = findViewById(R.id.tvLevelValue);
        tvSummary = findViewById(R.id.tvSummary);
        swNewsletter = findViewById(R.id.swNewsletter);
        cbTerms = findViewById(R.id.cbTerms);
        spCourse = findViewById(R.id.spCourse);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.courses,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spCourse.setAdapter(adapter);

        cbTerms.setOnCheckedChangeListener((buttonView, isChecked) ->
                btnSubmit.setEnabled(isChecked));

        btnBirthDate.setOnClickListener(v -> showDatePicker());

        sbLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvLevelValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btnSubmit.setOnClickListener(v ->
                tvSummary.setText(buildSummary()));

        tvLevelValue.setText(String.valueOf(sbLevel.getProgress()));
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    birthDate = String.format(Locale.US, "%04d-%02d-%02d",
                            year, month + 1, dayOfMonth);
                    btnBirthDate.setText(birthDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }

    private String buildSummary() {
        String mode = "";

        if (rgMode.getCheckedRadioButtonId() == R.id.rbOnsite) {
            mode = getString(R.string.mode_onsite);
        } else if (rgMode.getCheckedRadioButtonId() == R.id.rbRemote) {
            mode = getString(R.string.mode_remote);
        }

        String newsletter = swNewsletter.isChecked()
                ? getString(R.string.yes_short)
                : getString(R.string.no_short);

        String course = spCourse.getSelectedItem().toString();

        return etName.getText().toString().trim() + "\n"
                + etEmail.getText().toString().trim() + "\n"
                + "Ur. " + birthDate + "\n"
                + "Kurs: " + course + "\n"
                + "Tryb: " + mode + "\n"
                + "Poziom: " + sbLevel.getProgress() + "/10\n"
                + "Newsletter: " + newsletter;
    }
}
