import java.io.IOException;

import com.dangdang.config.service.GeneralConfigGroup;
import com.dangdang.config.service.observer.IObserver;
import com.dangdang.config.service.zookeeper.ZookeeperConfigGroup;
import com.dangdang.config.service.zookeeper.ZookeeperConfigProfile;
import com.google.common.base.Preconditions;
import com.tower.service.config.DynamicConfig;

public class Tester {

	public static void main(String[] args) {
		DynamicConfig config0 = new DynamicConfig();
		config0.init();
		DynamicConfig config1 = new DynamicConfig();
		config1.init();
		DynamicConfig config2 = new DynamicConfig();
		config2.init();
		DynamicConfig config3 = new DynamicConfig();
		config3.init();
		while (true){
			try {
				Thread.sleep(1000);
				System.out.println(config3.getString("test"));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
