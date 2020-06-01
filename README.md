# Rapid-Android
  
andorid-个人快速开发框架

项目主要结构流程 https://www.processon.com/view/5e49f658e4b01f766b46e96e <br>
一级页面 ottom-navigation-bar + Fragment
网络请求：retrofit2 + rxjava1.1.2 + rxandroid1.1.0 + Gson
图片加载 glide
兼容至android p
LogCat封装工具，三种类型，方便线上排查
gradle productFlavors配置方便区分环境
BaseRecyclerViewAdapterHelper + swipeToLoadLayout

###注意点
BaseResponse 中声明数据接口success状态
FOREGROUND_SERVICE 需要单独声明
okhttp相关配置为no cache，Proxy.NO_PROXY
httpSubscriber 中处理onError code
