package testWithDagger.main.dagger.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import testWithDagger.main.R;
import testWithDagger.main.app.DemoApplication;
import testWithDagger.main.dagger.demo.consInjection.LocalDataSource;

public class Dagger2Activity extends AppCompatActivity {

    public static void jump(Context context){
        Intent i=new Intent(context,Dagger2Activity.class);
        context.startActivity(i);
    }

    @Bind(R.id.dagger_tv)
    TextView tv1;


    @Inject
    LocalDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
        ButterKnife.bind(this);

        DemoApplication app=(DemoApplication)getApplication();
        app.getDataComponent().inject(this);


        tv1.setText(dataSource.getStringData(""));

    }







}
