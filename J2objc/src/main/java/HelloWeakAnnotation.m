//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: HelloWeakAnnotation.java
//

#include "HelloWeakAnnotation.h"
#include "IOSObjectArray.h"
#include "J2ObjC_source.h"
#include "java/io/PrintStream.h"
#include "java/lang/System.h"
#include "java/util/ArrayList.h"
#include "java/util/List.h"

@implementation HelloWeakAnnotation

J2OBJC_IGNORE_DESIGNATED_BEGIN
- (instancetype)init {
  HelloWeakAnnotation_init(self);
  return self;
}
J2OBJC_IGNORE_DESIGNATED_END

+ (void)mainWithNSStringArray:(IOSObjectArray *)args {
  HelloWeakAnnotation_mainWithNSStringArray_(args);
}

- (void)__javaClone:(HelloWeakAnnotation *)original {
  [super __javaClone:original];
  JreRelease(kingTest_);
}

+ (const J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { NULL, NULL, 0x1, -1, -1, -1, -1, -1, -1 },
    { NULL, "V", 0x9, 0, 1, -1, -1, -1, -1 },
  };
  #pragma clang diagnostic push
  #pragma clang diagnostic ignored "-Wobjc-multiple-method-names"
  methods[0].selector = @selector(init);
  methods[1].selector = @selector(mainWithNSStringArray:);
  #pragma clang diagnostic pop
  static const J2ObjcFieldInfo fields[] = {
    { "kingTest_", "LJavaUtilList;", .constantValue.asLong = 0, 0x1, -1, -1, 2, -1 },
  };
  static const void *ptrTable[] = { "main", "[LNSString;", "Ljava/util/List<Ljava/lang/String;>;" };
  static const J2ObjcClassInfo _HelloWeakAnnotation = { "HelloWeakAnnotation", NULL, ptrTable, methods, fields, 7, 0x1, 2, 1, -1, -1, -1, -1, -1 };
  return &_HelloWeakAnnotation;
}

@end

void HelloWeakAnnotation_init(HelloWeakAnnotation *self) {
  NSObject_init(self);
}

HelloWeakAnnotation *new_HelloWeakAnnotation_init() {
  J2OBJC_NEW_IMPL(HelloWeakAnnotation, init)
}

HelloWeakAnnotation *create_HelloWeakAnnotation_init() {
  J2OBJC_CREATE_IMPL(HelloWeakAnnotation, init)
}

void HelloWeakAnnotation_mainWithNSStringArray_(IOSObjectArray *args) {
  HelloWeakAnnotation_initialize();
  HelloWeakAnnotation *helloWeakAnnotation = new_HelloWeakAnnotation_init();
  helloWeakAnnotation->kingTest_ = new_JavaUtilArrayList_init();
  [helloWeakAnnotation->kingTest_ addWithId:@"testListValue"];
  [((JavaIoPrintStream *) nil_chk(JreLoadStatic(JavaLangSystem, out))) printlnWithNSString:@"hello weak annotation"];
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(HelloWeakAnnotation)
