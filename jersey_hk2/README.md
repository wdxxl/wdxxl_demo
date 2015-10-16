# Jersey Demo
http://stackoverflow.com/questions/23304404/trouble-creating-a-simple-singleton-class-in-jersey-2-using-built-in-jersey-depe


http://localhost:8080/jersey/test/func1/1
1.单例计数器
    最简单的单例模式应用就是计数器，在基于Web的应用中，我们希望有一个全局计数器来统计单件的次数，为此我们要定义一个单例计数器：
Key Code: 
    bind(new JustOne()).to(JustOne.class);
    bind(JustOne.class).to(JustOne.class).in(Singleton.class);

