javaService=http://192.168.201.190:8080/
#
iDatabase=true
jdbc.driver = com.mysql.jdbc.Driver
sql.jdbc.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
#
initialSize = 0
maxActive = 40
maxIdle = 10
minIdle = 0
maxWait = 3000
testOnBorrow=false
testWhileIdle=true
validationQuery = select now()
removeAbandonedTimeout = 300
minEvictableIdleTimeMillis = 2000
timeBetweenEvictionRunsMillis = 1000
poolPreparedStatements=true
defaultReadOnly=false
logAbandoned=true
removeAbandoned=true
connStr=useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&elideSetAutoCommits=true&cacheServerConfiguration=true

#cache
cache_db.driver = ${jdbc.driver}
cache_db.url = jdbc:mysql://db.corp.tower.com:3306/soafw_db?${connStr}
cache_db.username = #{db.username}
cache_db.password = #{db.password}
cache_db.initialSize = ${initialSize}
cache_db.maxActive = ${maxActive}
cache_db.maxIdle = 5
cache_db.minIdle = ${minIdle}
cache_db.maxWait = ${maxWait}
cache_db.removeAbandonedTimeout = ${removeAbandonedTimeout}
cache_db.minEvictableIdleTimeMillis= 1000
cache_db.timeBetweenEvictionRunsMillis = 500
cache_db.poolPreparedStatements = ${poolPreparedStatements}
cache_db.defaultReadOnly = ${defaultReadOnly}
cache_db.logAbandoned = ${logAbandoned}
cache_db.removeAbandoned = ${removeAbandoned}
cache_db.testOnBorrow = ${testOnBorrow}
cache_db.testWhileIdle = ${testWhileIdle}
cache_db.validationQuery = ${validationQuery}

#cache_db_slave
cache_db_slave.driver = ${jdbc.driver}
cache_db_slave.url = jdbc:mysql://db.corp.tower.com:3306/soafw_db?${connStr}
cache_db_slave.username = #{db.username}
cache_db_slave.password = #{db.password}
cache_db_slave.initialSize = ${initialSize}
cache_db_slave.maxActive = ${maxActive}
cache_db_slave.maxIdle = ${maxIdle}
cache_db_slave.minIdle = ${minIdle}
cache_db_slave.maxWait = ${maxWait}
cache_db_slave.removeAbandonedTimeout = ${removeAbandonedTimeout}
cache_db_slave.minEvictableIdleTimeMillis = ${minEvictableIdleTimeMillis}
cache_db_slave.timeBetweenEvictionRunsMillis = ${timeBetweenEvictionRunsMillis}
cache_db_slave.poolPreparedStatements = ${poolPreparedStatements}
cache_db_slave.defaultReadOnly = ${defaultReadOnly}
cache_db_slave.logAbandoned = ${logAbandoned}
cache_db_slave.removeAbandoned = ${removeAbandoned}
cache_db_slave.testOnBorrow = ${testOnBorrow}
cache_db_slave.testWhileIdle = ${testWhileIdle}
cache_db_slave.validationQuery = ${validationQuery}

#cache_db_map_query
cache_db_map_query.driver = ${jdbc.driver}
cache_db_map_query.url = jdbc:mysql://db.corp.tower.com:3306/soafw_db?${connStr}
cache_db_map_query.username = #{db.username}
cache_db_map_query.password = #{db.password}
cache_db_map_query.initialSize = ${initialSize}
cache_db_map_query.maxActive = ${maxActive}
cache_db_map_query.maxIdle = ${maxIdle}
cache_db_map_query.minIdle = ${minIdle}
cache_db_map_query.maxWait = ${maxWait}
cache_db_map_query.removeAbandonedTimeout = ${removeAbandonedTimeout}
cache_db_map_query.minEvictableIdleTimeMillis = ${minEvictableIdleTimeMillis}
cache_db_map_query.timeBetweenEvictionRunsMillis = ${timeBetweenEvictionRunsMillis}
cache_db_map_query.poolPreparedStatements = ${poolPreparedStatements}
cache_db_map_query.defaultReadOnly = ${defaultReadOnly}
cache_db_map_query.logAbandoned = ${logAbandoned}
cache_db_map_query.removeAbandoned = ${removeAbandoned}
cache_db_map_query.testOnBorrow = ${testOnBorrow}
cache_db_map_query.testWhileIdle = ${testWhileIdle}
cache_db_map_query.validationQuery = ${validationQuery}

##{artifactId}_db
#{artifactId}_db.driver = ${jdbc.driver}
#{artifactId}_db.url = jdbc:mysql://db.corp.tower.com:3306/#{artifactId}_db?${connStr}
#{artifactId}_db.username = #{db.username}
#{artifactId}_db.password = #{db.password}
#{artifactId}_db.initialSize = ${initialSize}
#{artifactId}_db.maxActive = ${maxActive}
#{artifactId}_db.maxIdle = 5
#{artifactId}_db.minIdle = ${minIdle}
#{artifactId}_db.maxWait = ${maxWait}
#{artifactId}_db.removeAbandonedTimeout = ${removeAbandonedTimeout}
#{artifactId}_db.minEvictableIdleTimeMillis= 1000
#{artifactId}_db.timeBetweenEvictionRunsMillis = 500
#{artifactId}_db.poolPreparedStatements = ${poolPreparedStatements}
#{artifactId}_db.defaultReadOnly = ${defaultReadOnly}
#{artifactId}_db.logAbandoned = ${logAbandoned}
#{artifactId}_db.removeAbandoned = ${removeAbandoned}
#{artifactId}_db.testOnBorrow = ${testOnBorrow}
#{artifactId}_db.testWhileIdle = ${testWhileIdle}
#{artifactId}_db.validationQuery = ${validationQuery}

##{artifactId}_db_slave
#{artifactId}_db_slave.driver = ${jdbc.driver}
#{artifactId}_db_slave.url = jdbc:mysql://db.corp.tower.com:3306/#{artifactId}_db?${connStr}
#{artifactId}_db_slave.username = #{db.username}
#{artifactId}_db_slave.password = #{db.password}
#{artifactId}_db_slave.initialSize = ${initialSize}
#{artifactId}_db_slave.maxActive = ${maxActive}
#{artifactId}_db_slave.maxIdle = ${maxIdle}
#{artifactId}_db_slave.minIdle = ${minIdle}
#{artifactId}_db_slave.maxWait = ${maxWait}
#{artifactId}_db_slave.removeAbandonedTimeout = ${removeAbandonedTimeout}
#{artifactId}_db_slave.minEvictableIdleTimeMillis = ${minEvictableIdleTimeMillis}
#{artifactId}_db_slave.timeBetweenEvictionRunsMillis = ${timeBetweenEvictionRunsMillis}
#{artifactId}_db_slave.poolPreparedStatements = ${poolPreparedStatements}
#{artifactId}_db_slave.defaultReadOnly = ${defaultReadOnly}
#{artifactId}_db_slave.logAbandoned = ${logAbandoned}
#{artifactId}_db_slave.removeAbandoned = ${removeAbandoned}
#{artifactId}_db_slave.testOnBorrow = ${testOnBorrow}
#{artifactId}_db_slave.testWhileIdle = ${testWhileIdle}
#{artifactId}_db_slave.validationQuery = ${validationQuery}

##{artifactId}_db_map_query
#{artifactId}_db_map_query.driver = ${jdbc.driver}
#{artifactId}_db_map_query.url = jdbc:mysql://db.corp.tower.com:3306/#{artifactId}_db?${connStr}
#{artifactId}_db_map_query.username = #{db.username}
#{artifactId}_db_map_query.password = #{db.password}
#{artifactId}_db_map_query.initialSize = ${initialSize}
#{artifactId}_db_map_query.maxActive = ${maxActive}
#{artifactId}_db_map_query.maxIdle = ${maxIdle}
#{artifactId}_db_map_query.minIdle = ${minIdle}
#{artifactId}_db_map_query.maxWait = ${maxWait}
#{artifactId}_db_map_query.removeAbandonedTimeout = ${removeAbandonedTimeout}
#{artifactId}_db_map_query.minEvictableIdleTimeMillis = ${minEvictableIdleTimeMillis}
#{artifactId}_db_map_query.timeBetweenEvictionRunsMillis = ${timeBetweenEvictionRunsMillis}
#{artifactId}_db_map_query.poolPreparedStatements = ${poolPreparedStatements}
#{artifactId}_db_map_query.defaultReadOnly = ${defaultReadOnly}
#{artifactId}_db_map_query.logAbandoned = ${logAbandoned}
#{artifactId}_db_map_query.removeAbandoned = ${removeAbandoned}
#{artifactId}_db_map_query.testOnBorrow = ${testOnBorrow}
#{artifactId}_db_map_query.testWhileIdle = ${testWhileIdle}
#{artifactId}_db_map_query.validationQuery = ${validationQuery}