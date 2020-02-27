package co.raveblue.teacherassistant;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class Solve_sum extends AppCompatActivity {

    public String mEquation = null;

    TextView mResult;
    TextView mEquationView;

    int var1=0, var2=0, sum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_sum);

        mResult = (TextView)findViewById(R.id.textView17);
        mEquationView = (TextView) findViewById(R.id.equation_view2);

        mEquation = getIntent().getExtras().getString("equation");

        assert mEquation != null;
        for (int i = 0; i < mEquation.length(); i++)
        {

                if (mEquation.charAt(i) >= 'a' && mEquation.charAt(i) <= 'z' || mEquation.charAt(i) >= 'A' && mEquation.charAt(i) <= 'Z')
                {

                    Toast.makeText(Solve_sum.this, "Oops Can't Recognize..! Try Again..!", Toast.LENGTH_SHORT).show();
                    mEquationView.setText("Oops Can't Recognize..!");
                    mResult.setText("Try Again..!");
//                    Intent intent = new Intent(Solve_sum.this, Calculator.class);
  //                  startActivity(intent);

                }

        }

//        Toast.makeText(Solve_sum.this, mEquation, Toast.LENGTH_SHORT).show();

        double result = computeAnother(mEquation);

        mEquationView.setText(mEquation);
        mResult.setText("Answer = "+ result);
//        Toast.makeText(Solve_sum.this, result, Toast.LENGTH_LONG).show();
    }

    static double computeAnother(String mEquation)
    {

        double result = 0.0;

        try {
            String noMinus = mEquation.replace("-", "+-");
            String[] byPluses = noMinus.split("\\+");

            for (String multipl : byPluses) {

                String[] byMultipl = multipl.split("\\*");
                double multiplResult = 1.0;

                for (String operand : byMultipl) {

                    if (operand.contains("/")) {

                        String[] division = operand.split("\\/");
                        double divident = Double.parseDouble(division[0]);

                        for (int i = 1; i < division.length; i++) {

                            divident /= Double.parseDouble(division[i]);

                        }

                        multiplResult *= divident;

                    } else {

                        multiplResult *= Double.parseDouble(operand);

                    }
                }

                result += multiplResult;
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }

}
