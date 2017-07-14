package com.wdxxl.lucene.customscorequery;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.function.CustomScoreProvider;
import org.apache.lucene.search.function.CustomScoreQuery;

public class WdxxlCustomScoreQuery extends CustomScoreQuery {
	private static final long serialVersionUID = 1L;
	private Query subQuery;
	//subQuery表示原有的Query，valSrcQuery用于做评分的Query
    //CustomScoreQuery其实有很多构造方法，我们可以一次性传入很多评分规则
    public WdxxlCustomScoreQuery(Query subQuery) {
        super(subQuery);
        this.subQuery = subQuery;
    }

    //3、覆盖此方法
    @Override
    protected CustomScoreProvider getCustomScoreProvider(IndexReader reader)
            throws IOException {
        //默认情况实现的评分是通过   原有的评分*传入    进来的评分域得到最后的评分
        //而这种评分算法很显然是满足不了我们的要求的，于是我们自己为了根据不同的需求需要
        //自己进行评分设定
        /*
         * 自定义评分的步骤
         * （1）创建一个类继承于CustomScoreProvider
         * （2）覆盖CustomScoreProvider中的方法customScore（）
         * */
        return new WdxxlCustomScoreProvider(reader, subQuery);
    }
}
