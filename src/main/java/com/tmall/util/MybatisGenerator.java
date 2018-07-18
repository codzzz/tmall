package com.tmall.util;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MybatisGenerator {

	public static void main(String[] args) throws Exception {
		List<String> warnings = new ArrayList<>();
		boolean overwrite = true;
		InputStream is  = MyBatisGenerator.class.getClassLoader().getResource("generatorConfig.xml").openStream();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(is);
		is.close();
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator mbg = new MyBatisGenerator(config, callback, warnings);
		mbg.generate(null);
	}

}
