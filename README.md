# Rewrite-java
重写一个文件的某几个字节的数据。比如，修改某个<a href="http://blog.fpliu.com/it/data/file#checkType" target="_blank">文件的魔数</a>、修改某个文件中的时间戳等，总之，它可能很有用，你懂得。
<br><br>
该工具使用<a href="http://blog.fpliu.com/it/language/Java" target="_blank">Java</a>语言编写，使用<a href="http://blog.fpliu.com/it/software/gradle" target="_blank">gradle</a>构建。

## 1、克隆项目
```
git clone https://github.com/leleliu008/Rewrite-java.git
```

## 2、构建jar包
<a href="http://blog.fpliu.com/it/os/unix-like/distribution/gnu-linux" target="_blank">GNU/Linux</a>、<a href="http://blog.fpliu.com/it/os/macOS" target="_blank">macOS</a>用户执行如下命令：
```
./gradlew build
```
<a href="http://blog.fpliu.com/it/os/Windows" target="_blank">Windows</a>用户执行如下命令：
```
gradlew.bat
```
构建后的jar包在build/libs目录中。
## 3、执行命令
查看使用帮助：
```
java -jar RewriteFile.jar -h
```
运行结果：
```
usage:   java -jar RewriteFile.jar <hex | string> <replacement> <fromBytes> <toBytes> <filePath>
example: java -jar RewriteFile.jar hex FD09A623CC 0 5 ~/xx.png
         java -jar RewriteFile.jar string "1544889600" 20 30 ~/index.json
note:    fromBytes must bigger than 0
warning: this command will rewrite your origin file, so, please bak your origin file!
```
