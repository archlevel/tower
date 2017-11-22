# 1. REST
>REST是英文Representational State Transfer的缩写，中文翻译为“表述性状态转移”，他是由Roy Thomas Fielding博士在他的论文《Architectural Styles and the Design of Network-based Software Architectures》中提出的一个术语。REST本身只是为分布式超媒体系统设计的一种架构风格，而不是标准。基于Web的架构，实际上就是各种规范的集合，这些规范共同组成了Web架构。比如Http协议，比如客户端服务器模式，这些都是规范。每当我们在原有规范的基础上增加新的规范，就会形成新的架构。而REST正是这样一种架构，他结合了一系列的规范，形成了一种新的基于Web的架构风格。REST通过URL来进行Web Service的 调用，最大限度的减少了开发人员在配置方面的工作量，这也是我们采用REST风格的接口的初衷。


# 2. 地址格式
`https://EndpointURL/{Channel}/{version}/{domain}/{rest-convention}:`

1. 通讯协议必须是https
2. {Channel}标识应用名称，比如mogoroom-renter/mogoroom-renterpc等。
3. {version}代表api的版本信息：当前在线运行的协议为1.0；本文档所描述的为2.0，可以在后续实施中使用
4. {domain}是一个你可以用来定义任何技术的区域(例如：安全-允许指定的用户可以访问这个区域。)或者业务上的原因。同样的功能在同一个前缀之下，如user/flat/supp等。
5. {rest-convention} 代表这个域(domain)下，约定的rest接口集合


# 3. HTTP方法
HTTP 定义了与服务器交互的不同方法，最基本的方法是 GET 和POST。事实上HTTP协议里还定义了其他很多请求方式，如下：
^HTTP请求方式  ^文字描述   ^
|GET(SELECT)|从服务器取出资源（一项或多项）|
|HEAD|获取资源的元数据|
|POST(CREATE)|在服务器新建一个资源|
|PUT(UPDATE)|在服务器更新资源（客户端提供改变后的完整资源）|
|DELETE(DELETE)|从服务器删除资源|
|OPTIONS|获取信息，关于资源的哪些属性是客户端可以改变的|
|TRACE|请求服务器在响应中的实体主体部分返回所得到的内容|
|PATCH(UPDATE)|在服务器更新资源（客户端提供改变的属性）|

# 4. 参数类型
接口参数类型定义要求：接口数据类型请勿用java的原始数据类型，需用封装类型。如：
^原始类型^封装类型^
|short|Short|
|float|Float|
|int|Integer|
|long|Long|
|double|Double|
|boolean|Boolean|

# 5. Request Header和Request Body规则
### 5.1 Request Header
*** 以下Key全部为小写 ***
^Key^Value（描述）^安卓^IOS^H5^PC^
|User-Agent|浏览器的浏览器身份标识字符串(MogoPartner/201709131615|√|√|√|√|
|Host|api.mgzf.com|√|√|√|√|
|AppVersion|App版配号|√|√| | |
|Model|手机型号(精确到具体型号，如：iphone5、iphone6、小米1、小米2等)|√|√| | |
|OS|手机系统(iOS, Android)|√|√| | |
|OSVersion|手机系统版本|√|√| | |
|UUID|App生产商唯一标识符|√|√|√|√|
|RegId|极光推送注册唯一标识|√|√| | |
|Token|Token|√|√|√|√|
|Channel|渠道id（具体渠道id见 文档底部附件）|√|√| | |
|Signature|签名值|√|√| | |
|DeviceId|设备ID（设备id尽量不要变动，app卸载重新安装还是同一个设备id。如刷机暂不考虑）|√|√| | |
|Market|下载来源（具体下载来源id见 文档底部附件）|√|√| | |
|Server|{m=hostname, v=version} 服务端设置|√|√| | |
|Timestamp|时间戳（Long 毫秒）|√|√| | |

### 5.2 Request Body
请求参数(key，value)，以form表单的形式，为可选。范例：
```
currentPage=1&
perPageCount=25&
sortBy=name&
orderBy=desc/asc&
limit=25&
offset=0&
userName=Martin&
pwd=drHssO24
```

# 6. Request Header签名规则
### 6.1 组织参与签名计算的字符串:
```
String signature = 
HTTPMethod + “\n” +
Headers+ “\n” +
URL
```

说明：


HTTPMethod：
```
包含的字段见上述第3点，http请求方式，均为大写
```
Headers：
```
指Headers中参与签名计算的字段（key-value）拼接的字符串，除Host、Signature外其余字段值均参与签名计算。
先对参与Headers签名计算的Header的Key按照字典排序后使用如下方式拼接，如果某个Header的value为空，则使用key+”:” +”\n”参与签名计算。
String headers = 
key1+":"+value1+"\n" +
Key2+":"+value2+"\n" +
...
keyn+":"+valuen+"\n" +
```

Body或URL
```
指path+query+body中的form参数。
组织方法：对query+form参数（按照key的字典排序）按照如下方法拼接，如果query或form参数为空，则URL=path，不需要添加“?”号，如果某个参数的value为，则保留key参与签名计算，“=”号不需要加入签名计算。
String URL = path + 
"?" +
key1+"="+value1+ 
"&"+key2+"="+value2+
...
"&"+keyN+"="+valueN

注：query或form参数中的value可能有多个也有可能会有数组，多个或是数组的时候所有value均需参与到签名计算。value为多个的时候用key相同value不同的方式加入到签名字符串中，value为数组的时候value生成字符串作为一个参数值加入到签名字符串中。key、value均需按字典排序。

例：
1、id=2,3,1。参与签名计算的字符串应为：String URL = path+"?id=1&id=2&id=3"
2、id=[2,3,1]。参与签名计算的字符串应为：String URL =  path+"?id=[1,2,3]"
```

### 6.2 盐值:
用app版本号+具体盐值字符串通过Base64算法生成具体盐值。



### 6.3 计算签名:

```
通过上述6.1和6.2生成的签名字符串及盐值生成md5作为盐值。
String signatureStr = 上述6.1中需要参与签名计算的字符串
String secret = 上述6.2中的盐值


String signature = MD5(signatureStr + secret)

```

### 6.4 传递签名
将计算的签名结果放到Request的Header 中，key为：Signature。

### 6.5 签名错误排查方法
当签名校验失败时，服务端应将signature放到HTTP Response的Header中返回到客户端，Key为：Error-Message，同时将参与签名计算之前的字符串返回到客户端，Key为： Error-Parameters,客户端将本地计算的signature与服务端返回的signature进行对比，如还找不到问题所在就对比参数，并将服务端返回的参与签名计算之前的字符串生成md5比对； 以此来检查用于签名计算的密钥是否正确。


# 7. Response Header和Response Body规则

### 7.1 Response Header
```
Error-Message: 错误签名值
Error-Parameters：错误生成签名值之前的字符串（只允许生产环境以外的环境有这个字段出现）
App-Timestamp: 1505443930(POSIX时间)

说明:

1.POSIX时间(POSIX time)，是一种时间表示方式，定义为从格林威治时间1970年01月01日00时00分00秒起至现在的总秒数
```

### 7.2 Response Body
```
{
	"code":"10000",  //状态码
	"message":"", //状态信息，如状态为SUCCESS则该字段值为空，相反为错误具体信息
	"detail" :""
	"content":{ 
               "page":{
			"pageNum":0,  //当前页数
			"pageSize":0, //每页多少条
			"total"0:,    //总条数
			"isEnd":0,    //是否为最后一页
			"nextPage":0  //下一页
                        "pid":"" //后端排查问题所需，前端不必关心
                        "totalPage": 0 //需要查总数的取此值，不需要查总数的不取此值
		},   
                 
                ....
                
                //根据业务需求自行决定	
	}
}


注意：返回的具体数据节点，不能出现null值，要么为空，要么这个节点就不返回
     
```

### 7.3 Response Body字段说明:
1、code是后端定义好的代码，详见：http://192.168.60.125:8080/dokuwiki/doku.php?id=%E5%BC%82%E5%B8%B8%E9%94%99%E8%AF%AF%E7%BC%96%E7%A0%81%E8%A7%84%E8%8C%83 中的各个模块在错误码中的模块编码。正确码均为10000（10000为字符串）

2、message是后端返回的结果消息，状态码为：10000，message为空；状态码为其它，message里的内容为错误描述，比如某某参数为空。

3、detail是详情（后端排查问题所用，前端不必关心）

4、content是返回的内容

5、page是分页信息

# 8. HTTP Response Code

- 200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
- 201 CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功。
- 202 Accepted - [*]：表示一个请求已经进入后台排队（异步任务）
- 204 NO CONTENT - [DELETE]：用户删除数据成功。
- 400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
- 401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
- 403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
- 404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
- 406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
- 410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
- 422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
- 500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功

# 9. 后端接口返回数据说明
业务逻辑尽量在后端完成，返回给前端的数据尽量精简。
例如：
```
1、某个房源信息有三个维度：A:1、B:2、C:3，必须通过这三个维度才能判断出该房源是否为已租或未租或其他等
2、后端不能把这三个维度直接返回给前端，应该返回一个状态码，表示该房源已租、未租、其他等。
```


# 10. 接口说明文档
### 10.1 接口说明:
^接口名  ^请求方式   ^是否签名验证  ^是否登录验证^ 请求数限制^是否抛出异常 ^接口描述 ^负责人 ^
| getUserByUserId | Get |  是  | 是 |否|否|获取某个用户信息|xx|


### 10.2 输入 参数说明:
^参数名  ^类型   ^是否必选  ^描述^
|userId|Integer|是|用户id|

### 10.3 输出 返回结果说明:
```
{
	"code":"10000",  //状态码
	"message":"", //状态信息，如状态为SUCCESS则该字段值为空，相反为错误具体信息
	"detail" :"" //后端特定字段，前端不必关心
	"content":{ 
               "page":{
			
		},   
                 
		"user":{
                	"name":"张三", //姓名
			"sex":"男", //性别
			"age":18 //年龄	
                }
	}
}
```
^key  ^value   ^
|code|状态码|
|message|具体状态消息|
|detail|后端特定字段，前端不必关心|
|content|返回内容|
|page|分页信息|
|user|具体数据对象key|
|name|姓名|
|sex|性别|
|age|年龄|

### 

### 10.4 输出 异常编码

====== 异常错误编码规范 ======

====== 修订记录 ======

^版本  ^修订日期   ^修订人  ^说明  ^
| 1.0 |2017-03-15 |宋伟  |第一版 |

===== 背景 =====
  * 目前各个开发人员在代码里抛出异常时、方式各异。								
  * 有些人在抛出异常时、会给该异常编一个错误码、有些人不会。								
  * 有些人在抛出异常时、只有很笼统的错误消息、导致查看log时、没有足够的上下文信息去定位、重现错误。								
  * 有些异常错误消息本来只供开发人员使用、却直接呈现给了最终用户、导致用户迷惑。								
  * 针对这些问题、有必要制定一个错误编码规范。	
===== 整体开发过程 =====
  - 开发人员按照规范、根据业务需要定义错误码。								
  - 工具每天从代码里收集最新的错误码一览、生成文档。								
  - 审核人员审核错误码、特别是审核最终面向用户的错误消息是否规范。								
  - 开发人员在发生异常的地方抛出MogoException或者其子类、需传入相应的ErrorCode。								
  - 产品经理或者项目经理可根据业务需要定义面向最终用户的错误message、这些message将由开发人员定义在资源文件中。
===== 具体规范方案 =====

  * **错误码格式**
错误码信息统一由ErrorCode类定义、类中各个字段定义如下：
^字段英文	^字段名	^格式	^例子	^备注^
^code	|错误码	|模块编码+四位整数	|C0001	|模块编码参考下面|
^msg	|内部错误消息	|String	|"参数非法。{paramName=%s, value=%s}"	|内部错误消息模板、抛出异常时根据本模板定义具体的错误消息。|				
^userMsg	|用户错误消息	|String	|"参数非法。{paramName=%s, value=%s}"	|用户错误消息模板、抛出异常时根据本模板定义具体的错误消息。若不设置、则和msg一致。错误消息可以定义在资源文件、默认文件名为errorcodes.properties。|				
^type	|错误类型	|枚举	|COMMON	|枚举定义在ErrorType类|

----

  * **错误消息资源文件**
可将面向用户的错误消息定义在资源文件中、默认在资源文件为classpath下的errorcodes.properties。								
可通过系统变量指定资源文件名、系统变量KEY为sysconfig.errorcode.filepath。								
例子：指定资源文件:								
"System.setProperty(""sysconfig.errorcode.filepath"", ""classpath:errorcodes.properties"");"														
资源文件格式
资源文件key为ErrorCode中定义的错误码、value为面向用户的错误消息模板。
							
 例子：
  errorcodes.properties
  C0001=参数必须输入
  C0002=参数%s有误

----  				
  * **错误类型**

错误类型定义在ErrorType类中、根据业务需要定义下面几种								
	1）COMMON
	一般错误类型。该类型的异常会导致DB事务回滚、并由统一异常拦截器处理。
	一般会向client端返回错误页面或者错误消息的JSON串（若客户端请求为AJAX请求）。
	2）NOROLLBACK
	事务不回滚类型。该类型的异常不会导致DB事务回滚（需要配置事务管理器）、由统一异常拦截器处理。
	一般会向client端返回错误页面或者错误消息的JSON串（若客户端请求为AJAX请求）。
	抛出本类型的异常意味着之前的业务操作有效、但是后续的业务处理将终止。
	3）NEW_FEATURE_NOT_READY	
	新功能未就绪类型。该类型的异常需要在业务侧捕捉、捕捉到之后按照旧功能的业务逻辑处理、
	捕捉异常之后不会导致DB事务回滚(若不捕捉则会回滚)。
	本异常一般不由统一异常拦截器处理。

----

  * **错误码常量收集类**

  -  普通列表项目共通的错误码作为常量统一定义在GeneralErrorCodes类中								
  -  ErrorCode实例只能通过GeneralErrorCodes的protected的工厂方法实例化。
  -  各个模块的私有的错误码定义在自身模块的常量收集类中。			
  -  命名格式：	模块名+ErrorCodes	
  -  要求：	需要继承GeneralErrorCodes类、并定义为抽象类。理由参见
例子：
  public abstract class AcctErrorCodes extends GeneralErrorCodes{...}                             
  App层(Controller层）由于不应该包含复杂业务逻辑、该层的错误码定义在共通错误码常量类GeneralErrorCodes里


----

  * **异常类XXXException**
  - 代码里所有需要抛出异常的地方必须抛出MogoException及其子类。								
  - XXXException实例化时需要传入某个ErrorCode实例、并根据ErrorCode的消息模板定义具体的错误消息。								
例子：	
  "throw new XXXException(GeneralErrorCodes.PARAM_INVALID, ""roomId"", -1);"	
  "GeneralErrorCodes.PARAM_INVALID的消息模板为：参数非法。{paramName=%s, value=%s}"	
  - 可定义XXXException的子类、但是若没必要不推荐这样。	
  例如，	定义一个不会导致DB事务回滚的异常：	
   public class NoRollBackXXXException extends XXXException{...}

----

  * **各个模块在错误码中的模块编码**

^模块名	^模块编码	^错误码例子	^错误码常量收集类^

^service-acct	|S01	|S010001	|AcctErrorCodes|

^service-base	|S02	|S020001	|BaseErrorCodes|

^service-bill	|S03	|S030001	|BillErrorCodes|

^service-city	|S04	|S040001	|CityErrorCodes|

^service-cntr	|S05	|S050001	|CntrErrorCodes|

----

  * **校验工具类ValidateUtil**

  目前系统提供了一个通用的输入校验类ValidateUtil、该类定义在service-util模块中。可使用该类完成一些基本的校验功能。
   ·检查参数是否为空	
   ·检查参数是否匹配正则表达式	
   ·检查参数是否匹配一些常用模式、例如是否为URL、是否全是数字...	
  为了使用上的方便、ValidateUtil校验类设计为：若校验不通过则抛出异常。	
  抛出的异常均为MogoException类及其子类、可通过成员方法getErrorCode获取详细错误码。

----

  * **错误码一览**

  错误码信息将通过工具在代码中实时收集、并记录到【编码一览】sheet里。		
  不需要预先在本表格中定义。
