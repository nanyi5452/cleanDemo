package testWithDagger.main.data;

/**
 * Created by Administrator on 16-4-1.
 */
public class FakeDataSource implements MyDataSource {
    @Override
    public String getStringData(String str_input) {
        return str_input+" from mock data source";
    }
}
