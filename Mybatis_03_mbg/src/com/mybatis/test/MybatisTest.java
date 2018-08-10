package com.mybatis.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import oracle.net.aso.c;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.mybatis.bean.Employee;
import com.mybatis.bean.EmployeeExample;
import com.mybatis.bean.EmployeeExample.Criteria;
import com.mybatis.dao.EmployeeMapper;


/**
 * 类说明:
 * @author:pgf
 * @date:  2018年7月5日下午4:42:43
 */
public class MybatisTest {

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
	
	/**
	 * 代码自动生成工具-generator
	 * @author:pgf
	 * @date:  2018年7月18日下午1:50:09
	 * @return:void
	 */
	@Test
	public void testMBG() throws Exception{
		List<String> warnings = new ArrayList<String>();
	    boolean overwrite = true;
	    File configFile = new File("generatorConfig.xml");
	    ConfigurationParser cp = new ConfigurationParser(warnings);
	    Configuration config = cp.parseConfiguration(configFile);
	    DefaultShellCallback callback = new DefaultShellCallback(overwrite);
	    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
	    myBatisGenerator.generate(null);	
	}
	
	/**
	 * 测试targetRuntime="MyBatis3Simple" 时
	 * @author:pgf
	 * @date:  2018年7月18日下午2:42:38
	 * @return:void
	 */
	@Test
	public void testMyBatis3Simple() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			List<Employee> selectAll = mapper.selectByExample(null);
			System.out.println(selectAll);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			openSession.close();
		}
		
	}
	/**
	 * 测试targetRuntime="MyBatis3" 时
	 * @author:pgf
	 * @date:  2018年7月18日下午2:42:38
	 * @return:void
	 */
	@Test
	public void testMyBatis3() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			EmployeeExample employeeExample = new EmployeeExample();
			//1、查询员工名字中有e字母的，和员工性别是1的
			Criteria createCriteria = employeeExample.createCriteria();
			createCriteria.andLastNameLike("%e%");
			createCriteria.andGenderEqualTo("1");
			
			//WHERE ( last_name like ? and gender = ? ) or( email like ? ) 
			Criteria createCriteria2 = employeeExample.createCriteria();
			createCriteria2.andEmailLike("%e%");
			//拼接or条件
			employeeExample.or(createCriteria2);
			
			List<Employee> selectAll = mapper.selectByExample(employeeExample);
			System.out.println(selectAll);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			openSession.close();
		}
		
	}
	
	
	
}
