# Rapid-Android
  
andorid-个人快速开发框架

项目主要结构流程 https://www.processon.com/view/5e49f658e4b01f766b46e96e <br>
一级页面 ottom-navigation-bar + Fragment <br>
网络请求：retrofit2 + rxjava1.1.2 + rxandroid1.1.0 + Gson <br>
图片加载 glide <br>
兼容至android p <br>
LogCat封装工具，三种类型，方便线上排查 <br>
gradle productFlavors配置方便区分环境 <br>
BaseRecyclerViewAdapterHelper + swipeToLoadLayout <br>

###配置相关
gradle version : 3.2.1

###注意点
BaseResponse 中声明数据接口success状态 <br>
FOREGROUND_SERVICE 需要单独声明 <br>
okhttp相关配置为no cache，Proxy.NO_PROXY <br>
httpSubscriber 中处理onError code <br>
