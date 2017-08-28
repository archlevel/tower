CACHED.FLG = true
# native String.hashCode();
NATIVE_HASH     = 0
# original compatibility hashing algorithm (works with other clients)
OLD_COMPAT_HASH = 1
# new CRC32 based compatibility hashing algorithm (works with other clients)
NEW_COMPAT_HASH = 2
# MD5 Based -- Stops thrashing when a server added or removed
CONSISTENT_HASH = 3

memcached.initialConnections   = 1
memcached.minSpareConnections  = 1
memcached.maxSpareConnections  = 15
memcached.maxIdleTime          = 300000
memcached.maxBusyTime          = 30000
memcached.maintThreadSleep     = 30000
memcached.socketTimeout        = 30000
memcached.socketConnectTimeout = 3000
memcached.aliveCheck           = false
memcached.failover             = false
memcached.failback             = true
memcached.nagleAlgorithm       = true
memcached.hashingAlgorithm     = ${CONSISTENT_HASH}

###########################################################################

#{artifactId}.memcached.initialConnections   = ${memcached.initialConnections}
#{artifactId}.memcached.minSpareConnections  = ${memcached.minSpareConnections}
#{artifactId}.memcached.maxSpareConnections  = ${memcached.maxSpareConnections}
#{artifactId}.memcached.maxIdleTime          = ${memcached.maxIdleTime}
#{artifactId}.memcached.maxBusyTime          = ${memcached.maxBusyTime}
#{artifactId}.memcached.maintThreadSleep     = ${memcached.maintThreadSleep}
#{artifactId}.memcached.socketTimeout        = ${memcached.socketTimeout}
#{artifactId}.memcached.socketConnectTimeout = ${memcached.socketConnectTimeout}
#{artifactId}.memcached.aliveCheck           = ${memcached.aliveCheck}
#{artifactId}.memcached.failover             = ${memcached.failover}
#{artifactId}.memcached.failback             = ${memcached.failback}
#{artifactId}.memcached.nagleAlgorithm       = ${memcached.nagleAlgorithm}
#{artifactId}.memcached.hashingAlgorithm     = ${memcached.hashingAlgorithm}

##use comma split diff instance  
#{artifactId}.memcached.weights = 1,2,2
#{artifactId}.memcached.servers = 192.168.1.111:11211,192.168.1.111:11211,192.168.1.111:11211
