package testWithDagger.main.di.components;

import javax.inject.Singleton;

import dagger.Component;
import testWithDagger.main.di.modules.DataModule;

/**
 * Created by Administrator on 16-4-1.
 */

@Singleton
@Component(modules={DataModule.class})
public interface ApplicationComponent extends DataComponent {

}
