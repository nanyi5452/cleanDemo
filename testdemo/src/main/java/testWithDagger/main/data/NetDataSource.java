package testWithDagger.main.data;

/**
 * Created by Administrator on 16-4-1.
 */
public class NetDataSource implements MyDataSource {

    @Override
    public String getStringData(String str_input) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str_input+" from internet";
    }
}
