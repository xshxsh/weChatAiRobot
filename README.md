# 微信AI机器人介绍

## 产品介绍
本产品是基于个人微信，连接AI模型，生成的微信AI机器人，可提供AI问答服务，上知天文下知地理！
目前支持私聊或者群聊@我的问答场景使用；后续会增加自动添加好友，发朋友圈，转发消息等功能。

## 前期准备
### 个人微信号
因为机器人是基于腾讯官方微信账号使用的，有可能会面临封号风险（经过大量测试，10个群的会话处理尚未被封号，更多群聊或者私聊风险未知）。
建议注册一个微信小号，用作机器人。

### Windows电脑
机器人目前只支持在windows电脑上运行。

### 注册AI模型账号
机器人需要连接AI大模型账号，AI模型按照回答字数收费，自己注册开始会有50万左右的免费字数体验，超过50万字后需要收费。用户可自己注册账号，也可联系作者提供服务。


## 搭建使用
### 注册AI账号
（若使用作者提供的AI账号，可跳过此步骤，直接联系作者）
机器人连接的AI模型是讯飞星火大模型，任何人都可以注册使用，有50万体验字数限制，超过会收费。
#### 注册讯飞账号
注册地址：https://www.xfyun.cn/
进入上面的地址，用个人手机号注册即可。

#### 创建应用
注册账号后，进入“控制台”，创建新应用，记住APPID。

#### 申请API使用
申请地址：https://www.xfyun.cn/solutions/xinghuoAPI

填写申请后，只需等待工单审核通过（通常1天内通过），通过后会有短信通知，可以在控制台看到AI账号的相关信息。

把以上账号对应字段名称填到解压文件的application-dev.yml中（文件用记事本打开即可）。

至此，AI账号申请流程完毕。首次申请默认会有50万tokens体验，1tokens 约等于1.5个中文汉字 或者 0.8个英文单词。若使用完50万tokens后可换个手机号再申请体验，需要长期稳定服务的可以提工单申请永久服务，或者联系作者提供（收费）。

### 安装微信
机器人只支持特定版本的微信。
在解压文件里，双击“WeChat3.6.0.18.exe”安装微信，如果之前装过微信的需要覆盖安装。
安装完成后，在微信设置中关闭自动更新（更新了版本后可能用不了）。

## 安装代理软件
### 设置目录
在解压文件里，双击“代理框架.exe”，启动后设置微信相关目录。

其中，微信安装目录可按图示步骤获取，默认安装目录：
C:\Program Files (x86)\Tencent\WeChat\[3.6.0.18]

图片缓存目录，自己新建一个目录即可，如：
C:\Users\Administrator\Documents\DaenWxHookImg\
微信数据目录，可安装图示目录获取，默认目录：
C:\Users\Administrator\Documents\WeChat Files\（只取到WeChat Files目录即可）


### 添加微信

接下来需要在手机扫码登录（建议用小号），如果经常使用，可以在手机设置自动登录，免手机确认。

微信启动后，如果提示更新，忽略即可。

至此，代理框架设置及微信设置完成。如果把代理服务关闭了，下次只需要在“微信管理”再添加微信即可。
## 安装JDK
如果之前已经安装过的无需再安装（再安装也无妨，会覆盖原有版本）。
打开JDK目录，64位电脑安装“x64-jdk.msi”，32位电脑安装“x86-jdk.msi”。
双击启动安装，一直点击“next”，记住安装目录，默认是“C:\Program Files\Amazon Corretto\”。

安装完成后，用快捷键“win+r”打开运行，输入“cmd”，在cmd命令行执行“java -version”验证安装结果。

至此，jdk安装完毕。

## 启动服务
双击“start.bat”启动服务，最后出现“Started WeChatRootApplication ...”表示启动成功。

启动完成后不要关闭此窗口，关闭服务会停止，可以缩小窗口到任务栏。
## 测试使用
用另一个微信号向机器人微信发送消息，可以看到回复。

也可以在命令行看到打印的日志：

至此，微信机器人启动完成，可以愉快地使用了。

![image](https://github.com/xshxsh/weChatAiRobot/assets/38281418/daba1e10-1aa7-432c-a672-00bccf356993)

也可以在命令行看到打印的日志：
![image](https://github.com/xshxsh/weChatAiRobot/assets/38281418/8a578ec0-515a-4822-b3f4-1431bce3bf9d)

### 旅游：
<img width="432" alt="image" src="https://github.com/xshxsh/weChatAiRobot/assets/38281418/1dd68508-cd4e-4bb9-8d54-ad70e662d8f6">
### 写代码：
<img width="420" alt="image" src="https://github.com/xshxsh/weChatAiRobot/assets/38281418/af4c61e8-9e0a-41c3-a2c0-08697019bdb4">
### 教做菜：
<img width="434" alt="image" src="https://github.com/xshxsh/weChatAiRobot/assets/38281418/673d03fe-676b-4085-850b-c305ce223d23">
### 历史：
<img width="420" alt="image" src="https://github.com/xshxsh/weChatAiRobot/assets/38281418/851acf60-69b7-41a1-acad-c5256a7dc0a5">
### 天文：
<img width="434" alt="image" src="https://github.com/xshxsh/weChatAiRobot/assets/38281418/da2b3be8-cefd-4703-b326-aedfa189257f">
### 地理
<img width="435" alt="image" src="https://github.com/xshxsh/weChatAiRobot/assets/38281418/c6b63f88-594c-483c-a256-8d1cb65c0237">
