
https://lucene.apache.org/core/7_2_1/demo/overview-summary.html

Tips 1:
	System.out.println(searcher.explain(query, scoreDocs[i].doc));

|            Package      |   Sub Package    |            Info            |
| ----------------------- | ---------------- | -------------------------- |
| lucene.facet            | FacetDemo        | 面向面的查询，和分组查询类似    |
| lucene.index            | MultiReaderDemo  | 多个索引一起查询, 并且排序正确  |
| lucene.query.spanquery  | Span*QueryDemo   | SpanQuery即跨度查询          |
