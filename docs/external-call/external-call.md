
# camellia-external-call

## 背景
多租户自动隔离的外部调用系统：  
* 在云平台系统中（Paas/Saas），平台方需要调用客户的接口，客户的接口被认为是不可靠的，因此我们需要一个机制可以自动屏蔽或者隔离有问题的调用接口
* 这样的外部调用可以分为两种类型，同步调用和异步调用
* 同步调用：调用方需要获取到调用的结果，并且根据返回值做进一步的业务逻辑
* 异步调用：调用方不需要第一时间获取到调用的结果，只是单向的将结果告知给目标接口

## 希望解决的问题
* 异常的目标接口可能阻塞或者影响平台方其他正常的请求
* 期望系统可以自动识别并隔离异常目标接口，正常租户的接口不受影响，特别是延迟不受影响，也就是不能被堵住了