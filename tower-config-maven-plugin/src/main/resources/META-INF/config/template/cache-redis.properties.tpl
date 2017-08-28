#########################################################################
redis.maxTotal   = 8
redis.minIdle   = 0
redis.maxIdle  = 8
redis.minEvictableIdleTimeMillis  = 60000
redis.numTestsPerEvictionRun          = -1
redis.softMinEvictableIdleTimeMillis          = 30000
redis.testOnBorrow     = false
redis.testOnReturn        = false
redis.testWhileIdle = false
redis.timeBetweenEvictionRunsMillis           = 30000

###########################################################################


###########################################################################

#{artifactId}.tab.cache.tag=4
#{artifactId}.rec.cache.tag=4
#{artifactId}.redis.timeout=1000
#{artifactId}.redis.maxTotal          = ${redis.maxTotal}
#{artifactId}.redis.minIdle          = ${redis.minIdle}
#{artifactId}.redis.maxIdle          = ${redis.maxIdle}
#{artifactId}.redis.minEvictableIdleTimeMillis     = ${redis.minEvictableIdleTimeMillis}
#{artifactId}.redis.numTestsPerEvictionRun        = ${redis.numTestsPerEvictionRun}
#{artifactId}.redis.softMinEvictableIdleTimeMillis = ${redis.softMinEvictableIdleTimeMillis}
#{artifactId}.redis.testOnBorrow           = ${redis.testOnBorrow}
#{artifactId}.redis.testOnReturn             = ${redis.testOnReturn}
#{artifactId}.redis.testWhileIdle             = ${redis.testWhileIdle}
#{artifactId}.redis.timeBetweenEvictionRunsMillis       = ${redis.timeBetweenEvictionRunsMillis}

##use comma split diff instance
#{artifactId}.redis.servers = 192.168.1.111,192.168.1.112
#{artifactId}.redis.ports = 6379,6379
