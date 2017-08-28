import java.io.IOException;

import com.dangdang.config.service.GeneralConfigGroup;
import com.dangdang.config.service.observer.IObserver;
import com.dangdang.config.service.zookeeper.ZookeeperConfigGroup;
import com.dangdang.config.service.zookeeper.ZookeeperConfigProfile;
import com.google.common.base.Preconditions;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZookeeperConfigProfile configProfile = new ZookeeperConfigProfile(
				"127.0.0.1:2181", "/config",
				"1.0.0");
		GeneralConfigGroup propertyGroup1 = new ZookeeperConfigGroup(
				configProfile, "property-group1");
		System.out.println(propertyGroup1);

		// Listen changes
		propertyGroup1.register(new IObserver() {
			@Override
			public void notified(String data, String value) {
				// Some initialization
			}
		});

		String stringProperty = propertyGroup1.get("string_property_key");
		Preconditions.checkState("Config-Toolkit".equals(stringProperty));
		String intProperty = propertyGroup1.get("int_property_key");
		Preconditions.checkState(1123 == Integer.parseInt(intProperty));

		try {
			propertyGroup1.close();
		} catch (IOException e) {
		}
	}

}
