package testWithDagger.main.di.components;

import testWithDagger.main.MainActivity;
import testWithDagger.main.dagger.demo.Dagger2Activity;

/**
 * Created by Administrator on 16-4-1.
 */
public interface DataComponent {
    void inject(MainActivity mainActivity);
    void inject(Dagger2Activity activity);
}
