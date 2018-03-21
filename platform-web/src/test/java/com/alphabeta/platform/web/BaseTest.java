package com.alphabeta.platform.web;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * 测试基类
 * <p>
 * Created by alphabeta on 17-9-24 下午6:47.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/platform-web-test.xml"})
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {
}
