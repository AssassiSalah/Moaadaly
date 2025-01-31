package com.example.moaadaly;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class moaadal_layout extends AppCompatActivity {

    DatabaseHandler database;
    ArrayList<Module_Only_Exame> modules_list;
    ArrayList<TextView> modules_Moyan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moaadal_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Accessing TextViews
        TextView nameSpeciality = findViewById(R.id.Name_Speciality);
        String nameofSpeciality = "M1 IA";
        nameSpeciality.setText(nameofSpeciality); // Example: Set new text

        TextView nameSaves = findViewById(R.id.Name_Saves);
        String nameSaved = "Current_Note";
        nameSaves.setText(nameSaved);

        TextView semesterNumber = findViewById(R.id.Semaster_Number);
        String semester = "S1";
        semesterNumber.setText(semester);

        database = new DatabaseHandler(this);

        //database.reCreateTable();

        load(null);
    }

    //HACK
    @NonNull
    private ArrayList<Module_Only_Exame> getModuleList() {
        ArrayList<Module_Only_Exame> modules = new ArrayList<>();
        modules.add(new Module_With_TD_TP("AAP",3, 6,0.25f,0.25f));
        modules.add(new Module_With_TD_TP("SD",3, 6,0.25f,0.25f));
        modules.add(new Module_With_TP("RC",3, 6,0.33f));
        modules.add(new Module_With_TD_TP("ISD",2, 4,0.25f,0.25f));
        modules.add(new Module_With_TP("AA",2, 4,0.5f));
        modules.add(new Module_Only_Exame("ENG",1, 2));
        modules.add(new Module_Only_Exame("ENT",1, 2));
        return modules;
    }

    private void createTableRows() {
        // Accessing TableLayout and TableRow
        TableLayout tableLayout = findViewById(R.id.Table_Layout); // Add this id if missing from XML

        // Add the row to the table layout
        for (Module_Only_Exame module : modules_list) {
            TextView moyan = new TextView(this);
            moyan.setTextColor(Color.WHITE);

            modules_Moyan.add(moyan);
            tableLayout.addView(createRow(module, moyan));

            //Make 20dp Space
            tableLayout.addView(new View(this) {{
                setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        (int) (20 * getResources().getDisplayMetrics().density)
                ));
            }});
        }
    }

    @NonNull
    private TableRow createRow(Module_Only_Exame module, TextView moyan) {
        TableRow tableRow = new TableRow(this);

        // Add dynamic rows to the table
        TextView moduleName = new TextView(this);
        moduleName.setText(module.getName_Module());
        moduleName.setTextColor(Color.WHITE);
        tableRow.addView(moduleName);

        TextView coefficient = new TextView(this);
        coefficient.setText(String.valueOf(module.getCouf()));
        coefficient.setTextColor(Color.WHITE);
        tableRow.addView(coefficient);

        TextView credit = new TextView(this);
        credit.setText(String.valueOf(module.getCred()));
        credit.setTextColor(Color.WHITE);
        tableRow.addView(credit);

        EditText td = createEditTextDecimal();
        td.setTag("TD_" + module.getName_Module()); // Assign a unique tag
        td.addTextChangedListener(getTextWatcher(td)); // Add TextWatcher
        td.setTextColor(Color.WHITE);
        if(module instanceof Module_With_TD) {
            td.setText(((Module_With_TD) module).getTD_Note_String());
            tableRow.addView(td);
        } else
            if(module instanceof Module_With_TD_TP) {
                td.setText(((Module_With_TD_TP) module).getTD_Note_String());
                tableRow.addView(td);
            } else {
                // No Need For Edit Text
                TextView empty = new TextView(this);
                empty.setText("N/A");
                empty.setTextColor(Color.WHITE);
                tableRow.addView(empty);
            }


        EditText tp = createEditTextDecimal();
        tp.setTag("TP_" + module.getName_Module()); // Assign a unique tag
        tp.addTextChangedListener(getTextWatcher(tp)); // Add TextWatcher
        tp.setTextColor(Color.WHITE);
        if(module instanceof Module_With_TP) {
            tp.setText(((Module_With_TP) module).getTP_Note_String());
            tableRow.addView(tp);
        } else
            if(module instanceof Module_With_TD_TP) {
                tp.setText(((Module_With_TD_TP) module).getTP_Note_String());
                tableRow.addView(tp);
            } else {
                // No Need For Edit Text
                TextView empty = new TextView(this);
                empty.setText("N/A");
                empty.setTextColor(Color.WHITE);
                tableRow.addView(empty);
            }

        EditText exam = createEditTextDecimal();
        exam.setTag("Exam_" + module.getName_Module()); // Assign a unique tag
        exam.addTextChangedListener(getTextWatcher(exam)); // Add TextWatcher

        exam.setText(module.getExam_Note_String());
        exam.setTextColor(Color.WHITE);
        tableRow.addView(exam);

        moyan.setText(String.valueOf(calculateMoyan(module)));
        tableRow.addView(moyan);

        return tableRow;
    }

    @NonNull
    private EditText createEditTextDecimal() {
        EditText editTextDecimal = new EditText(this);

        editTextDecimal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        // Apply an InputFilter to restrict the input format
        editTextDecimal.setFilters(new InputFilter[] {
                (source, start, end, dest, d_start, d_end) -> { // new InputFilter()
                    String input = dest.toString() + source.toString();

                    // Regular expression to match XX.XX format
                    if (input.matches("^\\d{0,2}(\\.\\d{0,2})?$")) {
                        return null; // Accept the input
                    }
                    return ""; // Reject the input
                }
        });

        return editTextDecimal;
    }

    // Create a reusable TextWatcher
    private TextWatcher getTextWatcher(EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing before text changes
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Optionally handle live changes here
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && s.toString().equals("0.0")) {
                    s.clear(); // Remove "0.0" when user starts typing
                    return;
                }

                String tag = (String) editText.getTag();
                String nameModule = tag.split("_")[1];
                String newValue = s.toString();
                Log.d("EditTextChange", "Tag: " + tag + ", New Value: " + newValue);

                // Call your custom logic or methods
                handleValueChange(tag, nameModule, newValue);

                Module_Only_Exame module = searchForModule(nameModule);
                if(module == null)
                    return;

                updateTheValue(module, tag, newValue);
                int index = modules_list.indexOf(module);
                if(modules_list.size() <= index || modules_Moyan.size() <= index)
                    return;

                TextView moyanTextView = modules_Moyan.get(index);

                moyanTextView.setText(String.valueOf(calculateMoyan(module)));

                calculateMoyanTotal();
            }
        };
    }

    private Module_Only_Exame searchForModule(String nameModule) {
        for(Module_Only_Exame module : modules_list)
            if(module.getName_Module().equals(nameModule))
                return module;

        return null;
    }

    // Handle the updated value based on the tag
    private void handleValueChange(String tag, String nameModule, String newValue) {
        // Identify the source of the change using the tag
        if (tag.startsWith("TD_")) {
            Log.d("ValueUpdate", "TD for Module: " + nameModule + ", New Value: " + newValue);
            // Handle TD update
        } else if (tag.startsWith("TP_")) {
            Log.d("ValueUpdate", "TP for Module: " + nameModule + ", New Value: " + newValue);
            // Handle TP update
        } else if (tag.startsWith("Exam_")) {
            Log.d("ValueUpdate", "Exam for Module: " + nameModule + ", New Value: " + newValue);
            // Handle Exam update
        }
    }

    private void updateTheValue(Module_Only_Exame module, String tag, String newValueString) {
        float newValue;

        if(newValueString.isEmpty())
            newValue = 0.0f;
        else {
            try {
                newValue = Float.parseFloat(newValueString);  // Try parsing the string to a Float
                // If successful, Continue
            } catch (NumberFormatException e) {
                return;  // If an exception occurs, Exit
            }
        }

        Log.d("Changing Valuer", module.getName_Module());
        if (tag.startsWith("TD_")) {
            if(module instanceof Module_With_TD_TP) {
                Log.d("ValueUpdate", "TD from value: " + ((Module_With_TD_TP) module).getTD_Note() + ", To New Value: " + newValue);
                ((Module_With_TD_TP) module).setTD_Note(newValue);
            } else {
                Log.d("ValueUpdate", "TD from value: " + ((Module_With_TD) module).getTD_Note() + ", To New Value: " + newValue);
                ((Module_With_TD) module).setTD_Note(newValue);
            }
        } else if (tag.startsWith("TP_")) {
            if(module instanceof Module_With_TD_TP) {
                Log.d("ValueUpdate", "TP from value: " + ((Module_With_TD_TP) module).getTP_Note() + ", To New Value: " + newValue);
                ((Module_With_TD_TP) module).setTP_Note(newValue);
            } else {
                Log.d("ValueUpdate", "TP from value: " + ((Module_With_TP) module).getTP_Note() + ", To New Value: " + newValue);
                ((Module_With_TP) module).setTP_Note(newValue);
            }
        } else if (tag.startsWith("Exam_")) {
            Log.d("ValueUpdate", "Exam from value: " + module.getExam_Note() + ", New Value: " + newValue);
            module.setEXAM_Note(newValue);
            // Handle Exam update
        }
    }

    private float calculateMoyan(Module_Only_Exame module) {
        // Cast The Value To The Right Class
        if (module instanceof Module_With_TD)
            return calculateMoyan((Module_With_TD) module);
        if (module instanceof Module_With_TP)
            return calculateMoyan((Module_With_TP) module);
        if (module instanceof Module_With_TD_TP)
            return calculateMoyan((Module_With_TD_TP) module);
        else
            return  module.getExam_Note();
    }

    private float calculateMoyan(Module_With_TD_TP module) {
        return calculateMoyan(module.getTD_Note()
                , module.getTD_Percent()
                , module.getTP_Note()
                , module.getTP_Percent()
                , module.getExam_Note());
    }
    private float calculateMoyan(Module_With_TD module) {
        return calculateMoyan(module.getTD_Note()
                , module.getTD_Percent()
                , module.getExam_Note());
    }
    private float calculateMoyan(Module_With_TP module) {
        return calculateMoyan(module.getTP_Note()
                , module.getTP_Percent()
                , module.getExam_Note());
    }

    private float calculateMoyan(float T, float T_percent, float exam) {
        // Approximate To Two Decimal Places
        return Math.round((T * T_percent + exam * (1 - T_percent)) * 100) / 100.0f;
    }

    private float calculateMoyan(float TD, float TD_percent, float TP, float TP_percent, float exam) {
        // Approximate To Two Decimal Places
        return Math.round((TD * TD_percent + TP * TP_percent + exam * (1 - TD_percent - TP_percent)) * 100) / 100.0f;
    }

    private void calculateMoyanTotal() {
        if (modules_list.size() != modules_Moyan.size()) {
            Log.e("Error", "Mismatch: modules_list=" + modules_list.size() + ", modules_Moyan=" + modules_Moyan.size());
            return; // Prevent crash
        }
        float moyan_total = 0.0f;
        float couf_total = 0;
        for(Module_Only_Exame module : modules_list) {
            TextView moyanTextView = modules_Moyan.get(modules_list.indexOf(module));
            float moyan_local = calculateMoyan(module);
            moyan_total += moyan_local * module.getCouf();
            couf_total += module.getCouf();
            moyanTextView.setText(String.valueOf(moyan_local));
        }
        Log.d("dbb", "Moyan Total: " + moyan_total + " , couf Total: " + couf_total + " ,the / of the total: " + (moyan_total / couf_total) + " , the round value: " + (Math.round(moyan_total / couf_total) * 100 / 100.0f));
        TextView moyan = findViewById(R.id.Moyan);
        moyan.setText(String.valueOf(moyan_total / couf_total));
    }

    public void load(View view) {
        TableLayout tableLayout = findViewById(R.id.Table_Layout);
        tableLayout.removeAllViews(); // Clear old data before loading new ones

        modules_list = database.getAllModules();

        if(modules_list.isEmpty())
            modules_list = getModuleList();

        modules_Moyan = new ArrayList<>();
        createTableRows();

        Toast.makeText(this, "Load Complete", Toast.LENGTH_LONG).show();
    }
    public void save(View view) {
        int id = 1;
        for (Module_Only_Exame m : modules_list) {
            if (database.moduleExists(m.getName_Module())) {
                database.updateModuleByName(m, 1); // Update instead of inserting
            } else {
                if (m instanceof Module_With_TD_TP)
                    database.addModuleWithTDTP((Module_With_TD_TP) m, id);
                else if (m instanceof Module_With_TD)
                    database.addModuleWithTD((Module_With_TD) m, id);
                else if (m instanceof Module_With_TP)
                    database.addModuleWithTP((Module_With_TP) m, id);
                else
                    database.addModule(m, id);
            }
            Log.d("Mod", "--> " + m);
        }
        Toast.makeText(this, "Save Complete", Toast.LENGTH_LONG).show();
    }

}