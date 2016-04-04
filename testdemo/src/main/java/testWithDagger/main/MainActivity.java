package testWithDagger.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import testWithDagger.main.app.DemoApplication;
import testWithDagger.main.dagger.demo.Dagger2Activity;
import testWithDagger.main.data.MyDataSource;

public class MainActivity extends AppCompatActivity {

    String passedIn;
    public static final String INTENT_KEY="input";

    @Bind(R.id.main_tv) TextView tv1;
    @Bind(R.id.bn_jump) Button bn_jump;
    @OnClick(R.id.bn_jump)
    void jump(){
        Dagger2Activity.jump(this);
    }


    @Inject
    MyDataSource dataSource;

    void initSource(){
        DemoApplication app=(DemoApplication)getApplication();
        app.getDataComponent().inject(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initSource();
        Intent i=getIntent();
        if (i!=null) passedIn=i.getStringExtra(INTENT_KEY);
        tv1.setText(dataSource.getStringData(passedIn==null?"hello dagger":passedIn));
    }



}
