### Week_14

周四作业：

**1.（必做）**思考和设计自定义 MQ 第二个版本或第三个版本，写代码实现其中至少一个功能点，把设计思路和实现代码，提交到 GitHub。（暂未实现）



**第一个版本：**内存mq

基于内存Queue实现生产和消费API

1. 创建内存BlockingQueue，作为底层消息存储
2. 定义Topic，支持多个Topic
3. 定义Producer，支持Send消息
4. 定义Consumer，支持Poll消息

**第二个版本：**实现按照offset手动选择拉取消息

去掉内存Queue，设计自定义的Queue，实现消息确认和消费offset

1. 自定义内存Message数组模拟Queue
2. 使用指针记录当前消息写入位置
3. 对于每个命名消费者，用指针记录消费位置

存在问题：因为数据没有真实的弹出，还存在内存，容易OOM

**第三个版本：**从单机走向服务器模式

拆分broker和client（包括producer和consumer）

1. 将Queue保存到web server端
2. 设计消息读写API接口，确认接口，提交offset
3. producer和consumer通过httpclient访问Queue
4. 实现消息确认，offset提交
5. 实现consumer从offset增量拉取

**第四个版本：**

增加多种策略（各条之间没有关系，可以任意选择实现）

1. 考虑实现消息过期，消息重试，消息定时投递等策略
2. 考虑批量操作，包括读写，可以打包和压缩
3. 考虑消息清理策略，包括定时清理，按容量清理、LRU等
4. 考虑消息持久化，存入数据库，或WAL日志文件，或BookKeeper
5. 考虑将spring mvc替换成netty下的tcp传输协议，rsocket/websocket

特点和问题：

1. 完全功能
2. 内存不OOM，持久化
3. 走TCP，可以真正做到server-> client，从而实现PUSH模式

**第五个版本**

对接各种技术（各条之间没有关系，可以任意选择实现）

1. 考虑对接JMS1.1接口规范
2. 考虑实现STOMP消息规范
3. 考虑实现消息事务机制与事务管理器
4. 对接Spring
5. 对接Camel或Spring Integration
6. 优化内存和磁盘的使用

