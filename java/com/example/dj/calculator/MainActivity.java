package com.example.dj.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.lang.Math;


public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();


    private double housePrice = 0.0;
    private double downPayment = 0.0;
    private double annualInterest = 0.0;
    private double mortgageLength = 0.0;


    private TextView houseTextView;
    private TextView downTextView;
    private TextView interestTextView;
    private TextView lengthTextView;
    private TextView monthlyTextView;
    private TextView totalTextView;

    private EditText houseEditText;
    private EditText downEditText;
    private EditText interestEditText;
    private EditText lengthEditText;

    private Button calculateButton;
    private Button cancelButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        houseTextView = (TextView) findViewById(R.id.houseTextView);
        downTextView = (TextView) findViewById(R.id.downTextView);
        interestTextView = (TextView) findViewById(R.id.interestTextView);
        lengthTextView = (TextView) findViewById(R.id.lengthTextView);
        monthlyTextView = (TextView) findViewById(R.id.monthlyTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        houseEditText = (EditText) findViewById(R.id.houseEditText);
        houseEditText.addTextChangedListener(houseEditTextWatcher);
        downEditText = (EditText) findViewById(R.id.downEditText);
        downEditText.addTextChangedListener(downEditTextWatcher);
        interestEditText = (EditText) findViewById(R.id.interestEditText);
        interestEditText.addTextChangedListener(interestEditTextWatcher);
        lengthEditText = (EditText) findViewById(R.id.lengthEditText);
        lengthEditText.addTextChangedListener(lengthEditTextWatcher);


        calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(calculateListener);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(cancelListener);
    }

    private final TextWatcher houseEditTextWatcher =
            new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try{
                        housePrice = Double.parseDouble(s.toString());
                        houseTextView.setText(currencyFormat.format(housePrice));
                    }
                    catch(NumberFormatException e){
                        houseTextView.setText("");
                    }

                }


                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };

    private final TextWatcher downEditTextWatcher =
            new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try{
                        downPayment = Double.parseDouble(s.toString());
                        downTextView.setText(currencyFormat.format(downPayment));
                    }
                    catch(NumberFormatException e){
                        downTextView.setText("");
                    }

                }


                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };


    private final TextWatcher interestEditTextWatcher =
            new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try{
                        annualInterest = Double.parseDouble(s.toString()) / 100.0;
                        interestTextView.setText(percentFormat.format(annualInterest));
                    }
                    catch(NumberFormatException e){
                        interestTextView.setText("");
                    }

                }


                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };


    private final TextWatcher lengthEditTextWatcher =
            new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try{
                        mortgageLength = Double.parseDouble(s.toString());
                        lengthTextView.setText(s);
                    }
                    catch(NumberFormatException e){
                        lengthTextView.setText("");
                    }

                }


                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };

    private final View.OnClickListener calculateListener = new View.OnClickListener(){

        public void onClick(View v){
            try{
                double monthlyInterestRate = annualInterest / (12 * 100);
                double months = (mortgageLength * 12);
                double loanAmount = (housePrice - downPayment);

                double monthlyPayment = (loanAmount * monthlyInterestRate) / (1- Math.pow(1+ monthlyInterestRate,(months * -1)));

                double totalPayment = monthlyPayment * months;

                if(Double.isNaN(monthlyPayment) || Double.isNaN(totalPayment)){
                    throw new Exception();
                }
                monthlyTextView.setText(currencyFormat.format(monthlyPayment));
                totalTextView.setText(currencyFormat.format(totalPayment));
            }

            catch(Exception e){
                monthlyTextView.setText("There was an error.");
                totalTextView.setText("Please press Cancel and try again.");
            }

        }

    };

    private final View.OnClickListener cancelListener = new View.OnClickListener(){

        public void onClick(View v){
            annualInterest = 0.0;
            mortgageLength = 0.0;
            housePrice = 0.0;
            downPayment = 0.0;

            houseEditText.setText("");
            downEditText.setText("");
            interestEditText.setText("");
            lengthEditText.setText("");

            monthlyTextView.setText("");
            totalTextView.setText("");
        }
    };







}
