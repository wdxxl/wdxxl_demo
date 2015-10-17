# PowerMock Demo

现如今比较流行的Mock工具如jMock 、EasyMock 、Mockito等都有一个共同的缺点：
不能mock静态、final、私有方法等。而PowerMock能够完美的弥补以上三个Mock工具的不足。

PowerMock介绍 
http://blog.csdn.net/jackiehff/article/details/14000779

Using PowerMock with Mockito
https://code.google.com/p/powermock/wiki/MockitoUsage13

# 
@RunWith(PowerMockRunner.class)
@PrepareForTest(CallPrivateMethod.class)