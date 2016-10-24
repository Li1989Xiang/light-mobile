# light-mobile

MobileTest Gamma2.0<br>
2016-10-21<br>

1.代码重构，去除所有动态代理，简化代码及调试<br>
2.TestListenerImpl中增加监听UnreachableBrowserException异常，解决appium server因内存泄露崩溃问题<br>
3.增加StopException异常，抛出此异常可停止当前执行<br>
4.增加ViewNavigator类，使App内导航更加简便，大大减少Test类中的代码<br>


使用说明：<br>
1、appium测试环境（自行配置）<br>
2、导入此工程前要导入light-automation框架（maven工程：https://github.com/Li1989Xiang/light-automation.git）<br>
3、运行前修改配置文件：<br>
  1）、选择平台：test.properties<br>
  2）、ios（android）设备名，app路径，系统版本配置：src/main/resources/android(ios)/config/config.properties。PS：一定要修改resources下的路径<br>
  3）、修改对象库：src/main/resources/android(ios)/repo<br>
  4）、修改用例：android(ios)/data<br>
