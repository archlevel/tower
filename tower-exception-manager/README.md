### exception管理规范

#### soa exception的基类：BasicException
#### 每个服务对应的exception由服务自己管理
#### 每个服务都有三类exception：DataAccessException、ServiceException、ControllerException
#### 每个服务每一类都由枚举方式定义相应的错误代码，
	1, 数据访问层exception定义在com.company.service.xxxx.dao.exception
	2, 服务层exception定义在com.company.service.xxxx.exception
	3, 控制层expetion定义在com.company.service.xxxx.web.exception