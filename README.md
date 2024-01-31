#### 作者致言

   最近工作比较忙，加微信和qq咨询的人也比较多,建议，大家尽量在评论区留言，如果比较着急的话加微信，说清楚问题，看到有时间，一定回复！

  首先恭喜你看到了这个项目，在开发这个项目前，本人已经通过百度和gitee上搜索了很多关于微信公众号爬虫相关的项目，目前大致爬取微信公众号的方法重要有三种，下面有讲到，本人尝试前两种方法，第三种太多麻烦，需要耗费太多的时间和精力于是放弃，采取了性价比最高的第二种方法，本项目相对gitee其他的开源项目，最大的优势，功能相对更完善一些和代码近期推送的，随着微信公众号的这两年的改变，近期没有更新过的爬虫开源项目，大多不能正常运行。开发维护不易，觉得有用的点赞收藏吧，这也是我继续下去的动力。

#### 我的博客

1. **JAVA OPC UA专栏**：[https://blog.csdn.net/weixin_40986713/category_12356608.html](https://blog.csdn.net/weixin_40986713/category_12356608.html)
2. **AI绘画 | Stable diffusion**：[https://blog.csdn.net/weixin_40986713/category_12481790.html](https://blog.csdn.net/weixin_40986713/category_12481790.html) 
3. **java高级技术专栏**：[https://blog.csdn.net/weixin_40986713/category_10796066.html](https://blog.csdn.net/weixin_40986713/category_10796066.html)
4. **java Selenium自动化爬虫**：[https://blog.csdn.net/weixin_40986713/category_12165790.html](https://blog.csdn.net/weixin_40986713/category_12165790.html)
5. **java 推荐算法专栏**：[https://blog.csdn.net/weixin_40986713/category_12268014.html](https://blog.csdn.net/weixin_40986713/category_12268014.html)
6. **Java视频图像处理专栏**：[https://blog.csdn.net/weixin_40986713/category_11109931.html](https://blog.csdn.net/weixin_40986713/category_11109931.html) 

#### 项目背景
个人在业余时间，写的一个以微信公众号爬虫为主要功能，普通网页爬虫、浏览器控制、邮件群发功能为辅的简单DEMO。功能简单，给开发者巨大的学习和发挥的空间。对spring boot和html有一些经验的人来说，上手简单，学习成本低.

#### 功能介绍
爬虫项目，微信公众号文章爬虫，网站文章爬虫，群发邮件系统

#### 项目架构
springBoot 单项目架构

#### 已知爬取微信公众号有三种方法：

第一种：用搜狗微信公众号搜过，这个只能收到前10条；(亲试，好多公众号连近10条都获取不到，放弃)

第二种：用fiddler或手机抓包，从访问链接去获得appmsg_token，发现虽然这个值就在html页面里，但只有抓包的数据里含有效值，直接访问的是空的，而且还有时效性。这样，每次都要抓包获取，就很麻烦。

第三种：就是这种用公众号搜公众号的，虽然速度慢点，但便捷了不少。（每天请求次数限制，约为100次）

#### 使用须知

程序原理:

通过selenium登录获取token和cookie，再自动爬取和下载

使用前提：

1、修改项目中Chrome驱动的路径改为自己本地的

2、有自己的公众号，没有可以申请一个微信公众号（个人订阅版）(https://mp.weixin.qq.com)

3、修改reptile.properties文件中的账号和密码

#### 安装教程

1.  git下载源码
2.  maven构建
3.  idea-java运行

#### 使用说明

1.  core核心包 java主方法运行
2.  spring boot 主类运行
#### 功能简介

简单的爬虫系统和邮件系统
1.爬虫分为微信公众号爬虫和和普通网页爬虫(主要通过selenium和jsoup实现)
2.java邮箱发送系统，可以实现邮件群发(主要通过javax.mail实现)

#### 系统运行视图
![输入图片说明](https://images.gitee.com/uploads/images/2020/0612/141019_d5ee2bee_1981977.png "cc9207b96f12b04c52f8279cb918f32.png")

#### 可视化页面
![输入图片说明](https://images.gitee.com/uploads/images/2020/0724/180527_49676bda_1981977.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0724/180555_09b09a49_1981977.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2020/0724/180621_2bae8894_1981977.png "屏幕截图.png")

#### 常见问题
在使用selenium启动谷歌Chrome浏览器的时候，是需要用到chromedirver的，两者之间的版本是需要匹配的，否则会出现下面类似的报错：

```
Only local connections are allowed.
org.openqa.selenium.WebDriverException: unknown error: cannot find Chrome binary
  (Driver info: chromedriver=2.35.528161 (5b82f2d2aae0ca24b877009200ced9065a772e73),platform=Windows NT 10.0.18363 x86_64) (WARNING: The server did not provide any stacktrace information)
Command duration or timeout: 76 milliseconds
Build info: version: '3.141.59', revision: 'e82be7d358', time: '2018-11-14T08:17:03'
System info: host: 'WIN-9T6EKDMSTI5', ip: '172.16.10.8', os.name: 'Windows 10', os.arch: 'amd64', os.version: '10.0', java.version: '1.8.0_221'
Driver info: driver.version: ChromeDriver
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
```
 **解决方案，查看这篇文章** 

[Selenium Chrome浏览器版本与chromedriver驱动兼容版本对照表](https://blog.csdn.net/weixin_40986713/article/details/115075324)

#### 爬虫教学专栏

[Selenium自动化爬虫](https://blog.csdn.net/weixin_40986713/category_12165790.html)

#### 技术交流&问题反馈
      微信号：vxhqqh



