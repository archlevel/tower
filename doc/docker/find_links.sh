#!/bin/bash

DOCKER_NETNS_SCRIPT=./docker_netns.sh
IFINDEX=$1
if [[ -z $IFINDEX ]]; then
    
   for namespace in $($DOCKER_NETNS_SCRIPT); 
   do
       printf "e[1;31m%s: e[0m"
       $namespace $DOCKER_NETNS_SCRIPT $namespace ip -c -o link
       printf ""
    
    done
else
    
    for namespace in $($DOCKER_NETNS_SCRIPT); 
    do
        
        if $DOCKER_NETNS_SCRIPT $namespace ip -c -o link | grep -Pq "^$IFINDEX: "; then
            printf "e[1;31m%s: e[0m"
            $namespace $DOCKER_NETNS_SCRIPT $namespace ip -c -o link | grep -P "^$IFINDEX: ";
            printf ""
        
        fi
    done
fi
