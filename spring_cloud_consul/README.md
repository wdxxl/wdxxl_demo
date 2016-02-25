# Spring_cloud_consul Demo

wget https://releases.hashicorp.com/consul/0.6.3/consul_0.6.3_linux_amd64.zip
unzip consul_0.6.3_linux_amd64.zip
wdxxl@ubuntu:~/Downloads$ sudo mv consul /usr/local/bin/consul
wdxxl@ubuntu:~$ sudo consul agent -server -bootstrap -data-dir /temp/consul -ui

http://localhost:8500/ui
