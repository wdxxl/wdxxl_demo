# Jesque Demo


Job ran at=Tue Dec 15 01:09:04 PST 2015, with arg1 = Hello, and arg2= World

127.0.0.1:6379> keys *
1) "resque:queues"
2) "resque:stat:processed"
127.0.0.1:6379>  type resque:queues
set
127.0.0.1:6379> zrange reuqes:queues 0 -1
(empty list or set)
127.0.0.1:6379>  type resque:queues
set
127.0.0.1:6379> zrange reuqes:queues 0 -1
(empty list or set)
