package com.kknews.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.kknews.controller.DeleteController;
import com.kknews.controller.KKNewsController;
/**
 * 
 * @author zyq
 * @e-mail zhuyq@zensvision.com
 * @date 2016年9月23日 下午5:37:10
 */

public class KKNewsConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
	} 

	@Override
	public void configRoute(Routes me) {
		// TODO 自动生成的方法存根
		me.add("kknews", KKNewsController.class);//资讯
		me.add("delete", DeleteController.class);//资讯
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO 自动生成的方法存根
		loadPropertyFile("jdbc.properties"); //load配置文件
		C3p0Plugin c3p0 = new C3p0Plugin(getProperty("jdbc.url"), getProperty("jdbc.username"), getProperty("jdbc.password"));
		me.add(c3p0); //添加插件
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0);
		me.add(arp);
		arp.setDialect(new MysqlDialect());
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void configHandler(Handlers me) {
		// TODO 自动生成的方法存根

	}

}
