MobileTest v2.0
2016-10-21

1.代码重构，去除所有动态代理，简化代码及调试
2.TestListenerImpl中增加监听UnreachableBrowserException异常，解决appium server因内存泄露崩溃问题
3.增加StopException异常，抛出此异常可停止当前执行
4.增加ViewNavigator类，使App内导航更加简便，大大减少Test类中的代码

简单说明：
test.properties:选择平台
运行android(ios)需修改src/main/resources/android(ios)/config/config.properties
修改对象库src/main/resources/android(ios)/repo
修改用例android(ios)/data
